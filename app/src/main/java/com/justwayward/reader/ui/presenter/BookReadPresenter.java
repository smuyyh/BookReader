package com.justwayward.reader.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.ui.contract.BookReadContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BookReadPresenter implements BookReadContract.Presenter<BookReadContract.View> {
    private Context context;
    private BookApi bookApi;

    private BookReadContract.View view;

    private static final String TAG = "BookReadPresenter";

    @Inject
    public BookReadPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(BookReadContract.View view) {
        this.view = view;
    }

    @Override
    public void getBookToc(String bookId, String viewChapters) {
        bookApi.getBookToc(bookId, viewChapters).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookToc>() {
                    @Override
                    public void onNext(BookToc data) {
                        List<BookToc.mixToc.Chapters> list = data.mixToc.chapters;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showBookToc(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                });
    }

    @Override
    public void getChapterRead(String url) {
        bookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onNext(ChapterRead data) {
                        if (data.chapter != null && view != null) {
                            view.showChapterRead(data.chapter);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                });
    }


}