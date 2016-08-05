package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.ui.contract.MainContract;

import javax.inject.Inject;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class MainActivityPresenter implements MainContract.Presenter<MainContract.View> {

    private Context context;
    private BookApi bookApi;

    private MainContract.View view;

    @Inject
    public MainActivityPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }
}
