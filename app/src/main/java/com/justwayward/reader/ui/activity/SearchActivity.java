package com.justwayward.reader.ui.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSearchActivityComponent;
import com.justwayward.reader.ui.adapter.AutoCompleteAdapter;
import com.justwayward.reader.ui.adapter.SearchResultAdapter;
import com.justwayward.reader.ui.contract.SearchContract;
import com.justwayward.reader.ui.presenter.SearchPresenter;
import com.justwayward.reader.view.TagColor;
import com.justwayward.reader.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SearchActivity extends BaseActivity implements SearchContract.View, SearchResultAdapter.ItemClickListener {

    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;
    @Bind(R.id.rootLayout)
    LinearLayout mRootLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.layoutHotWord)
    RelativeLayout mLayoutHotWord;

    @Inject
    SearchPresenter mPresenter;

    private SearchResultAdapter mAdapter;
    private List<SearchDetail.SearchBooks> mList = new ArrayList<>();
    private AutoCompleteAdapter mAutoAdapter;
    private List<String> mAutoList = new ArrayList<>();

    private String key;
    private SearchView searchView;

    private ListPopupWindow mListPopupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchActivityComponent.builder()
                .appComponent(appComponent)
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

    }

    @Override
    public void configViews() {
        setSupportActionBar(mToolbar);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchResultAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

        mAutoAdapter = new AutoCompleteAdapter(this, mAutoList);
        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setAdapter(mAutoAdapter);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPopupWindow.setAnchorView(mToolbar);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListPopupWindow.dismiss();
                TextView tv = (TextView) view.findViewById(R.id.tvAutoCompleteItem);
                String str = tv.getText().toString();
                searchView.setQuery(str, true);
            }
        });

        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                searchView.onActionViewExpanded();
                searchView.setQuery(tag, true);
            }
        });

        mPresenter.attachView(this);
        mPresenter.getHotWordList();
    }

    @Override
    public synchronized void showHotWordList(List<String> list) {
        List<TagColor> colors = TagColor.getRandomColors(list.size());
        mTagGroup.setTags(colors, (String[]) list.toArray(new String[list.size()]));
    }

    @Override
    public void showAutoCompleteList(List<String> list) {
        mAutoList.clear();
        mAutoList.addAll(list);

        if (!mListPopupWindow.isShowing()) {
            mListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mListPopupWindow.show();
        }
        mAutoAdapter.notifyDataSetChanged();

    }

    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
        initSearchResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                key = query;
                mPresenter.getSearchResultList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if (mListPopupWindow.isShowing())
                        mListPopupWindow.dismiss();
                    initTagGroup();
                } else {
                    mPresenter.getAutoCompleteList(newText);
                }
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        initTagGroup();
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            //initSearchResult();
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
        if (mListPopupWindow.isShowing())
            mListPopupWindow.dismiss();
    }

    private void initTagGroup() {
        mTagGroup.setVisibility(View.VISIBLE);
        mLayoutHotWord.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        if (mListPopupWindow.isShowing())
            mListPopupWindow.dismiss();
    }

    @Override
    public void onItemClick(SearchDetail.SearchBooks item) {
        startActivity(new Intent(SearchActivity.this, BookDetailActivity.class).putExtra("bookId", item._id));
    }
}
