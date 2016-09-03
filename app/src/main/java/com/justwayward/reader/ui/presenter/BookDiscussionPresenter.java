package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.ui.contract.BookDiscussionContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class BookDiscussionPresenter implements BookDiscussionContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookDiscussionContract.View view;

    @Inject
    public BookDiscussionPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDisscussionList(String sort, String distillate, final int start, int limit) {
        bookApi.getBookDisscussionList("ramble", "all", sort, "all", start + "", limit + "", distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscussionList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getCategoryList:" + e.toString());
                    }

                    @Override
                    public void onNext(DiscussionList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        view.showBookDisscussionList(list.posts, isRefresh);
                    }
                });
    }

    @Override
    public void attachView(BookDiscussionContract.View view) {
        this.view = view;
    }
}
