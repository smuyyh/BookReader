package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.ui.contract.BookDetailReviewContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/7.
 */
public class BookDetailReviewPresenter implements BookDetailReviewContract.Presenter<BookDetailReviewContract.View> {

    private Context context;
    private BookApi bookApi;

    private BookDetailReviewContract.View view;

    @Inject
    public BookDetailReviewPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    public void getBookDetailReviewList(String bookId, String sort, final int start, int limit) {
        bookApi.getBookDetailReviewList(bookId, sort, start + "", limit + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDetailReviewList:" + e.toString());
                    }

                    @Override
                    public void onNext(HotReview list) {
                        boolean isRefresh = start == 0 ? true : false;
                        view.showBookDetailReviewList(list.reviews,isRefresh);
                    }
                });
    }


    @Override
    public void attachView(BookDetailReviewContract.View view) {
        this.view = view;
    }
}
