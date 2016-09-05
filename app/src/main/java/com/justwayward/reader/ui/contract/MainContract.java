package com.justwayward.reader.ui.contract;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public interface MainContract {

    interface View{
        void loginSuccess();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{

        void login(String uid, String token, String platform);
    }

}
