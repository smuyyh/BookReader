package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookReview;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.ui.contract.BookReviewDetailContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class BookReviewDetailPresenter implements BookReviewDetailContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookReviewDetailContract.View view;

    @Inject
    public BookReviewDetailPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookReviewDetail(String id) {
        bookApi.getBookReviewDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookReview>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookReviewDetail:" + e.toString());
                    }

                    @Override
                    public void onNext(BookReview data) {
                        view.showBookReviewDetail(data);
                    }
                });
    }

    @Override
    public void getBestComments(String bookReviewId) {
        bookApi.getBestComments(bookReviewId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBestComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        view.showBestComments(list);
                    }
                });
    }

    @Override
    public void getBookReviewComments(String bookReviewId, int start, int limit) {
        bookApi.getBookReviewComments(bookReviewId,start+"",limit+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDisscussionComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        view.showBookReviewComments(list);
                    }
                });
    }

    @Override
    public void attachView(BookReviewDetailContract.View view) {
        this.view = view;
    }
}
