/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.BookListTags;
import com.justwayward.reader.bean.support.TagEvent;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerFindComponent;
import com.justwayward.reader.ui.adapter.SubjectTagsAdapter;
import com.justwayward.reader.ui.contract.SubjectBookListContract;
import com.justwayward.reader.ui.fragment.SubjectFragment;
import com.justwayward.reader.ui.presenter.SubjectBookListPresenter;
import com.justwayward.reader.utils.ToastUtils;
import com.justwayward.reader.view.RVPIndicator;
import com.justwayward.reader.view.ReboundScrollView;
import com.justwayward.reader.view.SupportDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SubjectBookListActivity extends BaseActivity implements SubjectBookListContract.View, OnRvItemClickListener<String> {

    @Bind(R.id.indicatorSubject)
    RVPIndicator mIndicator;
    @Bind(R.id.viewpagerSubject)
    ViewPager mViewPager;
    @Bind(R.id.rsvTags)
    ReboundScrollView rsvTags;

    @Bind(R.id.rvTags)
    RecyclerView rvTags;
    private SubjectTagsAdapter mTagAdapter;
    private List<BookListTags.DataBean> mTagList = new ArrayList<>();

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;

    @Inject
    SubjectBookListPresenter mPresenter;

    private String currentTag = "";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SubjectBookListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_subject_book_list_tag;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle(R.string.subject_book_list);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.subject_tabs));

        mTabContents = new ArrayList<>();
        mTabContents.add(SubjectFragment.newInstance("", 0));
        mTabContents.add(SubjectFragment.newInstance("", 1));
        mTabContents.add(SubjectFragment.newInstance("", 2));

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }

    @Override
    public void configViews() {
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);

        rvTags.setHasFixedSize(true);
        rvTags.setLayoutManager(new LinearLayoutManager(this));
        rvTags.addItemDecoration(new SupportDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mTagAdapter = new SubjectTagsAdapter(this, mTagList);
        mTagAdapter.setItemClickListener(this);
        rvTags.setAdapter(mTagAdapter);

        mPresenter.attachView(this);
        mPresenter.getBookListTags();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_tags) {
            if (isVisible(rsvTags)) {
                hideTagGroup();
            } else {
                showTagGroup();
            }
            return true;
        } else if (item.getItemId() == R.id.menu_my_book_list) {
            startActivity(new Intent(this, MyBookListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isVisible(rsvTags)) {
            hideTagGroup();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showBookListTags(BookListTags data) {
        mTagList.clear();
        mTagList.addAll(data.data);
        mTagAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
    }

    @Override
    public void onItemClick(View view, int position, String data) {
        hideTagGroup();
        currentTag = data;
        EventBus.getDefault().post(new TagEvent(currentTag));
    }

    private void showTagGroup() {
        if (mTagList.isEmpty()) {
            ToastUtils.showToast(getString(R.string.network_error_tips));
            return;
        }
        Animation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(400);
        rsvTags.startAnimation(mShowAction);
        rsvTags.setVisibility(View.VISIBLE);
    }

    private void hideTagGroup() {
        Animation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(400);
        rsvTags.startAnimation(mHiddenAction);
        rsvTags.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
