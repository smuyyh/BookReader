package com.justwayward.reader.ui.activity;

import android.net.Uri;
import android.os.AsyncTask;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.ui.adapter.EPubReaderAdapter;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.view.epubview.DirectionalViewpager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class ReadEPubActivity extends BaseActivity {

    @Bind(R.id.epubViewPager)
    DirectionalViewpager viewpager;

    private EPubReaderAdapter mAdapter;

    private String mFileName;
    private String mFilePath;

    private Book mBook;
    private ArrayList<TOCReference> mTocReferences;
    private List<SpineReference> mSpineReferences;
    public boolean mIsSmilParsed = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_epub;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        mFilePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
        mFileName = mFilePath.substring(mFilePath.lastIndexOf("/") + 1, mFilePath.lastIndexOf("."));
    }

    @Override
    public void configViews() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                loadBook();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                initPager();
            }
        }.execute();

    }

    private void loadBook() {

        try {
            // 打开书籍
            EpubReader reader = new EpubReader();
            InputStream is = new FileInputStream(mFilePath);
            mBook = reader.readEpub(is);

            mTocReferences = (ArrayList<TOCReference>) mBook.getTableOfContents().getTocReferences();
            mSpineReferences = mBook.getSpine().getSpineReferences();

            setSpineReferenceTitle();

            // 解压epub至缓存目录
            FileUtils.unzipFile(mFilePath, Constant.PATH_EPUB + "/" + mFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPager() {
        if (mBook != null && mSpineReferences != null && mTocReferences != null) {

            mAdapter = new EPubReaderAdapter(getSupportFragmentManager(),
                    mSpineReferences, mBook, mFileName, mIsSmilParsed);
            viewpager.setAdapter(mAdapter);
        }
    }

    private void setSpineReferenceTitle() {
        for (int j = 0; j < mSpineReferences.size(); j++) {
            String href = mSpineReferences.get(j).getResource().getHref();
            for (int i = 0; i < mTocReferences.size(); i++) {
                if (mTocReferences.get(i).getResource().getHref().equalsIgnoreCase(href)) {
                    mSpineReferences.get(j).getResource()
                            .setTitle(mTocReferences.get(i).getTitle());
                    break;
                } else {
                    mSpineReferences.get(j).getResource().setTitle("");
                }
            }
        }
    }

    public String getPageHref(int position) {
        String pageHref = mSpineReferences.get(position).getResource().getHref();
        String opfpath = FileUtils.getPathOPF(FileUtils.getEpubFolderPath(mFileName));
        if (FileUtils.checkOPFInRootDirectory(FileUtils.getEpubFolderPath(mFileName))) {
            pageHref = FileUtils.getEpubFolderPath(mFileName) + "/" + pageHref;
        } else {
            pageHref = FileUtils.getEpubFolderPath(mFileName) + "/" + opfpath + "/" + pageHref;
        }
        //String html = EpubManipulator.readPage(pageHref);
        return pageHref;
    }
}
