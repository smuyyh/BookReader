package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public interface MainContract {

    interface View extends BaseContract.BaseView {
        void loginSuccess();

        void syncBookShelfCompleted();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void login(String uid, String token, String platform);

        void syncBookShelf();
    }

}
