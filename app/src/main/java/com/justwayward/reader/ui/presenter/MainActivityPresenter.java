package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.ui.contract.MainContract;

import javax.inject.Inject;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class MainActivityPresenter implements MainContract.Presenter {

    private Context context;
    private BookApi bookApi;

    @Inject
    public MainActivityPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }
}
