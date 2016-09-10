package com.justwayward.reader.ui.fragment;

import android.os.Bundle;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerFindComponent;
import com.justwayward.reader.ui.activity.BookDetailActivity;
import com.justwayward.reader.ui.contract.SubRankContract;
import com.justwayward.reader.ui.easyadapter.SubCategoryAdapter;
import com.justwayward.reader.ui.presenter.SubRankPresenter;

/**
 * 二级排行榜
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubRankFragment extends BaseRVFragment<SubRankPresenter,BooksByCats.BooksBean> implements SubRankContract.View {

    public final static String BUNDLE_ID = "_id";

    public static SubRankFragment newInstance(String id) {
        SubRankFragment fragment = new SubRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        id = getArguments().getString(BUNDLE_ID);
    }

    @Override
    public void configViews() {
        initAdapter(SubCategoryAdapter.class, true, false);

        mPresenter.attachView(this);
        mPresenter.getRankList(id);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showCategoryList(BooksByCats data) {
        mAdapter.clear();
        mAdapter.addAll(data.books);
    }

    @Override
    public void complete() {
    }

    @Override
    public void onItemClick(int position) {
        BookDetailActivity.startActivity(activity, mAdapter.getItem(position)._id);
    }

    @Override
    public void onRefresh() {
        mPresenter.getRankList(id);
    }
}
