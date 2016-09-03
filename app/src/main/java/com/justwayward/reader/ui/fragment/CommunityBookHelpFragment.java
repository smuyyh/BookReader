package com.justwayward.reader.ui.fragment;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.HelpList;
import com.justwayward.reader.bean.support.SelectionEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerCommunityComponent;
import com.justwayward.reader.ui.activity.ComOverallDetailActivity;
import com.justwayward.reader.ui.contract.ComBookHelpContract;
import com.justwayward.reader.ui.easyadapter.CommunityBookHelpAdapter;
import com.justwayward.reader.ui.presenter.ComBookHelpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class CommunityBookHelpFragment extends BaseRVFragment implements ComBookHelpContract.View{

    private CommunityBookHelpAdapter mAdapter;

    @Inject
    ComBookHelpPresenter mPresenter;

    private String sort = Constant.SortType.DEFAULT;
    private String distillate = Constant.Distillate.ALL;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        mAdapter = new CommunityBookHelpAdapter(mContext);
        modiifyAdapter(true, true);

        mPresenter.attachView(this);
        onRefresh();
    }

    @Override
    public void showHelpList(List<HelpList.HelpsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        sort = event.sort;
        distillate = event.distillate;
        start = 0;
        limit = 20;
        mPresenter.getHelpList(sort, distillate, start, limit);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getHelpList(sort, distillate, start, limit);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getHelpList(sort, distillate, start, limit);
    }

    @Override
    public void onItemClick(int position) {
        HelpList.HelpsBean data = (HelpList.HelpsBean) mAdapter.getItem(position);
        ComOverallDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
