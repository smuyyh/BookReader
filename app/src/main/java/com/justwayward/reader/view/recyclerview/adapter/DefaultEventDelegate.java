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
package com.justwayward.reader.view.recyclerview.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.justwayward.reader.view.recyclerview.EasyRecyclerView;


/**
 * Created by Mr.Jude on 2015/8/18.
 */
public class DefaultEventDelegate implements EventDelegate {
    private RecyclerArrayAdapter adapter;
    private EventFooter footer ;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean hasData = false;
    private boolean isLoadingMore = false;

    private boolean hasMore = false;
    private boolean hasNoMore = false;
    private boolean hasError = false;

    private int status = STATUS_INITIAL;
    private static final int STATUS_INITIAL = 291;
    private static final int STATUS_MORE = 260;
    private static final int STATUS_NOMORE = 408;
    private static final int STATUS_ERROR = 732;

    public DefaultEventDelegate(RecyclerArrayAdapter adapter) {
        this.adapter = adapter;
        footer = new EventFooter();
        adapter.addFooter(footer);
    }

    public void onMoreViewShowed() {
        log("onMoreViewShowed");
        if (!isLoadingMore&&onLoadMoreListener!=null){
            isLoadingMore = true;
            onLoadMoreListener.onLoadMore();
        }
    }

    public void onErrorViewShowed() {
        resumeLoadMore();
    }

    //-------------------5个状态触发事件-------------------
    @Override
    public void addData(int length) {
        log("addData" + length);
        if (hasMore){
            if (length == 0){
                //当添加0个时，认为已结束加载到底
                if (status==STATUS_INITIAL || status == STATUS_MORE){
                    footer.showNoMore();
                }
            }else {
                //当Error或初始时。添加数据，如果有More则还原。
                if (hasMore && (status == STATUS_INITIAL || status == STATUS_ERROR)){
                    footer.showMore();
                }
                hasData = true;
            }
        }else{
            if (hasNoMore){
                footer.showNoMore();
                status = STATUS_NOMORE;
            }
        }
        isLoadingMore = false;
    }

    @Override
    public void clear() {
        log("clear");
        hasData = false;
        status = STATUS_INITIAL;
        footer.hide();
        isLoadingMore = false;
    }

    @Override
    public void stopLoadMore() {
        log("stopLoadMore");
        footer.showNoMore();
        status = STATUS_NOMORE;
        isLoadingMore = false;
    }

    @Override
    public void pauseLoadMore() {
        log("pauseLoadMore");
        footer.showError();
        status = STATUS_ERROR;
        isLoadingMore = false;
    }

    @Override
    public void resumeLoadMore() {
        isLoadingMore = false;
        footer.showMore();
        onMoreViewShowed();
    }

    //-------------------3种View设置-------------------

    @Override
    public void setMore(View view, OnLoadMoreListener listener) {
        this.footer.setMoreView(view);
        this.onLoadMoreListener = listener;
        hasMore = true;
        log("setMore");
    }

    @Override
    public void setNoMore(View view) {
        this.footer.setNoMoreView(view);
        hasNoMore = true;
        log("setNoMore");
    }

    @Override
    public void setErrorMore(View view) {
        this.footer.setErrorView(view);
        hasError = true;
        log("setErrorMore");
    }


    private class EventFooter implements RecyclerArrayAdapter.ItemView {
        private FrameLayout container;
        private View moreView;
        private View noMoreView;
        private View errorView;

        private int flag = Hide;
        public static final int Hide = 0;
        public static final int ShowMore = 1;
        public static final int ShowError = 2;
        public static final int ShowNoMore = 3;


        public EventFooter(){
            container = new FrameLayout(adapter.getContext());
            container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            log("onCreateView");
            return container;
        }

        @Override
        public void onBindView(View headerView) {
            log("onBindView");
            switch (flag){
                case ShowMore:
                    onMoreViewShowed();
                    break;
                case ShowError:
                    onErrorViewShowed();
                    break;
            }
        }

        public void refreshStatus(){
            if (container!=null){
                if (flag == Hide){
                    container.setVisibility(View.GONE);
                    return;
                }
                if (container.getVisibility() != View.VISIBLE)container.setVisibility(View.VISIBLE);
                View view = null;
                switch (flag){
                    case ShowMore:view = moreView;break;
                    case ShowError:view = errorView;break;
                    case ShowNoMore:view = noMoreView;break;
                }
                if (view == null){
                    hide();
                    return;
                }
                if (view.getParent()==null)container.addView(view);
                for (int i = 0; i < container.getChildCount(); i++) {
                    if (container.getChildAt(i) == view)view.setVisibility(View.VISIBLE);
                    else container.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

        public void showError(){
            flag = ShowError;
            refreshStatus();
        }
        public void showMore(){
            flag = ShowMore;
            refreshStatus();
        }
        public void showNoMore(){
            flag = ShowNoMore;
            refreshStatus();
        }

        //初始化
        public void hide(){
            flag = Hide;
            refreshStatus();
        }

        public void setMoreView(View moreView) {
            this.moreView = moreView;
        }

        public void setNoMoreView(View noMoreView) {
            this.noMoreView = noMoreView;
        }

        public void setErrorView(View errorView) {
            this.errorView = errorView;
        }
    }

    private static void log(String content){
        if (EasyRecyclerView.DEBUG){
            Log.i(EasyRecyclerView.TAG,content);
        }
    }
}
