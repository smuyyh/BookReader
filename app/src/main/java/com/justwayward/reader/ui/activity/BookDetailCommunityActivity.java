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
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.support.SelectionEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.ui.fragment.BookDetailDiscussionFragment;
import com.justwayward.reader.ui.fragment.BookDetailReviewFragment;
import com.justwayward.reader.view.RVPIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * 书籍详情 社区
 */
public class BookDetailCommunityActivity extends BaseActivity {

    public static final String INTENT_ID = "bookId";
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_INDEX = "index";

    public static void startActivity(Context context, String bookId, String title, int index) {
        context.startActivity(new Intent(context, BookDetailCommunityActivity.class)
                .putExtra(INTENT_ID, bookId)
                .putExtra(INTENT_TITLE, title)
                .putExtra(INTENT_INDEX, index));
    }

    private String bookId;
    private String title;
    private int index;

    @Bind(R.id.indicatorSubRank)
    RVPIndicator mIndicator;
    @Bind(R.id.viewpagerSubRank)
    ViewPager mViewPager;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;

    private AlertDialog dialog;
    private int[] select = new int[]{0, 0};

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail_community;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        bookId = getIntent().getStringExtra(INTENT_ID);
        title = getIntent().getStringExtra(INTENT_TITLE);
        index = getIntent().getIntExtra(INTENT_INDEX, 0);
        mCommonToolbar.setTitle(title);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.bookdetail_community_tabs));

        mTabContents = new ArrayList<>();
        mTabContents.add(BookDetailDiscussionFragment.newInstance(bookId));
        mTabContents.add(BookDetailReviewFragment.newInstance(bookId));

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
        mViewPager.setOffscreenPageLimit(2);
        mIndicator.setViewPager(mViewPager, index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_community, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            showSortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        int checked = select[mViewPager.getCurrentItem()];
        dialog = new AlertDialog.Builder(this)
                .setTitle("排序")
                .setSingleChoiceItems(new String[]{"默认排序", "最新发布", "最多评论"},
                        checked, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (select[mViewPager.getCurrentItem()] != which) {
                                    select[mViewPager.getCurrentItem()] = which;
                                    switch (which) {
                                        case 0:
                                            EventBus.getDefault().post(new SelectionEvent(Constant.SortType.DEFAULT));
                                            break;
                                        case 1:
                                            EventBus.getDefault().post(new SelectionEvent(Constant.SortType.CREATED));
                                            break;
                                        case 2:
                                            EventBus.getDefault().post(new SelectionEvent(Constant.SortType.COMMENT_COUNT));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
