package com.justwayward.reader.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookReviewList;
import com.justwayward.reader.bean.support.SelectionEvent;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerCommunityComponent;
import com.justwayward.reader.ui.activity.ComOverallDetailActivity;
import com.justwayward.reader.ui.adapter.CommunityBookReviewAdapter;
import com.justwayward.reader.ui.contract.ComBookReviewContract;
import com.justwayward.reader.ui.presenter.ComBookReviewPresenter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * 书评区Fragment
 * @author lfh.
 * @date 16/9/3.
 */
public class CommunityBookReviewFragment extends BaseFragment implements ComBookReviewContract.View, OnRvItemClickListener<BookReviewList.ReviewsBean> {


    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<BookReviewList.ReviewsBean> mList = new ArrayList<>();
    private CommunityBookReviewAdapter mAdapter;

    @Inject
    ComBookReviewPresenter mPresenter;

    private String sort = Constant.SortType.DEFAULT;
    private String type = Constant.BookType.ALL;
    private String distillate = Constant.Distillate.ALL;

    private int start = 0;
    private int limit = 20;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
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
                start = 0;
                limit = 20;
                mPresenter.getBookReviewList(sort,type,distillate, start, limit);
            }
        });

        mAdapter = new CommunityBookReviewAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.getBookReviewList(sort,type,distillate, start, limit);
    }

    @Override
    public void showBookReviewList(List<BookReviewList.ReviewsBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        sort = event.sort;
        type = event.type;
        distillate = event.distillate;
        start = 0;
        limit = 20;
        mPresenter.getBookReviewList(sort,type, distillate, start, limit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(View view, int position, BookReviewList.ReviewsBean data) {
        ComOverallDetailActivity.startActivity(activity, data._id);
    }
}
