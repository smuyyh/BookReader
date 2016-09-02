package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookReviewList;
import com.justwayward.reader.ui.contract.ComBookReviewContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class ComBookReviewPresenter implements ComBookReviewContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private ComBookReviewContract.View view;

    @Inject
    public ComBookReviewPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookReviewList(String sort, String type,String distillate, int start, int limit) {
        bookApi.getBookReviewList("all", sort, type, start + "", limit + "", distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookReviewList>() {
                    @Override
                    public void onCompleted() {
                        view.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookReviewList:" + e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(BookReviewList list) {
                        view.showBookReviewList(list.reviews);
                    }
                });
    }

    @Override
    public void attachView(ComBookReviewContract.View view) {
        this.view = view;
    }
}
