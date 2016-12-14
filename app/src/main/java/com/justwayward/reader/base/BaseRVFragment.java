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
package com.justwayward.reader.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.view.recyclerview.EasyRecyclerView;
import com.justwayward.reader.view.recyclerview.adapter.OnLoadMoreListener;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.justwayward.reader.view.recyclerview.swipe.OnRefreshListener;

import java.lang.reflect.Constructor;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public abstract class BaseRVFragment<T1 extends BaseContract.BasePresenter, T2> extends BaseFragment implements OnLoadMoreListener, OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @Inject
    protected T1 mPresenter;

    @Bind(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;
    protected RecyclerArrayAdapter<T2> mAdapter;

    protected int start = 0;
    protected int limit = 20;

    /**
     * [此方法不可再重写]
     */
    @Override
    public void attachView() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected void initAdapter(boolean refreshable, boolean loadmoreable) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
            mRecyclerView.setItemDecoration(ContextCompat.getColor(activity, R.color.common_divider_narrow), 1, 0, 0);
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

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T2>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter<T2>) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
    }

    public Object createInstance(Class<?> cls) {
        Object obj;
        try {
            Constructor c1 = cls.getDeclaredConstructor(Context.class);
            c1.setAccessible(true);
            obj = c1.newInstance(mContext);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefresh() {
        mRecyclerView.setRefreshing(true);
    }

    protected void loaddingError() {
        if (mAdapter.getCount() < 1) { // 说明缓存也没有加载，那就显示errorview，如果有缓存，即使刷新失败也不显示error
            mAdapter.clear();
        }
        mAdapter.pauseMore();
        mRecyclerView.setRefreshing(false);
        mRecyclerView.showTipViewAndDelayClose("似乎没有网络哦");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
