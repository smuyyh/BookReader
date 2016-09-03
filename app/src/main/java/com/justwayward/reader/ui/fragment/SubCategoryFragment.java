package com.justwayward.reader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.bean.support.SubEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubCategoryFragmentComponent;
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.contract.SubCategoryFragmentContract;
import com.justwayward.reader.ui.easyadapter.SubCategoryAdapter;
import com.justwayward.reader.ui.presenter.SubCategoryFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * 二级分类
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubCategoryFragment extends BaseRVFragment<BooksByCats.BooksBean> implements SubCategoryFragmentContract.View {

    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    public static SubCategoryFragment newInstance(String major, String minor, String gender,
                                                  @Constant.CateType String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Inject
    SubCategoryFragmentPresenter mPresenter;

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        major = getArguments().getString(BUNDLE_MAJOR);
        gender = getArguments().getString(BUNDLE_GENDER);
        minor = getArguments().getString(BUNDLE_MINOR);
        type = getArguments().getString(BUNDLE_TYPE);
    }

    @Override
    public void configViews() {
        mAdapter = new SubCategoryAdapter(mContext);
        modiifyAdapter(true, true);

        mPresenter.attachView(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubCategoryFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showCategoryList(BooksByCats data) {
        mAdapter.clear();
        mAdapter.addAll(data.books);
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SubEvent event) {
        minor = event.minor;
        String type = event.type;
        if (this.type.equals(type)) {
            showDialog();
            mPresenter.getCategoryList(gender, major, minor, this.type, 0);
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        BooksByCats.BooksBean data = mAdapter.getItem(position);
        startActivity(new Intent(activity, BookReadActivity.class)
                .putExtra("bookId", data._id)
                .putExtra("bookName", data.title));
    }
}
