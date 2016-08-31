package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.CategoryListLv2;
import com.justwayward.reader.bean.support.SubEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubCategoryActivityComponent;
import com.justwayward.reader.ui.adapter.MinorAdapter;
import com.justwayward.reader.ui.contract.SubCategoryActivityContract;
import com.justwayward.reader.ui.fragment.SubCategoryFragment;
import com.justwayward.reader.ui.presenter.SubCategoryActivityPresenter;
import com.justwayward.reader.view.RVPIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubCategoryListActivity extends BaseActivity implements SubCategoryActivityContract.View {

    public static final String INTENT_CATE_NAME = "name";
    public static final String INTENT_GENDER = "gender";
    private String cate = "";
    private String gender = "";

    private String currentMinor = "";

    @Bind(R.id.indicatorSub)
    RVPIndicator mIndicator;
    @Bind(R.id.viewpagerSub)
    ViewPager mViewPager;

    @Inject
    SubCategoryActivityPresenter mPresenter;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;

    private List<String> mMinors = new ArrayList<>();
    private ListPopupWindow mListPopupWindow;
    private MinorAdapter minorAdapter;
    private String[] types = new String[]{Constant.CateType.NEW, Constant.CateType.HOT, Constant.CateType.REPUTATION, Constant.CateType.OVER};

    private MenuItem menuItem = null;

    public static void startActivity(Context context, String name, @Constant.Gender String gender) {
        Intent intent = new Intent(context, SubCategoryListActivity.class);
        intent.putExtra(INTENT_CATE_NAME, name);
        intent.putExtra(INTENT_GENDER, gender);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_category_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubCategoryActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        cate = getIntent().getStringExtra(INTENT_CATE_NAME);
        if (menuItem != null) {
            menuItem.setTitle(cate);
        }
        gender = getIntent().getStringExtra(INTENT_GENDER);
        mCommonToolbar.setTitle(cate);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.sub_tabs));

        showDialog();
        mPresenter.attachView(this);
        mPresenter.getCategoryListLv2();

        mTabContents = new ArrayList<>();
        for (String ignored : mDatas) {
            mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.NEW));
            mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.HOT));
            mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.REPUTATION));
            mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.OVER));
        }

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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(new SubEvent(currentMinor, types[position]));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showCategoryList(CategoryListLv2 data) {
        mMinors.clear();
        if (gender.equals(Constant.Gender.MALE)) {
            for (CategoryListLv2.MaleBean bean : data.male) {
                if (cate.equals(bean.major)) {
                    mMinors.addAll(bean.mins);
                    break;
                }
            }
        } else {
            for (CategoryListLv2.MaleBean bean : data.female) {
                if (cate.equals(bean.major)) {
                    mMinors.addAll(bean.mins);
                    break;
                }
            }
        }
        if (minorAdapter == null) {
            minorAdapter = new MinorAdapter(this, mMinors);
            currentMinor = mMinors.get(0);
            EventBus.getDefault().post(new SubEvent(mMinors.get(0), Constant.CateType.NEW));
        }
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub_category, menu);
        menuItem = menu.findItem(R.id.menu_major);
        if (!TextUtils.isEmpty(cate)) {
            menuItem.setTitle(cate);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_major) {
            showMinorPopupWindow();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMinorPopupWindow() {
        if (mMinors.size() > 0 && minorAdapter != null) {
            if (mListPopupWindow == null) {
                mListPopupWindow = new ListPopupWindow(this);
                mListPopupWindow.setAdapter(minorAdapter);
                mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mListPopupWindow.setAnchorView(mCommonToolbar);
                mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        minorAdapter.setChecked(position);
                        currentMinor = mMinors.get(position);
                        int current = mViewPager.getCurrentItem();
                        EventBus.getDefault().post(new SubEvent(mMinors.get(position), types[current]));
                        mListPopupWindow.dismiss();
                        mCommonToolbar.setTitle(mMinors.get(position));

                    }
                });
            }
            mListPopupWindow.show();
        }
    }
}
