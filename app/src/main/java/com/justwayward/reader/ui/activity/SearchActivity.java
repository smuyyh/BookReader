package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.SearchView;
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
import com.justwayward.reader.base.BaseRVActivity;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.ui.adapter.AutoCompleteAdapter;
import com.justwayward.reader.ui.contract.SearchContract;
import com.justwayward.reader.ui.easyadapter.SearchAdapter;
import com.justwayward.reader.ui.presenter.SearchPresenter;
import com.justwayward.reader.utils.ToastUtils;
import com.justwayward.reader.view.TagColor;
import com.justwayward.reader.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SearchActivity extends BaseRVActivity<SearchDetail.SearchBooks> implements SearchContract.View{

    public static final String INTENT_QUERY = "query";

    public static void startActivity(Context context, String query) {
        context.startActivity(new Intent(context, SearchActivity.class)
                .putExtra(INTENT_QUERY, query));
    }

    @Bind(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;
    @Bind(R.id.rootLayout)
    LinearLayout mRootLayout;
    @Bind(R.id.layoutHotWord)
    RelativeLayout mLayoutHotWord;

    @Inject
    SearchPresenter mPresenter;

    private List<String> tagList = new ArrayList<>();
    private int times = 0;

    private AutoCompleteAdapter mAutoAdapter;
    private List<String> mAutoList = new ArrayList<>();

    private String key;
    private MenuItem searchMenuItem;
    private SearchView searchView;

    private ListPopupWindow mListPopupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        initAdapter(SearchAdapter.class, false, false);

        mAutoAdapter = new AutoCompleteAdapter(this, mAutoList);
        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setAdapter(mAutoAdapter);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPopupWindow.setAnchorView(mCommonToolbar);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListPopupWindow.dismiss();
                TextView tv = (TextView) view.findViewById(R.id.tvAutoCompleteItem);
                String str = tv.getText().toString();
                search(str);
            }
        });

        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                search(tag);
            }
        });

        mTvChangeWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHotWord();
            }
        });

        mPresenter.attachView(this);
        mPresenter.getHotWordList();

        key = getIntent().getStringExtra(INTENT_QUERY);
    }

    @Override
    public synchronized void showHotWordList(List<String> list) {
        tagList.clear();
        tagList.addAll(list);
        times = 0;
        showHotWord();
    }

    /**
     * 每次显示8个
     */
    private void showHotWord() {
        int start, end;
        if (times < tagList.size() && times + 8 <= tagList.size()) {
            start = times;
            end = times + 8;
        } else if (times < tagList.size() - 1 && times + 8 > tagList.size()) {
            start = times;
            end = tagList.size() - 1;
        } else {
            start = 0;
            end = tagList.size() > 8 ? 8 : tagList.size();
        }
        times = end;
        if (end - start > 0) {
            List<String> batch = tagList.subList(start, end);
            List<TagColor> colors = TagColor.getRandomColors(batch.size());
            mTagGroup.setTags(colors, (String[]) batch.toArray(new String[batch.size()]));
        }
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
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        initSearchResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        searchMenuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
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
        search(key);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
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

    private void search(String key) {
        if (!TextUtils.isEmpty(key)) {
            MenuItemCompat.expandActionView(searchMenuItem);
            searchView.setQuery(key, true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            //initSearchResult();
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
    public void onRefresh() {
        super.onRefresh();
        ToastUtils.showSingleToast("+++++");
    }

    @Override
    public void onItemClick(int position) {
        SearchDetail.SearchBooks data = (SearchDetail.SearchBooks) mAdapter.getItem(position);
        startActivity(new Intent(SearchActivity.this, BookDetailActivity.class).putExtra
                ("bookId", data._id));
    }
}
