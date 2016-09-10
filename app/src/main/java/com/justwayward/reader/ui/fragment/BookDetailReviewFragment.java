package com.justwayward.reader.ui.fragment;

import android.os.Bundle;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.bean.support.SelectionEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.ui.activity.BookReviewDetailActivity;
import com.justwayward.reader.ui.contract.BookDetailReviewContract;
import com.justwayward.reader.ui.easyadapter.BookDetailReviewAdapter;
import com.justwayward.reader.ui.presenter.BookDetailReviewPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 书籍详情 书评列表Fragment
 *
 * @author lfh.
 * @date 16/9/7.
 */
public class BookDetailReviewFragment extends BaseRVFragment<BookDetailReviewPresenter,HotReview.Reviews> implements BookDetailReviewContract.View {

    public final static String BUNDLE_ID = "bookId";

    public static BookDetailReviewFragment newInstance(String id) {
        BookDetailReviewFragment fragment = new BookDetailReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String bookId;

    private String sort = Constant.SortType.DEFAULT;
    private String type = Constant.BookType.ALL;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        bookId = getArguments().getString(BUNDLE_ID);
    }

    @Override
    public void configViews() {
        initAdapter(BookDetailReviewAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void showBookDetailReviewList(List<HotReview.Reviews> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(list);
        start = start + list.size();
        dismissDialog();
    }

    @Override
    public void showError() {
        loaddingError();
        dismissDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        if (getUserVisibleHint()) {
            showDialog();
            sort = event.sort;
            start = 0;
            limit = 20;
            mPresenter.getBookDetailReviewList(bookId, sort, start, limit);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookDetailReviewList(bookId, sort, start, limit);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getBookDetailReviewList(sort, type, start, limit);
    }

    @Override
    public void onItemClick(int position) {
        BookReviewDetailActivity.startActivity(activity, mAdapter.getItem(position)._id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
