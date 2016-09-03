package com.justwayward.reader.ui.fragment;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookReviewList;
import com.justwayward.reader.bean.support.SelectionEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerCommunityComponent;
import com.justwayward.reader.ui.activity.ComOverallDetailActivity;
import com.justwayward.reader.ui.contract.ComBookReviewContract;
import com.justwayward.reader.ui.easyadapter.CommunityBookReviewAdapter;
import com.justwayward.reader.ui.presenter.ComBookReviewPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * 书评区Fragment
 * @author lfh.
 * @date 16/9/3.
 */
public class CommunityBookReviewFragment extends BaseRVFragment<BookReviewList.ReviewsBean> implements ComBookReviewContract.View {

    @Inject
    ComBookReviewPresenter mPresenter;

    private String sort = Constant.SortType.DEFAULT;
    private String type = Constant.BookType.ALL;
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
        mAdapter = new CommunityBookReviewAdapter(mContext);
        modiifyAdapter(true, true);

        mPresenter.attachView(this);
        onRefresh();
    }

    @Override
    public void showBookReviewList(List<BookReviewList.ReviewsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        sort = event.sort;
        type = event.type;
        distillate = event.distillate;
        start = 0;
        limit = 20;
        mPresenter.getBookReviewList(sort,type, distillate, start, limit);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookReviewList(sort,type, distillate, start, limit);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getBookReviewList(sort,type, distillate, start, limit);
    }

    @Override
    public void onItemClick(int position) {
        BookReviewList.ReviewsBean data = mAdapter.getItem(position);
        ComOverallDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
