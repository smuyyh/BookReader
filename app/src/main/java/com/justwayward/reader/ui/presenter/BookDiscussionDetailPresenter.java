package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.bean.Disscussion;
import com.justwayward.reader.ui.contract.BookDiscussionDetailContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class BookDiscussionDetailPresenter implements BookDiscussionDetailContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookDiscussionDetailContract.View view;

    @Inject
    public BookDiscussionDetailPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDisscussionDetail(String id) {
        bookApi.getBookDisscussionDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Disscussion>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDisscussionDetail:" + e.toString());
                    }

                    @Override
                    public void onNext(Disscussion disscussion) {
                        view.showBookDisscussionDetail(disscussion);
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
    public void getBookDisscussionComments(String disscussionId, int start, int limit) {
        bookApi.getBookDisscussionComments(disscussionId,start+"",limit+"")
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
                        view.showBookDisscussionComments(list);
                    }
                });
    }

    @Override
    public void attachView(BookDiscussionDetailContract.View view) {
        this.view = view;
    }
}
