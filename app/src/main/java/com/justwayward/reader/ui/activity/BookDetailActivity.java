package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookDetail;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.bean.RecommendBookList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.ui.adapter.HotReviewAdapter;
import com.justwayward.reader.ui.adapter.RecommendBookListAdapter;
import com.justwayward.reader.ui.contract.BookDetailContract;
import com.justwayward.reader.ui.presenter.BookDetailPresenter;
import com.justwayward.reader.utils.FormatUtils;
import com.justwayward.reader.view.DrawableCenterButton;
import com.justwayward.reader.view.TagColor;
import com.justwayward.reader.view.TagGroup;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/6.
 */
public class BookDetailActivity extends BaseActivity implements BookDetailContract.View, OnRvItemClickListener<Object> {

    public static String INTENT_BOOK_ID = "bookId";

    public static void startActivity(Context context, String bookId) {
        context.startActivity(new Intent(context, BookDetailActivity.class)
                .putExtra(INTENT_BOOK_ID, bookId));
    }

    @Bind(R.id.ivBookCover)
    ImageView mIvBookCover;
    @Bind(R.id.tvBookListTitle)
    TextView mTvBookTitle;
    @Bind(R.id.tvBookListAuthor)
    TextView mTvAuthor;
    @Bind(R.id.tvCatgory)
    TextView mTvCatgory;
    @Bind(R.id.tvWordCount)
    TextView mTvWordCount;
    @Bind(R.id.tvLatelyUpdate)
    TextView mTvLatelyUpdate;
    @Bind(R.id.btnRead)
    DrawableCenterButton mBtnRead;
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
    @Bind(R.id.rlCommunity)
    RelativeLayout mRlCommunity;
    @Bind(R.id.tvCommunity)
    TextView mTvCommunity;
    @Bind(R.id.tvHelpfulYes)
    TextView mTvPostCount;
    @Bind(R.id.tvRecommendBookList)
    TextView mTvRecommendBookList;

    @Bind(R.id.rvRecommendBoookList)
    RecyclerView mRvRecommendBoookList;

    @Inject
    BookDetailPresenter mPresenter;

    private List<String> tagList = new ArrayList<>();
    private int times = 0;

    private HotReviewAdapter mHotReviewAdapter;
    private List<HotReview.Reviews> mHotReviewList = new ArrayList<>();
    private RecommendBookListAdapter mRecommendBookListAdapter;
    private List<RecommendBookList.RecommendBook> mRecommendBookList = new ArrayList<>();
    private String bookId;

    private boolean collapseLongIntro = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                //.mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
        mCommonToolbar.setTitle(R.string.book_detail);
    }

    @Override
    public void initDatas() {
        bookId = getIntent().getStringExtra(INTENT_BOOK_ID);
    }

    @Override
    public void configViews() {
        mRvHotReview.setHasFixedSize(true);
        mRvHotReview.setLayoutManager(new LinearLayoutManager(this));
        mHotReviewAdapter = new HotReviewAdapter(mContext, mHotReviewList, this);
        mRvHotReview.setAdapter(mHotReviewAdapter);

        mRvRecommendBoookList.setHasFixedSize(true);
        mRvRecommendBoookList.setLayoutManager(new LinearLayoutManager(this));
        mRecommendBookListAdapter = new RecommendBookListAdapter(mContext, mRecommendBookList,
                this);
        mRvRecommendBoookList.setAdapter(mRecommendBookListAdapter);

        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                startActivity(new Intent(BookDetailActivity.this, BooksByTagActivity.class)
                        .putExtra("tag", tag));
            }
        });

        mPresenter.attachView(this);
        mPresenter.getBookDetail(bookId);
        mPresenter.getHotReview(bookId);
        mPresenter.getRecommendBookList(bookId, "3");
    }

    @Override
    public void showBookDetail(BookDetail data) {
        Glide.with(mContext).load(Constant.IMG_BASE_URL + data.cover).placeholder(R.drawable
                .cover_default).transform(new GlideRoundTransform(mContext)).into(mIvBookCover);

        mTvBookTitle.setText(data.title);
        mTvAuthor.setText(String.format(getString(R.string.book_detail_author), data.author));
        mTvCatgory.setText(String.format(getString(R.string.book_detail_category), data.cat));
        mTvWordCount.setText(FormatUtils.formatWordCount(data.wordCount));
        mTvLatelyUpdate.setText(FormatUtils.formatDate(data.updated));
        mTvLatelyFollower.setText(String.valueOf(data.latelyFollower));
        mTvRetentionRatio.setText(String.valueOf(data.retentionRatio));
        mTvSerializeWordCount.setText(String.valueOf(data.serializeWordCount));

        tagList.clear();
        tagList.addAll(data.tags);
        times = 0;
        showHotWord();

        mTvlongIntro.setText(data.longIntro);
        mTvCommunity.setText(String.format(getString(R.string.book_detail_community), data.title));
        mTvPostCount.setText(String.format(getString(R.string.book_detail_post_count), data
                .postCount));
    }

    /**
     * 每次显示8个
     */
    private void showHotWord() {
        int start, end;
        if (times < tagList.size() && times + 8 <= tagList.size()) {
            start = times;
            end = times + 8;
        } else if (times < tagList.size() - 1 && times + 8 > tagList.size()) {
            start = times;
            end = tagList.size() - 1;
        } else {
            start = 0;
            end = tagList.size() > 8 ? 8 : tagList.size();
        }
        times = end;
        if (end - start > 0) {
            List<String> batch = tagList.subList(start, end);
            List<TagColor> colors = TagColor.getRandomColors(batch.size());
            mTagGroup.setTags(colors, (String[]) batch.toArray(new String[batch.size()]));
        }
    }

    @Override
    public void showHotReview(List<HotReview.Reviews> list) {
        mHotReviewList.clear();
        mHotReviewList.addAll(list);
        mHotReviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRecommendBookList(List<RecommendBookList.RecommendBook> list) {
        if (!list.isEmpty()) {
            mTvRecommendBookList.setVisibility(View.VISIBLE);
            mRecommendBookList.clear();
            mRecommendBookList.addAll(list);
            mRecommendBookListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        if (data instanceof HotReview.Reviews) {
            BookDiscussionDetailActivity.startActivity(this, ((HotReview.Reviews)data)._id);
        } else if (data instanceof RecommendBookList.RecommendBook) {
            String id = ((RecommendBookList.RecommendBook) data).id;
            SubjectBookListDetailActivity.startActivity(this, id);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnRead)
    public void onClickRead() {
        startActivity(new Intent(this, BookReadActivity.class)
                .putExtra("bookId", bookId));
    }

    @OnClick(R.id.tvBookListAuthor)
    public void searchByAuthor() {
        String author = mTvAuthor.getText().toString().replaceAll(" ", "");
        // TODO search
    }

    @OnClick(R.id.tvlongIntro)
    public void collapseLongIntro() {
        if (collapseLongIntro) {
            mTvlongIntro.setMaxLines(20);
            collapseLongIntro = false;
        } else {
            mTvlongIntro.setMaxLines(4);
            collapseLongIntro = true;
        }
    }

    @OnClick(R.id.tvMoreReview)
    public void onClickMoreReview() {
        BookDetailCommunityActivity.startActivity(this,bookId,mTvBookTitle.getText().toString(),1);
    }

    @OnClick(R.id.rlCommunity)
    public void onClickCommunity() {
        BookDetailCommunityActivity.startActivity(this,bookId,mTvBookTitle.getText().toString(),0);
    }

}
