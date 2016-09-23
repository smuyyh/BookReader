package com.justwayward.reader.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVActivity;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.manager.MyBookListsManager;
import com.justwayward.reader.ui.easyadapter.SubjectBookListAdapter;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * 我的书单
 * Created by lfh on 2016/9/23.
 */
public class MyBookListActivity extends BaseRVActivity<BookLists.BookListsBean> implements RecyclerArrayAdapter.OnItemLongClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_book_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle(R.string.subject_book_list_my_book_list);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        initAdapter(SubjectBookListAdapter.class, true, false);
        mAdapter.setOnItemLongClickListener(this);
        onRefresh();
    }

    @Override
    public void onItemClick(int position) {
        SubjectBookListDetailActivity.startActivity(this, mAdapter.getItem(position));
    }

    @Override
    public boolean onItemLongClick(int position) {
        showLongClickDialog(position);
        return false;
    }

    /**
     * 显示长按对话框
     *
     * @param position
     */
    private void showLongClickDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(mAdapter.getItem(position).title)
                .setItems(getResources().getStringArray(R.array.my_book_list_item_long_click_choice),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //删除
                                        MyBookListsManager.getInstance().remove(mAdapter.getItem(position)._id);
                                        mAdapter.remove(position);
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(null, null)
                .create().show();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        List<BookLists.BookListsBean> data = MyBookListsManager.getInstance().getCollectionList();
        mAdapter.clear();
        mAdapter.addAll(data);
        mRecyclerView.setRefreshing(false);
    }


}
