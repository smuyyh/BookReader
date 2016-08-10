package com.justwayward.reader.ui.activity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookReadActivityComponent;
import com.justwayward.reader.ui.adapter.BookReadPageAdapter;
import com.justwayward.reader.ui.contract.BookReadContract;
import com.justwayward.reader.ui.presenter.BookReadPresenter;
import com.justwayward.reader.utils.BookPageFactory;
import com.justwayward.reader.utils.LogUtils;
import com.yuyh.library.bookflip.FlipViewController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lfh on 2016/8/7.
 */
public class BookReadActivity extends BaseActivity implements BookReadContract.View {

    @Bind(R.id.iv_Back)
    ImageView mIvBack;
    @Bind(R.id.tvBookReadReading)
    TextView mTvBookReadReading;
    @Bind(R.id.tvBookReadCommunity)
    TextView mTvBookReadCommunity;
    @Bind(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
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

    @Bind(R.id.flipView)
    FlipViewController flipView;
    int lineHeight = 0;

    @Inject
    BookReadPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_read;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookReadActivityComponent.builder()
                .appComponent(appComponent)
                //.mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
        mPresenter.getBookToc(getIntent().getStringExtra("bookId"), "chapters");

        View view = getLayoutInflater().inflate(R.layout.item_book_read_page, null);
        final TextView tv = (TextView) view.findViewById(R.id.tvBookReadContent);
        lineHeight = tv.getLineHeight();
        new BookPageTask().execute();
    }

    @Override
    public void showBookToc(List<BookToc.Chapters> list) {
        mPresenter.getChapterRead(list.get(0).link);
    }

    @Override
    public void showChapterRead(ChapterRead.Chapter data) {
        LogUtils.d("content==" + data.cpContent);
    }

    @OnClick(R.id.rlBookReadRoot)
    public void onClickBookReadRoot() {
        if (mLlBookReadBottom.getVisibility() == View.VISIBLE) {
            mLlBookReadBottom.setVisibility(View.GONE);
            mLlBookReadTop.setVisibility(View.GONE);
        } else {
            mLlBookReadBottom.setVisibility(View.VISIBLE);
            mLlBookReadTop.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_Back)
    public void onClickBack() {
        finish();
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

    class BookPageTask extends AsyncTask<Integer, Integer, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtils.i("分页前" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date(System.currentTimeMillis())));
        }

        @Override
        protected List<String> doInBackground(Integer... params) {
            BookPageFactory factory = new BookPageFactory("xxxxxx", lineHeight);
            List<String> list = factory.readPage(factory.readTxt(1));
            return list;
        }

        @Override
        protected void onPostExecute(List<String> list) {
            super.onPostExecute(list);
            LogUtils.i("分页后" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date(System.currentTimeMillis())));
            flipView.setAdapter(new BookReadPageAdapter(mContext, list));
        }
    }
}
