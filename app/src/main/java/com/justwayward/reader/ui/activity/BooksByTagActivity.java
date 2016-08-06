package com.justwayward.reader.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBooksByTagActivityComponent;
import com.justwayward.reader.ui.adapter.BooksByTagAdapter;
import com.justwayward.reader.ui.contract.BooksByTagContract;
import com.justwayward.reader.ui.presenter.BooksByTagPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/7.
 */
public class BooksByTagActivity extends BaseActivity implements BooksByTagContract.View,
        OnRvItemClickListener<BooksByTag.TagBook> {

    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    BooksByTagPresenter mPresenter;

    private BooksByTagAdapter mAdapter;
    private List<BooksByTag.TagBook> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_books_by_tag;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBooksByTagActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(getIntent().getStringExtra("tag"));
        mToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        setSupportActionBar(mToolbar);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BooksByTagAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.getBooksByTag(getIntent().getStringExtra("tag"), "0", "10");
    }


    @Override
    public void showBooksByTag(List<BooksByTag.TagBook> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position, BooksByTag.TagBook data) {
        startActivity(new Intent(BooksByTagActivity.this, BookDetailActivity.class)
                .putExtra("bookId", data._id));
    }

}
