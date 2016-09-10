package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.ui.contract.SubCategoryFragmentContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubCategoryFragmentPresenter extends RxPresenter<SubCategoryFragmentContract.View> implements SubCategoryFragmentContract.Presenter<SubCategoryFragmentContract.View> {

    private BookApi bookApi;

    @Inject
    public SubCategoryFragmentPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getCategoryList(String gender, final String major, String minor, String type, final int start, int limit) {
        Subscription rxSubscription = bookApi.getBooksByCats(gender, type, major, minor, start, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BooksByCats>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getCategoryList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(BooksByCats booksByCats) {
                        mView.showCategoryList(booksByCats, start == 0 ? true : false);
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
