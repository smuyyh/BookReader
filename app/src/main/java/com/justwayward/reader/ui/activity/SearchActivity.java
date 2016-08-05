package com.justwayward.reader.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.ui.adapter.SearchResultAdapter;
import com.justwayward.reader.ui.component.DaggerSearchActivityComponent;
import com.justwayward.reader.ui.contract.SearchContract;
import com.justwayward.reader.ui.presenter.SearchPresenter;
import com.justwayward.reader.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SearchActivity extends BaseActivity implements SearchContract.View {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;

    @Inject
    SearchPresenter mPresenter;
    @Bind(R.id.rootLayout)
    LinearLayout mRootLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.layoutHotWord)
    RelativeLayout mLayoutHotWord;

    private SearchResultAdapter mAdapter;
    private List<SearchDetail.SearchBooks> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchActivityComponent.builder()
                .appComponent(appComponent)
                //.mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
//        mTagGroup.setOnClickListener(new TagGroup.OnTagClickListener() {
//            @Override
//            public void onTagClick(String tag) {
//
//            }
//        });
    }

    @Override
    public void configViews() {
        setSupportActionBar(mToolbar);
        mPresenter.attachView(this);
        mPresenter.getHotWordList();
    }

    @Override
    public void showHotWordList(List<String> list) {
        mTagGroup.setTags((String[]) list.toArray(new String[list.size()]));
    }

    @Override
    public void showAutoCompleteList(List<String> list) {

    }

    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            initSearchResult();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initSearchResult() {
        mTagGroup.setVisibility(View.GONE);
        mLayoutHotWord.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchResultAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.getSearchResultList();
    }

}
