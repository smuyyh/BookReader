package com.justwayward.reader.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.CategoryList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerCategoryListActivityComponent;
import com.justwayward.reader.ui.adapter.CategoryListAdapter;
import com.justwayward.reader.ui.contract.CategoryListContract;
import com.justwayward.reader.ui.presenter.CategoryListPresenter;
import com.justwayward.reader.view.SupportGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by lfh on 2016/8/30.
 */
public class CategoryListActivity extends BaseActivity implements CategoryListContract.View,
        OnRvItemClickListener<CategoryList.MaleBean> {

    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.rvMaleCategory)
    RecyclerView mRvMaleCategory;
    @Bind(R.id.rvFemaleCategory)
    RecyclerView mRvFeMaleCategory;

    @Inject
    CategoryListPresenter mPresenter;

    private CategoryListAdapter mMaleCategoryListAdapter;
    private CategoryListAdapter mFemaleCategoryListAdapter;
    private List<CategoryList.MaleBean> mMaleCategoryList = new ArrayList<>();
    private List<CategoryList.MaleBean> mFemaleCategoryList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCategoryListActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(getString(R.string.category));
        mToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        setSupportActionBar(mToolbar);

        mRvMaleCategory.setHasFixedSize(true);
        mRvMaleCategory.setLayoutManager(new GridLayoutManager(this, 3));
        mRvMaleCategory.addItemDecoration(new SupportGridItemDecoration(this));
        mRvFeMaleCategory.setHasFixedSize(true);
        mRvFeMaleCategory.setLayoutManager(new GridLayoutManager(this, 3));
        mRvFeMaleCategory.addItemDecoration(new SupportGridItemDecoration(this));
        mMaleCategoryListAdapter = new CategoryListAdapter(mContext, mMaleCategoryList, this);
        mFemaleCategoryListAdapter = new CategoryListAdapter(mContext, mFemaleCategoryList, this);
        mRvMaleCategory.setAdapter(mMaleCategoryListAdapter);
        mRvFeMaleCategory.setAdapter(mFemaleCategoryListAdapter);

        mPresenter.attachView(this);
        mPresenter.getCategoryList();
    }


    @Override
    public void showCategoryList(CategoryList data) {
        mMaleCategoryList.addAll(data.male);
        mFemaleCategoryList.addAll(data.female);
        mMaleCategoryListAdapter.notifyDataSetChanged();
        mFemaleCategoryListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position, CategoryList.MaleBean data) {
//        startActivity(new Intent(CategoryListActivity.this, BookDetailActivity.class)
//                .putExtra("bookId", data._id));
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
