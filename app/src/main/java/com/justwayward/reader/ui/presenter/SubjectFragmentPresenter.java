package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.ui.contract.SubjectFragmentContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubjectFragmentPresenter implements SubjectFragmentContract.Presenter<SubjectFragmentContract.View> {

    private SubjectFragmentContract.View view;

    private Context context;
    private BookApi bookApi;

    @Inject
    public SubjectFragmentPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(SubjectFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void getBookLists(String duration, String sort, int start, int limit, String tag, String gender) {
        bookApi.getBookLists(duration, sort, start+"", limit+"", tag, gender).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookLists>() {
                    @Override
                    public void onCompleted() {
                        view.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookLists:" + e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(BookLists tags) {
                        view.showBookList(tags.bookLists);
                    }
                });
    }
}
