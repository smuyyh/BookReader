package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.Disscussion;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public interface ComOverallDetailContract {

    interface View {

        void showDisscussion(Disscussion disscussion);

        void complete();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getDisscussionDetail(String id);

    }

}
