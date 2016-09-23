package com.justwayward.reader.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerMainComponent;
import com.justwayward.reader.manager.CollectionsManager;
import com.justwayward.reader.ui.activity.BookDetailActivity;
import com.justwayward.reader.ui.activity.ReadActivity;
import com.justwayward.reader.ui.contract.RecommendContract;
import com.justwayward.reader.ui.easyadapter.RecommendAdapter;
import com.justwayward.reader.ui.presenter.RecommendPresenter;
import com.justwayward.reader.utils.SharedPreferencesUtil;
import com.justwayward.reader.utils.ToastUtils;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

public class RecommendFragment extends BaseRVFragment<RecommendPresenter, Recommend.RecommendBooks> implements RecommendContract.View, RecyclerArrayAdapter.OnItemLongClickListener {

    private TableLayout shelf;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {
        initAdapter(RecommendAdapter.class, true, false);
        mAdapter.setOnItemLongClickListener(this);
        //initCollect();
        onRefresh();
    }

    private void initCollect() {

        final List<Recommend.RecommendBooks> collect = SharedPreferencesUtil.getInstance().getObject("collect", List.class);
        if (collect != null) {
            mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup parent) {
                    View headerView = LayoutInflater.from(activity).inflate(R.layout.header_view_shelf, parent, false);
                    shelf = (TableLayout) headerView.findViewById(R.id.tblLayout);
                    return headerView;
                }

                @Override
                public void onBindView(View headerView) {
                    TableRow tblRow = new TableRow(activity);
                    tblRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    tblRow.setBackgroundColor(Color.GRAY);

                    for (Recommend.RecommendBooks books : collect) {
                        ImageView iv = new ImageView(activity);
                        tblRow.addView(iv);
                        Glide.with(activity).load(Constant.IMG_BASE_URL + books.cover).into(iv);
                    }

                    shelf.addView(tblRow, 0);

                }
            });
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showRecommendList(List<Recommend.RecommendBooks> list) {
        mAdapter.clear();
        List<Recommend.RecommendBooks> data = CollectionsManager.getInstance().getCollectionList();
        if (data != null && !data.isEmpty()) {
            list.addAll(0, data);
        }
        mAdapter.addAll(list);
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(activity, ReadActivity.class)
                .putExtra("bookId", mAdapter.getItem(position)._id)
                .putExtra("bookName", mAdapter.getItem(position).title));
    }

    @Override
    public boolean onItemLongClick(int position) {
        showLongClickDialog(position);
        return false;
    }

    /**
     * 显示长按对话框
     * @param position
     */
    private void showLongClickDialog(final int position) {
        new AlertDialog.Builder(getSupportActivity())
                .setTitle(mAdapter.getItem(position).title)
                .setItems(getResources().getStringArray(R.array.recommend_item_long_click_choice),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //置顶
                                        ToastUtils.showToast("正在拼命开发中...");
                                        break;
                                    case 1:
                                        //书籍详情
                                        BookDetailActivity.startActivity(getSupportActivity(),
                                                mAdapter.getItem(position)._id);
                                        break;
                                    case 2:
                                        //移入养肥区
                                        ToastUtils.showToast("正在拼命开发中...");
                                        break;
                                    case 3:
                                        //缓存全本
                                        //TODO 缓存全本
                                        ToastUtils.showToast("正在拼命开发中...");
                                        break;
                                    case 4:
                                        //删除
                                        showDeleteCacheDialog(position);
                                        break;
                                    case 5:
                                        //批量管理
                                        ToastUtils.showToast("正在拼命开发中...");
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

    /**
     * 显示删除本地缓存对话框
     * @param position
     */
    private void showDeleteCacheDialog(final int position) {
        final boolean selected[] = {true};
        new AlertDialog.Builder(getSupportActivity())
                .setTitle(activity.getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{activity.getString(R.string.delete_local_cache)}, selected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                .setPositiveButton(activity.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (selected[0]) {
                            //TODO 删除本地缓存
                        }
                        CollectionsManager.getInstance().remove(mAdapter.getItem(position)._id);
                        mAdapter.remove(position);
                    }
                })
                .setNegativeButton(activity.getString(R.string.cancel), null)
                .create().show();
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getRecommendList();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

}
