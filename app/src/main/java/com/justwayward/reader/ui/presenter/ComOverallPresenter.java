package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.ui.contract.ComOverallContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class ComOverallPresenter implements ComOverallContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private ComOverallContract.View view;

    @Inject
    public ComOverallPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getDisscussionList(String sort, String distillate, final int start, int limit) {
        bookApi.getDisscussionList("ramble", "all", sort, "all", start + "", limit + "", distillate)
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
                        view.showDisscussionList(list.posts, isRefresh);
                    }
                });
    }

    @Override
    public void attachView(ComOverallContract.View view) {
        this.view = view;
    }
}
