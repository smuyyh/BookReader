package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.DiscussionList;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public interface ComOverallContract {

    interface View {
        void showDisscussionList(List<DiscussionList.PostsBean> list);

        void complete();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getDisscussionList(String sort, String distillate, int start, int limit);

    }

}
