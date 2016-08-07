package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.ui.contract.BooksByTagContract;
import com.justwayward.reader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BooksByTagPresenter implements BooksByTagContract.Presenter<BooksByTagContract.View> {

    private Context context;
    private BookApi bookApi;

    private BooksByTagContract.View view;

    private boolean isLoading = false;

    @Inject
    public BooksByTagPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(BooksByTagContract.View view) {
        this.view = view;
    }

    @Override
    public void getBooksByTag(String tags, final String start, String limit) {
        if (!isLoading) {
            isLoading = true;
            bookApi.getBooksByTag(tags, start, limit).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BooksByTag>() {
                        @Override
                        public void onNext(BooksByTag data) {
                            if (data != null) {
                                List<BooksByTag.TagBook> list = data.books;
                                if (list != null && !list.isEmpty() && view != null) {
                                    boolean isRefresh = start.equals("0") ? true : false;
                                    view.showBooksByTag(list, isRefresh);
                                }
                            }
                        }

                        @Override
                        public void onCompleted() {
                            LogUtils.i("complete");
                            isLoading = false;
                            view.onLoadComplete(true, "");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtils.e(e.toString());
                            isLoading = false;
                            view.onLoadComplete(false, e.toString());
                        }
                    });
        }
    }
}
