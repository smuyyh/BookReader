package com.justwayward.reader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.bean.support.SubEvent;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubCategoryFragmentComponent;
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.adapter.SubCategoryAdapter;
import com.justwayward.reader.ui.contract.SubCategoryFragmentContract;
import com.justwayward.reader.ui.presenter.SubCategoryFragmentPresenter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SubCategoryFragment extends BaseFragment implements SubCategoryFragmentContract.View,
        OnRvItemClickListener<Recommend.RecommendBooks> {

    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    public static SubCategoryFragment newInstance(String major, String minor, String gender,
                                                  @Constant.CateType String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    SubCategoryFragmentPresenter mPresenter;

    private SubCategoryAdapter mAdapter;
    private List<BooksByCats.BooksBean> mList = new ArrayList<>();

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        major = getArguments().getString(BUNDLE_MAJOR);
        gender = getArguments().getString(BUNDLE_GENDER);
        minor = getArguments().getString(BUNDLE_MINOR);
        type = getArguments().getString(BUNDLE_TYPE);
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
                //mPresenter.getCategoryList(cate, gender);
            }
        });

        mAdapter = new SubCategoryAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubCategoryFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onItemClick(View view, int position, Recommend.RecommendBooks data) {
        startActivity(new Intent(activity, BookReadActivity.class)
                .putExtra("bookId", data._id)
                .putExtra("bookName", data.title));
    }

    @Override
    public void showCategoryList(BooksByCats data) {
        mList.clear();
        mList.addAll(data.books);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SubEvent event) {
        minor = event.minor;
        String type = event.type;
        if (this.type.equals(type)) {
            mPresenter.getCategoryList(gender, major, minor, this.type, 0);
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
