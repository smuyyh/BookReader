package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.ui.contract.BookDetailDiscussionContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/7.
 */
public class BookDetailDiscussionPresenter implements BookDetailDiscussionContract.Presenter<BookDetailDiscussionContract.View> {

    private Context context;
    private BookApi bookApi;

    private BookDetailDiscussionContract.View view;

    @Inject
    public BookDetailDiscussionPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDetailDiscussionList(String bookId, String sort, final int start, int limit) {
        bookApi.getBookDetailDisscussionList(bookId,sort,"normal,vote", start + "", limit + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscussionList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDetailDiscussionList:" + e.toString());
                    }

                    @Override
                    public void onNext(DiscussionList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        view.showBookDetailDiscussionList(list.posts, isRefresh);
                    }
                });
    }

    @Override
    public void attachView(BookDetailDiscussionContract.View view) {
        this.view = view;
    }
}
