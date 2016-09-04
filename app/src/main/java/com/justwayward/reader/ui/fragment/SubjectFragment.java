package com.justwayward.reader.ui.fragment;

import android.os.Bundle;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVFragment;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.bean.support.TagEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubjectFragmentComponent;
import com.justwayward.reader.ui.activity.SubjectBookListDetailActivity;
import com.justwayward.reader.ui.contract.SubjectFragmentContract;
import com.justwayward.reader.ui.easyadapter.SubjectBookListAdapter;
import com.justwayward.reader.ui.presenter.SubjectFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * 主题书单
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubjectFragment extends BaseRVFragment<BookLists.BookListsBean, SubjectBookListAdapter> implements SubjectFragmentContract.View {

    public final static String BUNDLE_TAG = "tag";
    public final static String BUNDLE_TAB = "tab";

    public String currendTag;
    public int currentTab;

    public String duration = "";
    public String sort = "";

    public static SubjectFragment newInstance(String tag, int tab) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TAG, tag);
        bundle.putInt(BUNDLE_TAB, tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Inject
    SubjectFragmentPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);

        currentTab = getArguments().getInt(BUNDLE_TAB);
        switch (currentTab) {
            case 0:
                duration = "last-seven-days";
                sort = "collectorCount";
                break;
            case 1:
                duration = "all";
                sort = "created";
                break;
            case 2:
            default:
                duration = "all";
                sort = "collectorCount";
                break;
        }
    }

    @Override
    public void configViews() {
        initAdapter(SubjectBookListAdapter.class, true, true);

        showDialog();
        mPresenter.attachView(this);
        mPresenter.getBookLists(duration, sort, 0, 20, currendTag, "male");
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showBookList(List<BookLists.BookListsBean> bookLists) {
        mAdapter.clear();
        mAdapter.addAll(bookLists);
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(TagEvent event) {
        currendTag = event.tag;
        if (getUserVisibleHint()) {
            showDialog();
            mPresenter.getBookLists(duration, sort, 0, 20, currendTag, "male");
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        SubjectBookListDetailActivity.startActivity(activity, mAdapter.getItem(position)._id);
    }
}
