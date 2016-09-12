package com.justwayward.reader.base;

/**
 * @author yuyh.
 * @date 16/8/6.
 */
public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showError();

        void complete();

    }
}
