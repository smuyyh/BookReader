package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookListTags;
import com.justwayward.reader.ui.contract.SubjectBookListContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubjectBookListPresenter implements SubjectBookListContract.Presenter<SubjectBookListContract.View> {

    private SubjectBookListContract.View view;

    private Context context;
    private BookApi bookApi;

    @Inject
    public SubjectBookListPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookListTags() {
        bookApi.getBookListTags().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookListTags>() {
                    @Override
                    public void onCompleted() {
                        view.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getCategoryList:" + e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(BookListTags tags) {
                        view.showBookListTags(tags);
                    }
                });
    }

    @Override
    public void attachView(SubjectBookListContract.View view) {
        this.view = view;
    }
}
