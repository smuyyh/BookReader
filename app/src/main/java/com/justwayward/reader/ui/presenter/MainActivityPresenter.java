package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.user.Login;
import com.justwayward.reader.ui.contract.MainContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class MainActivityPresenter implements MainContract.Presenter<MainContract.View> {

    private Context context;
    private BookApi bookApi;

    private MainContract.View view;

    @Inject
    public MainActivityPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }


    @Override
    public void login(String uid, String token, String platform) {
        bookApi.login(uid, token, platform).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onNext(Login data) {
                        LogUtils.e(data.user.toString());
                        if (data != null && view != null) {
                            view.loginSuccess();
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
    }
}
