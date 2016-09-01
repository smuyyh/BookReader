package com.justwayward.reader.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubCategoryFragmentComponent;
import com.justwayward.reader.ui.activity.BookDetailActivity;
import com.justwayward.reader.ui.adapter.SubCategoryAdapter;
import com.justwayward.reader.ui.contract.SubRankContract;
import com.justwayward.reader.ui.presenter.SubRankPresenter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubRankFragment extends BaseFragment implements SubRankContract.View, OnRvItemClickListener<BooksByCats.BooksBean>{

    public final static String BUNDLE_ID = "_id";

    public static SubRankFragment newInstance(String id) {
        SubRankFragment fragment = new SubRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SubCategoryAdapter mAdapter;
    private List<BooksByCats.BooksBean> mList = new ArrayList<>();

    private String id;

    @Inject
    SubRankPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initDatas() {
        id = getArguments().getString(BUNDLE_ID);
    }

    @Override
    public void configViews() {
        showDialog();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //mPresenter.getCategoryList(cate, gender);
            }
        });

        mAdapter = new SubCategoryAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.getRankList(id);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubCategoryFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onItemClick(View view, int position, BooksByCats.BooksBean data) {
        BookDetailActivity.startActivity(activity, data._id);
    }


    @Override
    public void showCategoryList(BooksByCats data) {
        mList.clear();
        mList.addAll(data.books);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete() {
        dismissDialog();
    }
}
