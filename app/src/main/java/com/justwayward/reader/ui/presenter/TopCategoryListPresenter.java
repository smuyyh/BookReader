package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.CategoryList;
import com.justwayward.reader.ui.contract.TopCategoryListContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public class TopCategoryListPresenter extends RxPresenter<TopCategoryListContract.View> implements TopCategoryListContract.Presenter<TopCategoryListContract.View> {

    private BookApi bookApi;

    @Inject
    public TopCategoryListPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getCategoryList() {
        Subscription rxSubscription = bookApi.getCategoryList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryList>() {
                    @Override
                    public void onNext(CategoryList data) {
                        if (data != null && mView != null) {
                            mView.showCategoryList(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.i("complete");
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                        mView.complete();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
