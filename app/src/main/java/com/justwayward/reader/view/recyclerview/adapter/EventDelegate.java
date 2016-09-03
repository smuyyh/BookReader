package com.justwayward.reader.view.recyclerview.adapter;

import android.view.View;

/**
 * Created by Mr.Jude on 2015/8/18.
 */
public interface EventDelegate {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, OnLoadMoreListener listener);
    void setNoMore(View view);
    void setErrorMore(View view);
}
