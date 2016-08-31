package com.justwayward.reader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.bean.support.TagEvent;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubjectFragmentComponent;
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.adapter.SubjectBookListAdapter;
import com.justwayward.reader.ui.contract.SubjectFragmentContract;
import com.justwayward.reader.ui.presenter.SubjectFragmentPresenter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SubjectFragment extends BaseFragment implements SubjectFragmentContract.View,
        OnRvItemClickListener<BookLists.BookListsBean> {

    public final static String BUNDLE_TAG = "tag";
    public final static String BUNDLE_TAB = "tab";

    public String currendTag;
    public int currentTab;

    public String duration = "";
    public String sort  = "";

    public static SubjectFragment newInstance(String tag, int tab) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TAG, tag);
        bundle.putInt(BUNDLE_TAB, tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    SubjectFragmentPresenter mPresenter;

    private SubjectBookListAdapter mAdapter;
    private List<BookLists.BookListsBean> mList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);

        currentTab = getArguments().getInt(BUNDLE_TAB);
        switch (currentTab) {
            case 0:
                duration = "last-seven-days";
                sort = "collectorCount";
                break;
            case 1:
                duration = "all";
                sort = "created";
                break;
            case 2:
            default:
                duration = "all";
                sort = "collectorCount";
                break;
        }
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

        mAdapter = new SubjectBookListAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        showDialog();
        mPresenter.attachView(this);
        mPresenter.getBookLists(duration, sort, 0, 20, currendTag, "male");
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onItemClick(View view, int position, BookLists.BookListsBean data) {
        startActivity(new Intent(activity, BookReadActivity.class)
                .putExtra("bookId", data._id)
                .putExtra("bookName", data.title));
    }

    @Override
    public void showBookList(List<BookLists.BookListsBean> bookLists) {
        mList.clear();
        mList.addAll(bookLists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(TagEvent event) {
        currendTag = event.tag;
        showDialog();
        mPresenter.getBookLists(duration, sort, 0, 20, currendTag, "male");
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
