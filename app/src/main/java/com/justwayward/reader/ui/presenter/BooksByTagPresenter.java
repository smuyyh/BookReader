package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.ui.contract.BooksByTagContract;
import com.justwayward.reader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BooksByTagPresenter extends RxPresenter<BooksByTagContract.View> implements BooksByTagContract.Presenter<BooksByTagContract.View> {

    private BookApi bookApi;

    private boolean isLoading = false;

    @Inject
    public BooksByTagPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBooksByTag(String tags, final String start, String limit) {
        if (!isLoading) {
            isLoading = true;
            Subscription rxSubscription = bookApi.getBooksByTag(tags, start, limit).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BooksByTag>() {
                        @Override
                        public void onNext(BooksByTag data) {
                            if (data != null) {
                                List<BooksByTag.TagBook> list = data.books;
                                if (list != null && !list.isEmpty() && mView != null) {
                                    boolean isRefresh = start.equals("0") ? true : false;
                                    mView.showBooksByTag(list, isRefresh);
                                }
                            }
                        }

                        @Override
                        public void onCompleted() {
                            LogUtils.i("complete");
                            isLoading = false;
                            mView.onLoadComplete(true, "");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtils.e(e.toString());
                            isLoading = false;
                            mView.onLoadComplete(false, e.toString());
                        }
                    });
            addSubscrebe(rxSubscription);
        }
    }
}
