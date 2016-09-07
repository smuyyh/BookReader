package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.ui.fragment.BookDetailDiscussionFragment;
import com.justwayward.reader.ui.fragment.BookDetailReviewFragment;
import com.justwayward.reader.view.RVPIndicator;

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

    public static void startActivity(Context context, String bookId, String title,int index) {
        context.startActivity(new Intent(context, BookDetailCommunityActivity.class)
                .putExtra(INTENT_ID, bookId)
                .putExtra(INTENT_TITLE, title)
                .putExtra(INTENT_TITLE, index));
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
        index = getIntent().getIntExtra(INTENT_INDEX,0);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
