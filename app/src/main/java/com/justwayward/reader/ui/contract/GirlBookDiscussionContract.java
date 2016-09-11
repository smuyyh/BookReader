package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.bean.DiscussionList;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/8.
 */
public interface GirlBookDiscussionContract {

    interface View extends BaseContract.BaseView {
        void showGirlBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getGirlBookDisscussionList(String sort, String distillate, int start, int limit);
    }

}
