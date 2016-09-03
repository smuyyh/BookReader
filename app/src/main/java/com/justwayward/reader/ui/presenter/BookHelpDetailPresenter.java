package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.bean.BookHelp;
import com.justwayward.reader.ui.contract.BookHelpDetailContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class BookHelpDetailPresenter implements BookHelpDetailContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookHelpDetailContract.View view;

    @Inject
    public BookHelpDetailPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookHelpDetail(String id) {
        bookApi.getBookHelpDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookHelp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookHelpDetail:" + e.toString());
                    }

                    @Override
                    public void onNext(BookHelp bookHelp) {
                        view.showBookHelpDetail(bookHelp);
                    }
                });
    }

    @Override
    public void getBestComments(String disscussionId) {
        bookApi.getBestComments(disscussionId)
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
    public void getBookHelpComments(String disscussionId, int start, int limit) {
        bookApi.getBookReviewComments(disscussionId,start+"",limit+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookHelpComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        view.showBookHelpComments(list);
                    }
                });
    }

    @Override
    public void attachView(BookHelpDetailContract.View view) {
        this.view = view;
    }
}
