package com.yuyh.easyadapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuyh.easyadapter.helper.DataHelper;

import java.util.List;

/**
 * https://github.com/smuyyh/EasyAdapter
 *
 * @author yuyh.
 * @date 2016/7/21.
 */
public abstract class EasyRVAdapter<T> extends RecyclerView.Adapter<EasyRVHolder> implements DataHelper<T> {

    protected Context mContext;
    protected List<T> mList;
    protected int[] layoutIds;
    protected LayoutInflater mLInflater;

    private SparseArray<View> mConvertViews = new SparseArray<>();

    public EasyRVAdapter(Context context, List<T> list, int... layoutIds) {
        this.mContext = context;
        this.mList = list;
        this.layoutIds = layoutIds;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public EasyRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType > layoutIds.length) {
            throw new ArrayIndexOutOfBoundsException("layoutIndex");
        }
        if (layoutIds.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = layoutIds[viewType];
        View view = mConvertViews.get(layoutId);
        if (view == null) {
            view = mLInflater.inflate(layoutId, parent, false);
        }
        EasyRVHolder viewHolder = (EasyRVHolder) view.getTag();
        if (viewHolder == null || viewHolder.getLayoutId() != layoutId) {
            viewHolder = new EasyRVHolder(mContext, layoutId, view);
            return viewHolder;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EasyRVHolder holder, int position) {
        final T item = mList.get(position);
        onBindData(holder, position, item);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIndex(position, mList.get(position));
    }

    /**
     * 指定item布局样式在layoutIds的索引。默认为第一个
     *
     * @param position
     * @param item
     * @return
     */
    public int getLayoutIndex(int position, T item) {
        return 0;
    }

    protected abstract void onBindData(EasyRVHolder viewHolder, int position, T item);

    @Override
    public boolean addAll(List<T> list) {
        boolean result = mList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public boolean addAll(int position, List list) {
        boolean result = mList.addAll(position, list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void add(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void add(int position, T data) {
        mList.add(position, data);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean contains(T data) {
        return mList.contains(data);
    }

    @Override
    public T getData(int index) {
        return mList.get(index);
    }

    @Override
    public void modify(T oldData, T newData) {
        modify(mList.indexOf(oldData), newData);
    }

    @Override
    public void modify(int index, T newData) {
        mList.set(index, newData);
        notifyDataSetChanged();
    }

    @Override
    public boolean remove(T data) {
        boolean result = mList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }
}
