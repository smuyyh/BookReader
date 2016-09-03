package com.justwayward.reader.base;

import android.support.v7.widget.LinearLayoutManager;

import com.justwayward.reader.R;
import com.justwayward.reader.view.recyclerview.EasyRecyclerView;
import com.justwayward.reader.view.recyclerview.adapter.OnLoadMoreListener;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.justwayward.reader.view.recyclerview.swipe.OnRefreshListener;

import butterknife.Bind;

/**
 * @author yuyh.
 * @date 16/9/3.
 */
public abstract class BaseRVActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {


    @Bind(R.id.recyclerview)
    public EasyRecyclerView mRecyclerView;

    protected RecyclerArrayAdapter mAdapter;

    protected void modiifyAdapter(boolean refreshable, boolean loadmoreable) {
        if (mAdapter != null) {
            if (loadmoreable) {
                mAdapter.setMore(R.layout.common_more_view, this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshable && mRecyclerView != null) {
                mRecyclerView.setRefreshListener(this);
            }
        }
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemDecoration(R.color.common_divider_narrow, 1,0,0);
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
