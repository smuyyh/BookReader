package com.yuyh.easyadapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/20.
 */
public abstract class BaseEasyAdapter<T> extends BaseAdapter {

    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mLInflater;
    protected int[] layoutIds;



}
