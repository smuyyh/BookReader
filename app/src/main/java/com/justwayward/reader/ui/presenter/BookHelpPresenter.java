package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookHelpList;
import com.justwayward.reader.ui.contract.BookHelpContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class BookHelpPresenter implements BookHelpContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookHelpContract.View view;

    @Inject
    public BookHelpPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookHelpList(String sort, String distillate, final int start, int limit) {
        bookApi.getBookHelpList("all", sort, start + "", limit + "", distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookHelpList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookHelpList:" + e.toString());
                        view.showError();
                    }

                    @Override
                    public void onNext(BookHelpList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        view.showBookHelpList(list.helps,isRefresh);
                    }
                });
    }

    @Override
    public void attachView(BookHelpContract.View view) {
        this.view = view;
    }
}
