package com.justwayward.reader.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookDetail;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.comment.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookDetailActivityComponent;
import com.justwayward.reader.ui.adapter.HotReviewAdapter;
import com.justwayward.reader.ui.contract.BookDetailContract;
import com.justwayward.reader.ui.presenter.BookDetailPresenter;
import com.justwayward.reader.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/6.
 */
public class BookDetailActivity extends BaseActivity implements BookDetailContract.View,OnRvItemClickListener<HotReview.Reviews> {

    @Bind(R.id.common_toolbar)
    Toolbar mCommonToolbar;
    @Bind(R.id.ivBookCover)
    ImageView mIvBookCover;
    @Bind(R.id.tvBookTitle)
    TextView mTvBookTitle;
    @Bind(R.id.tvAuthor)
    TextView mTvAuthor;
    @Bind(R.id.tvCatgory)
    TextView mTvCatgory;
    @Bind(R.id.tvWordCount)
    TextView mTvWordCount;
    @Bind(R.id.tvLatelyUpdate)
    TextView mTvLatelyUpdate;
    @Bind(R.id.tvLatelyFollower)
    TextView mTvLatelyFollower;
    @Bind(R.id.tvRetentionRatio)
    TextView mTvRetentionRatio;
    @Bind(R.id.tvSerializeWordCount)
    TextView mTvSerializeWordCount;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;
    @Bind(R.id.tvlongIntro)
    TextView mTvlongIntro;
    @Bind(R.id.tvMoreReview)
    TextView mTvMoreReview;
    @Bind(R.id.rvHotReview)
    RecyclerView mRvHotReview;

    @Inject
    BookDetailPresenter mPresenter;

    private HotReviewAdapter mAdapter;
    private List<HotReview.Reviews> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookDetailActivityComponent.builder()
                .appComponent(appComponent)
                //.mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
        mCommonToolbar.setTitle("书籍详情");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        setSupportActionBar(mCommonToolbar);

        mRvHotReview.setHasFixedSize(true);
        mRvHotReview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HotReviewAdapter(mContext, mList, this);
        mRvHotReview.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.getBookDetail(getIntent().getStringExtra("bookId"));
        mPresenter.getHotReview(getIntent().getStringExtra("bookId"));
    }

    @Override
    public void showBookDetail(BookDetail data) {
        Glide.with(mContext).load(Constant.IMG_BASE_URL + data.cover).into(mIvBookCover);
        mTvBookTitle.setText(data.title);
        mTvAuthor.setText(data.author + " | ");
        mTvCatgory.setText(data.cat + " | ");
        mTvWordCount.setText(String.valueOf(data.wordCount));
        mTvLatelyFollower.setText(String.valueOf(data.latelyFollower));
        mTvRetentionRatio.setText(String.valueOf(data.retentionRatio));
        mTvSerializeWordCount.setText(String.valueOf(data.serializeWordCount));

        mTagGroup.setTags((String[]) data.tags.toArray(new String[data.tags.size()]));

        mTvlongIntro.setText(data.longIntro);
    }

    @Override
    public void showHotReview(List<HotReview.Reviews> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position, HotReview.Reviews data) {

    }
}
