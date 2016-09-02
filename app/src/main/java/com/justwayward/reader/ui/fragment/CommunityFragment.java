package com.justwayward.reader.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.bean.support.FindBean;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.ui.activity.ComBookCommActivity;
import com.justwayward.reader.ui.activity.ComOverallActivity;
import com.justwayward.reader.ui.activity.ComHelpActivity;
import com.justwayward.reader.ui.adapter.FindAdapter;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CommunityFragment extends BaseFragment implements OnRvItemClickListener<FindBean> {

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
        mList.add(new FindBean("综合讨论区", R.drawable.discuss_section));
        mList.add(new FindBean("书评区", R.drawable.comment_section));
        mList.add(new FindBean("书荒互助区", R.drawable.helper_section));
        mList.add(new FindBean("女生区", R.drawable.girl_section));
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
                ComOverallActivity.startActivity(activity);
                break;
            case 1:
                ComBookCommActivity.startActivity(activity);
                break;
            case 2:
                ComHelpActivity.startActivity(activity);
                break;
            case 3:
                break;
            default:
                break;
        }
    }

}
