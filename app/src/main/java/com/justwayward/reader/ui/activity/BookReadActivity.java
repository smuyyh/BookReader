package com.justwayward.reader.ui.activity;

import android.os.AsyncTask;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.BookSource;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookReadActivityComponent;
import com.justwayward.reader.ui.adapter.BookReadPageAdapter;
import com.justwayward.reader.ui.adapter.TocListAdapter;
import com.justwayward.reader.ui.contract.BookReadContract;
import com.justwayward.reader.ui.presenter.BookReadPresenter;
import com.justwayward.reader.utils.BookPageFactory;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.view.BookReadFrameLayout;
import com.yuyh.library.bookflip.FlipViewController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lfh on 2016/8/7.
 */
public class BookReadActivity extends BaseActivity implements BookReadContract.View,
        BookReadFrameLayout.OnScreenClickListener, FlipViewController.ViewFlipListener {

    @Bind(R.id.ivBack)
    ImageView mIvBack;
    @Bind(R.id.tvBookReadReading)
    TextView mTvBookReadReading;
    @Bind(R.id.tvBookReadCommunity)
    TextView mTvBookReadCommunity;
    @Bind(R.id.tvBookReadChangeSource)
    TextView mTvBookReadChangeSource;
    @Bind(R.id.ivBookReadMore)
    ImageView mIvBookReadMore;
    @Bind(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
    @Bind(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    @Bind(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    @Bind(R.id.tvBookReadFeedBack)
    TextView mTvBookReadFeedBack;
    @Bind(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;
    @Bind(R.id.tvBookReadDownload)
    TextView mTvBookReadDownload;
    @Bind(R.id.tvBookReadToc)
    TextView mTvBookReadToc;
    @Bind(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @Bind(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;
    @Bind(R.id.brflRoot)
    BookReadFrameLayout mBookReadFrameLayout;

    @Bind(R.id.flipView)
    FlipViewController flipView;
    int lineHeight = 0;

    @Inject
    BookReadPresenter mPresenter;

    private List<String> mContentList = new ArrayList<>();
    private BookReadPageAdapter readPageAdapter;

    private List<BookToc.mixToc.Chapters> mChapterList = new ArrayList<>();
    private ListPopupWindow mTocListPopupWindow;
    private TocListAdapter mTocListAdapter;

    private String bookId;
    private int currentChapter = 1;
    private BookPageFactory factory;

    /**
     * 是否开始阅读章节
     **/
    boolean startRead = false;
    /**
     * 当前是否处于最后一页
     **/
    boolean endPage = false;
    /**
     * 当前是否处于第一页
     **/
    boolean startPage = false;
    /**
     * 是否是跳转到上一章
     **/
    private boolean isPre = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_read;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookReadActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        bookId = getIntent().getStringExtra("bookId");
        mTvBookReadTocTitle.setText(getIntent().getStringExtra("bookName"));
    }

    @Override
    public void configViews() {
        View view = getLayoutInflater().inflate(R.layout.item_book_read_page, null);
        final TextView tv = (TextView) view.findViewById(R.id.tvBookReadContent);
        lineHeight = tv.getLineHeight();
        LogUtils.i("line height:" + lineHeight + "  getLineHeight:");
        factory = new BookPageFactory(bookId, lineHeight);

        mTocListAdapter = new TocListAdapter(this, mChapterList);
        mTocListPopupWindow = new ListPopupWindow(this);
        mTocListPopupWindow.setAdapter(mTocListAdapter);
        mTocListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTocListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTocListPopupWindow.setAnchorView(mLlBookReadTop);
        mTocListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTocListPopupWindow.dismiss();
                currentChapter = position + 1;
                startRead = false;
                isPre = false;
                readCurrentChapter();
                hideReadBar();
            }
        });

        mPresenter.attachView(this);
        mPresenter.getBookToc(bookId, "chapters");
        mBookReadFrameLayout.setOnScreenClickListener(this);

        flipView.setOnViewFlipListener(this);
    }

    /**
     * 读取currentChapter章节。章节文件存在则直接阅读，不存在就请求加载
     */
    public void readCurrentChapter() {
        if (factory.getBookFile(currentChapter).length() > 50)
            showChapterRead(null, currentChapter);
        else
            mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
    }

    @Override
    public void showBookToc(List<BookToc.mixToc.Chapters> list) { // 加载章节列表
        mChapterList.clear();
        mChapterList.addAll(list);

        readCurrentChapter();
    }

    @Override
    public synchronized void showChapterRead(ChapterRead.Chapter data, int chapter) { // 加载章节内容
        if (data != null)
            factory.append(data, chapter); // 缓存章节保存到文件

        // 阅读currentChapter章节
        if (factory.getBookFile(currentChapter).length() > 50 && !startRead && currentChapter < mChapterList.size()) {
            startRead = true;
            new BookPageTask().execute();
        }

        if (chapter == currentChapter) {
            // 每次都往后继续缓存三个章节
            for (int j = currentChapter + 1; j <= currentChapter + 3 && j <= mChapterList.size(); j++) {
                if (factory.getBookFile(j).length() < 50) { // 认为章节文件不存在
                    // 获取对应章节
                    mPresenter.getChapterRead(mChapterList.get(j - 1).link, j);
                } else {
                    new ChapterCacheTask().execute(j); // 文章存在，则读取，放到LRUMap中
                }
            }
        } else if (factory.getBookFile(chapter).length() > 50 && !factory.hasCache(chapter)) { // 新获取的章节，还未缓存在LruMap
            new ChapterCacheTask().execute(chapter);
        }
    }

    @Override
    public void showBookSource(List<BookSource> list) {

    }


    @OnClick(R.id.ivBack)
    public void onClickBack() {
        if (mTocListPopupWindow.isShowing()) {
            mTocListPopupWindow.dismiss();
        }else{
            finish();
        }
    }

    @OnClick(R.id.tvBookReadChangeSource)
    public void onClickChangeSource() {

    }

    @OnClick(R.id.tvBookReadToc)
    public void onClickToc() {
        if (!mTocListPopupWindow.isShowing()) {
            mTvBookReadTocTitle.setVisibility(View.VISIBLE);
            mTvBookReadReading.setVisibility(View.GONE);
            mTvBookReadCommunity.setVisibility(View.GONE);
            mTvBookReadChangeSource.setVisibility(View.GONE);
            mIvBookReadMore.setVisibility(View.GONE);
            mTocListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mTocListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mTocListPopupWindow.show();
        }
        mTocListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flipView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flipView.onPause();
    }

    @Override
    public void onSideClick(boolean isLeft) {
        hideReadBar();
        if (isLeft) {
            if (flipView.getSelectedItemPosition() == 0) {
                startPage = true;
            }
            endPage = false;
            flipView.setSelection(flipView.getSelectedItemPosition() - 1);
        } else {
            flipView.setSelection(flipView.getSelectedItemPosition() + 1);
            if (flipView.getSelectedItemPosition() == mContentList.size() - 1) {
                endPage = true;
            }
            startPage = false;
        }
    }

    private void hideReadBar() {
        if (mLlBookReadBottom.getVisibility() == View.VISIBLE) {
            mLlBookReadBottom.setVisibility(View.GONE);
            mLlBookReadTop.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCenterClick() {
        if (mLlBookReadBottom.getVisibility() == View.VISIBLE) {
            mLlBookReadBottom.setVisibility(View.GONE);
            mLlBookReadTop.setVisibility(View.GONE);
        } else {
            mLlBookReadBottom.setVisibility(View.VISIBLE);
            mLlBookReadTop.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewFlipped(View view, int position) { // 页面滑动切换
        hideReadBar();
        LogUtils.i("onViewFlipped--" + position);
        if (position == mContentList.size() - 1) { // 切换到最后一页
            if (!endPage) {
                endPage = true;// 标记。继续切换时就切换到下一章节
                return;
            }
            endPage = false;
            onNext();
        } else if (position == 0) { // 切换到第一页
            if (!startPage) {
                startPage = true; // 标记。继续切换时就切换到上一章节
                return;
            }
            startPage = false;
            onPre();
        } else {
            startPage = false;
            endPage = false;
        }
    }

    @Override
    public void onPre() { // 加载上一章
        if (currentChapter > 1) {
            currentChapter -= 1;
            startRead = false;
            isPre = true; // 标记。加载完成之后显示最后一页
            readCurrentChapter();
        }
    }

    @Override
    public void onNext() { // 加载下一章
        if (currentChapter < mChapterList.size()) {
            currentChapter += 1;
            startRead = false;
            startPage = true;
            readCurrentChapter();
        }
    }

    /**
     * 读取章节内容，并进行分页处理
     */
    class BookPageTask extends AsyncTask<Integer, Integer, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtils.i("分页前" + new SimpleDateFormat("HH:mm:ss:SSS").format(new Date(System.currentTimeMillis())));
        }

        @Override
        protected List<String> doInBackground(Integer... params) {
            List<String> list = factory.readPage(currentChapter);
            return list;
        }

        @Override
        protected void onPostExecute(List<String> list) {
            super.onPostExecute(list);
            LogUtils.i("分页后" + new SimpleDateFormat("HH:mm:ss:SSS").format(new Date(System.currentTimeMillis())));
            mContentList.clear();
            mContentList.addAll(list);

            if (readPageAdapter == null) {
                readPageAdapter = new BookReadPageAdapter(mContext, mContentList, mChapterList.get(currentChapter - 1).title);
            } else {
                readPageAdapter.title = mChapterList.get(currentChapter - 1).title;
            }
            flipView.setAdapter(readPageAdapter);

            if (isPre) { // 如果是加载上一章，则跳转到最后一页
                flipView.setSelection(mContentList.size() - 1);
                endPage = true;
                isPre = false;
            } else {
                startPage = true;
            }
        }
    }

    class ChapterCacheTask extends AsyncTask<Integer, Integer, List<String>> {

        @Override
        protected List<String> doInBackground(Integer... params) {
            int chapter = params[0];
            factory.readPage(chapter);
            LogUtils.i("read:" + chapter);
            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            if (mTocListPopupWindow.isShowing()) {
                mTocListPopupWindow.dismiss();
                mTvBookReadTocTitle.setVisibility(View.GONE);
                mTvBookReadReading.setVisibility(View.VISIBLE);
                mTvBookReadCommunity.setVisibility(View.VISIBLE);
                mTvBookReadChangeSource.setVisibility(View.VISIBLE);
                mIvBookReadMore.setVisibility(View.VISIBLE);
            }else{
                finish();
            }
        }
        return false;
    }
}
