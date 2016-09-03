package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookReviewList;
import com.justwayward.reader.ui.contract.BookReviewContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class BookReviewPresenter implements BookReviewContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookReviewContract.View view;

    @Inject
    public BookReviewPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookReviewList(String sort, String type, String distillate, final int start, int limit) {
        bookApi.getBookReviewList("all", sort, type, start + "", limit + "", distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookReviewList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookReviewList:" + e.toString());
                    }

                    @Override
                    public void onNext(BookReviewList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        view.showBookReviewList(list.reviews,isRefresh);
                    }
                });
    }

    @Override
    public void attachView(BookReviewContract.View view) {
        this.view = view;
    }
}
