package com.justwayward.reader.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lfh on 2016/9/11.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
