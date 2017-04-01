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

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseRVActivity;
import com.justwayward.reader.bean.BookSource;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.ui.contract.BookSourceContract;
import com.justwayward.reader.ui.easyadapter.BookSourceAdapter;
import com.justwayward.reader.ui.presenter.BookSourcePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * @author yuyh.
 * @date 2016/9/8.
 */
public class BookSourceActivity extends BaseRVActivity<BookSource> implements BookSourceContract.View {

    public static final String INTENT_BOOK_ID = "bookId";

    public static void start(Activity activity, String bookId, int reqId) {
        activity.startActivityForResult(new Intent(activity, BookSourceActivity.class)
                .putExtra(INTENT_BOOK_ID, bookId), reqId);
    }

    @Inject
    BookSourcePresenter mPresenter;

    private String bookId = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_recyclerview;
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
        bookId = getIntent().getStringExtra(INTENT_BOOK_ID);
        mCommonToolbar.setTitle("选择来源");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        initAdapter(BookSourceAdapter.class, false, false);
    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
        mPresenter.getBookSource("summary", bookId);

        new AlertDialog.Builder(this)
                .setMessage("换源功能暂未实现，后续更新...")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onItemClick(int position) {
        BookSource data = mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("source", data);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void showBookSource(List<BookSource> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
