package com.justwayward.reader.base;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.utils.NetworkUtils;
import com.justwayward.reader.view.recyclerview.EasyRecyclerView;
import com.justwayward.reader.view.recyclerview.adapter.OnLoadMoreListener;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.justwayward.reader.view.recyclerview.swipe.OnRefreshListener;

import butterknife.Bind;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public abstract class BaseRVFragment<T> extends BaseFragment implements OnLoadMoreListener, OnRefreshListener,RecyclerArrayAdapter.OnItemClickListener {

    @Bind(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;
    protected RecyclerArrayAdapter<T> mAdapter;

    protected int start = 0;
    protected int limit = 20;

    protected void modiifyAdapter(boolean refreshable, boolean loadmoreable) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
            mRecyclerView.setItemDecoration(R.color.common_divider_narrow, 1,0,0);
            mRecyclerView.setAdapterWithProgress(mAdapter);
        }
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);
            mAdapter.setError(R.layout.common_error_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.resumeMore();

                }
            });
            if (loadmoreable) {
                mAdapter.setMore(R.layout.common_more_view, this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshable && mRecyclerView != null) {
                mRecyclerView.setRefreshListener(this);
            }
        }
    }

    @Override
    public void onLoadMore() {
        if(!NetworkUtils.isConnected(getApplicationContext())){
            mAdapter.pauseMore();
            return;
        }
    }

    @Override
    public void onRefresh() {
        start = 0;
        if(!NetworkUtils.isConnected(getApplicationContext())){
            mAdapter.pauseMore();
            return;
        }
    }
}
