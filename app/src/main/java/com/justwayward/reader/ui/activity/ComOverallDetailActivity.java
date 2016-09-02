package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Disscussion;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerCommunityComponent;
import com.justwayward.reader.ui.contract.ComOverallDetailContract;
import com.justwayward.reader.ui.presenter.ComOverallDetailPresenter;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.RelativeDateFormat;

import javax.inject.Inject;

import butterknife.Bind;

public class ComOverallDetailActivity extends BaseActivity implements ComOverallDetailContract.View {

    private static final String INTENT_ID = "id";

    public static void startActivity(Context context, String id) {
        context.startActivity(new Intent(context, ComOverallDetailActivity.class)
                .putExtra(INTENT_ID, id));
    }

    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvNickName)
    TextView tvNickName;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;

    private String id;

    @Inject
    ComOverallDetailPresenter mPresenter;

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
        id = getIntent().getStringExtra(INTENT_ID);

        mPresenter.attachView(this);
        mPresenter.getDisscussionDetail(id);
    }

    @Override
    public void configViews() {


    }

    @Override
    public void showDisscussion(Disscussion disscussion) {
        Glide.with(mContext).load(Constant.IMG_BASE_URL + disscussion.post.author.avatar).placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(ivAvatar);

        tvNickName.setText(disscussion.post.author.nickname);
        tvTime.setText(RelativeDateFormat.format(disscussion.post.created));
        tvTitle.setText(disscussion.post.title);
        tvContent.setText(disscussion.post.content);
    }

    @Override
    public void complete() {

    }
}
