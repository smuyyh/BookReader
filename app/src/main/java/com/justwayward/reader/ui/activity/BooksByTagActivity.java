/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.ui.adapter.BooksByTagAdapter;
import com.justwayward.reader.ui.contract.BooksByTagContract;
import com.justwayward.reader.ui.presenter.BooksByTagPresenter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/7.
 */
public class BooksByTagActivity extends BaseActivity implements BooksByTagContract.View,
        OnRvItemClickListener<BooksByTag.TagBook> {

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Inject
    BooksByTagPresenter mPresenter;

    private BooksByTagAdapter mAdapter;
    private List<BooksByTag.TagBook> mList = new ArrayList<>();

    private String tag;
    private int current = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_books_by_tag;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle(getIntent().getStringExtra("tag"));
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        tag = getIntent().getStringExtra("tag");
    }

    @Override
    public void configViews() {
        refreshLayout.setOnRefreshListener(new RefreshListener());

        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new BooksByTagAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RefreshListener());

        mPresenter.attachView(this);
        mPresenter.getBooksByTag(tag, current + "", (current + 10) + "");
    }


    @Override
    public void showBooksByTag(List<BooksByTag.TagBook> list, boolean isRefresh) {
        if (isRefresh)
            mList.clear();
        mList.addAll(list);
        current = mList.size();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadComplete(boolean isSuccess, String msg) {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position, BooksByTag.TagBook data) {
        startActivity(new Intent(BooksByTagActivity.this, BookDetailActivity.class)
                .putExtra("bookId", data._id));
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        refreshLayout.setRefreshing(false);
    }

    private class RefreshListener extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            current = 0;
            mPresenter.getBooksByTag(tag, current + "", "10");
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) { // 滑到倒数第二项就加载更多

                boolean isRefreshing = refreshLayout.isRefreshing();
                if (isRefreshing) {
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    return;
                }
                mPresenter.getBooksByTag(tag, current + "", "10");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
