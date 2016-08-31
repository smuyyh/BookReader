package com.justwayward.reader.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.bean.FindBean;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.ui.activity.TopCategoryListActivity;
import com.justwayward.reader.ui.adapter.FindAdapter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class FindFragment extends BaseFragment implements
        OnRvItemClickListener<FindBean> {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private FindAdapter mAdapter;
    private List<FindBean> mList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initDatas() {
        mList.clear();
        mList.add(new FindBean("排行榜", R.drawable.home_find_rank));
        mList.add(new FindBean("主题书单", R.drawable.home_find_topic));
        mList.add(new FindBean("分类", R.drawable.home_find_category));
        mList.add(new FindBean("有声小说", R.drawable.home_find_listen));
    }

    @Override
    public void configViews() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));

        mAdapter = new FindAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onItemClick(View view, int position, FindBean data) {
        switch (position) {
            case 0:
                // startActivity(new Intent(activity, TopCategoryListActivity.class));
                break;
            case 1:
                //  startActivity(new Intent(activity, TopCategoryListActivity.class));
                break;
            case 2:
                startActivity(new Intent(activity, TopCategoryListActivity.class));
                break;
            case 3:
                break;
            default:
                break;
        }
    }

}
