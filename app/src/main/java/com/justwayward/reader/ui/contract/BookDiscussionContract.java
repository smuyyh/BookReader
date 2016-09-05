package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.DiscussionList;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public interface BookDiscussionContract {

    interface View {
        void showBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);

        void showError();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getBookDisscussionList(String sort, String distillate, int start, int limit);
    }

}
