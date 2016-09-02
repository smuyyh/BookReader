package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.bean.Disscussion;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerCommunityComponent;
import com.justwayward.reader.ui.adapter.BestCommentListAdapter;
import com.justwayward.reader.ui.adapter.CommentListAdapter;
import com.justwayward.reader.ui.contract.ComOverallDetailContract;
import com.justwayward.reader.ui.presenter.ComOverallDetailPresenter;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.RelativeDateFormat;
import com.justwayward.reader.view.BookContentTextView;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ComOverallDetailActivity extends BaseActivity implements ComOverallDetailContract
        .View, OnRvItemClickListener<CommentList.CommentsBean> {

    private static final String INTENT_ID = "id";

    public static void startActivity(Context context, String id) {
        context.startActivity(new Intent(context, ComOverallDetailActivity.class)
                .putExtra(INTENT_ID, id));
    }

    @Bind(R.id.ivBookCover)
    ImageView ivAvatar;
    @Bind(R.id.tvBookTitle)
    TextView tvNickName;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    BookContentTextView tvContent;
    @Bind(R.id.tvBestComments)
    TextView tvBestComments;
    @Bind(R.id.rvBestComments)
    RecyclerView rvBestComments;
    @Bind(R.id.tvCommentCount)
    TextView tvCommentCount;
    @Bind(R.id.rvComments)
    RecyclerView rvComments;

    private String id;

    @Inject
    ComOverallDetailPresenter mPresenter;

    private List<CommentList.CommentsBean> mBestCommentList = new ArrayList<>();
    private BestCommentListAdapter mBestCommentListAdapter;

    private List<CommentList.CommentsBean> mCommentList = new ArrayList<>();
    private CommentListAdapter mCommentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_com_overall_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("详情");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        showDialog();
        id = getIntent().getStringExtra(INTENT_ID);

        mPresenter.attachView(this);
        mPresenter.getDisscussionDetail(id);
        mPresenter.getBestComments(id);
        mPresenter.getDisscussionComments(id,"0","20");
    }

    @Override
    public void configViews() {
        rvBestComments.setHasFixedSize(true);
        rvBestComments.setLayoutManager(new LinearLayoutManager(this));
        rvBestComments.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
        mBestCommentListAdapter = new BestCommentListAdapter(mContext, mBestCommentList);
        mBestCommentListAdapter.setOnItemClickListener(this);
        rvBestComments.setAdapter(mBestCommentListAdapter);

        rvComments.setHasFixedSize(true);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
        mCommentAdapter = new CommentListAdapter(mContext, mCommentList);
        mCommentAdapter.setOnItemClickListener(this);
        rvComments.setAdapter(mCommentAdapter);
    }

    @Override
    public void showDisscussion(Disscussion disscussion) {
        Glide.with(mContext).load(Constant.IMG_BASE_URL + disscussion.post.author.avatar)
                .placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(ivAvatar);

        tvNickName.setText(disscussion.post.author.nickname);
        tvTime.setText(RelativeDateFormat.format(disscussion.post.created));
        tvTitle.setText(disscussion.post.title);
        tvContent.setText(disscussion.post.content);
    }

    @Override
    public void showBestComments(CommentList list) {
        if(list.comments.isEmpty()){
            gone(tvBestComments,rvBestComments);
        }else{
            visible(tvBestComments,rvBestComments);
            mBestCommentList.clear();
            mBestCommentList.addAll(list.comments);
            mBestCommentListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showDisscussionComments(CommentList list) {
        tvCommentCount.setText(String.format(mContext.getString(R.string.comment_comment_count), list.comments.size()));
        mCommentList.clear();
        mCommentList.addAll(list.comments);
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void onItemClick(View view, int position, CommentList.CommentsBean data) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
