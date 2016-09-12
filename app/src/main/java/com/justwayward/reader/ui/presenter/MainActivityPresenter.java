package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.user.Login;
import com.justwayward.reader.ui.contract.MainContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class MainActivityPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {

    private BookApi bookApi;

    @Inject
    public MainActivityPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void login(String uid, String token, String platform) {
        Subscription rxSubscription =  bookApi.login(uid, token, platform).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onNext(Login data) {
                        if (data != null && mView != null && data.ok) {
                            mView.loginSuccess();
                            LogUtils.e(data.user.toString());
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("login" + e.toString());
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
