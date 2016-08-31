package com.justwayward.reader.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerRecommendFragmentComponent;
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.adapter.RecommendAdapter;
import com.justwayward.reader.ui.contract.RecommendContract;
import com.justwayward.reader.ui.presenter.RecommendPresenter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class RecommendFragment extends BaseFragment implements RecommendContract.View,
        OnRvItemClickListener<Recommend.RecommendBooks> {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    RecommendPresenter mPresenter;

    private RecommendAdapter mAdapter;
    private List<Recommend.RecommendBooks> mList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.getRecommendList();
            }
        });

        mAdapter = new RecommendAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.getRecommendList();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRecommendFragmentComponent.builder()
                .appComponent(appComponent)
                //.recommendFragmentModule(new RecommendFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showRecommendList(List<Recommend.RecommendBooks> list) {
        mList.clear();
        mList.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position, Recommend.RecommendBooks data) {
        startActivity(new Intent(activity, BookReadActivity.class)
                .putExtra("bookId", data._id).putExtra("bookName", data.title));
    }

}
