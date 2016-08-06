package com.justwayward.reader.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookDetail;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.ui.contract.BookDetailContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/6.
 */
public class BookDetailPresenter implements BookDetailContract.Presenter<BookDetailContract.View> {

    private Context context;
    private BookApi bookApi;

    private BookDetailContract.View view;

    private static final String TAG = "BookDetailPresenter";

    @Inject
    public BookDetailPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(BookDetailContract.View view) {
        this.view = view;
    }

    public void getBookDetail(String bookId) {
        bookApi.getBookDetail(bookId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetail>() {
                    @Override
                    public void onNext(BookDetail data) {
                        if (data != null && view != null) {
                            view.showBookDetail(data);
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
    public void getHotReview(String book) {
        bookApi.getHotReview(book).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onNext(HotReview data) {
                        Log.e("TAG", "getHotReview" + data.reviews);
                        List<HotReview.Reviews> list = data.reviews;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showHotReview(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

}
