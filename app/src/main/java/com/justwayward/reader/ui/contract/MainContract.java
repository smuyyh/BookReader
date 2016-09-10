package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.base.BaseView;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public interface MainContract {

    interface View extends BaseView {
        void loginSuccess();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void login(String uid, String token, String platform);
    }

}
