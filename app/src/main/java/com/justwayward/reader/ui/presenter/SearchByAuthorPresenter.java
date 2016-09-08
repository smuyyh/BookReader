package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.ui.contract.SearchByAuthorContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/9/8.
 */
public class SearchByAuthorPresenter implements SearchByAuthorContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private SearchByAuthorContract.View view;

    @Inject
    public SearchByAuthorPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getSearchResultList(String author) {
        bookApi.searchBooksByAuthor(author).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BooksByTag>() {
                    @Override
                    public void onNext(BooksByTag booksByTag) {
                        if (view != null)
                            view.showSearchResultList(booksByTag.books);
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.i("complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getSearchResultList:" + e.toString());
                        if (view != null)
                            view.showError();
                    }
                });
    }

    @Override
    public void attachView(SearchByAuthorContract.View view) {
        this.view = view;
    }
}
