package com.justwayward.reader.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
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
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.contract.RecommendContract;
import com.justwayward.reader.ui.easyadapter.RecommendAdapter;
import com.justwayward.reader.ui.presenter.RecommendPresenter;
import com.justwayward.reader.utils.SharedPreferencesUtil;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

public class RecommendFragment extends BaseRVFragment<RecommendPresenter, Recommend.RecommendBooks> implements RecommendContract.View {

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
        mAdapter.addAll(list);

        //SharedPreferencesUtil.getInstance().putObject("collect", list);
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(activity, BookReadActivity.class)
                .putExtra("bookId", mAdapter.getItem(position)._id)
                .putExtra("bookName", mAdapter.getItem(position).title));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
