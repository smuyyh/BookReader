package com.justwayward.reader.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.CategoryList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerTopCategoryListActivityComponent;
import com.justwayward.reader.ui.adapter.TopCategoryListAdapter;
import com.justwayward.reader.ui.contract.TopCategoryListContract;
import com.justwayward.reader.ui.presenter.TopCategoryListPresenter;
import com.justwayward.reader.view.SupportGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by lfh on 2016/8/30.
 */
public class TopCategoryListActivity extends BaseActivity implements TopCategoryListContract.View {

    @Bind(R.id.rvMaleCategory)
    RecyclerView mRvMaleCategory;
    @Bind(R.id.rvFemaleCategory)
    RecyclerView mRvFeMaleCategory;

    @Inject
    TopCategoryListPresenter mPresenter;

    private TopCategoryListAdapter mMaleCategoryListAdapter;
    private TopCategoryListAdapter mFemaleCategoryListAdapter;
    private List<CategoryList.MaleBean> mMaleCategoryList = new ArrayList<>();
    private List<CategoryList.MaleBean> mFemaleCategoryList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_category_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerTopCategoryListActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle(getString(R.string.category));
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        showDialog();
        mRvMaleCategory.setHasFixedSize(true);
        mRvMaleCategory.setLayoutManager(new GridLayoutManager(this, 3));
        mRvMaleCategory.addItemDecoration(new SupportGridItemDecoration(this));
        mRvFeMaleCategory.setHasFixedSize(true);
        mRvFeMaleCategory.setLayoutManager(new GridLayoutManager(this, 3));
        mRvFeMaleCategory.addItemDecoration(new SupportGridItemDecoration(this));
        mMaleCategoryListAdapter = new TopCategoryListAdapter(mContext, mMaleCategoryList, new ClickListener(Constant.Gender.MALE));
        mFemaleCategoryListAdapter = new TopCategoryListAdapter(mContext, mFemaleCategoryList, new ClickListener(Constant.Gender.FEMALE));
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
    public void complete() {
        dismissDialog();
    }

    class ClickListener implements OnRvItemClickListener<CategoryList.MaleBean> {

        private String gender;

        public ClickListener(@Constant.Gender String gender) {
            this.gender = gender;
        }

        @Override
        public void onItemClick(View view, int position, CategoryList.MaleBean data) {
            SubCategoryListActivity.startActivity(mContext, data.name, gender);
        }
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
