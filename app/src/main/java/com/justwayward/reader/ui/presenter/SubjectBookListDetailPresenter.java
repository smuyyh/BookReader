package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookListDetail;
import com.justwayward.reader.ui.contract.SubjectBookListDetailContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/31.
 */
public class SubjectBookListDetailPresenter implements SubjectBookListDetailContract.Presenter<SubjectBookListDetailContract.View> {

    private SubjectBookListDetailContract.View view;

    private Context context;
    private BookApi bookApi;

    @Inject
    public SubjectBookListDetailPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookListDetail(String bookListId) {
        bookApi.getBookListDetail(bookListId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookListDetail>() {
                    @Override
                    public void onCompleted() {
                        view.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookListDetail:" + e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(BookListDetail data) {
                        view.showBookListDetail(data);
                    }
                });
    }

    @Override
    public void attachView(SubjectBookListDetailContract.View view) {
        this.view = view;
    }
}
