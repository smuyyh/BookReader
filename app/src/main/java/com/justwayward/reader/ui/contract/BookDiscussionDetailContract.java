package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.base.BaseView;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.bean.Disscussion;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public interface BookDiscussionDetailContract {

    interface View extends BaseView {

        void showBookDisscussionDetail(Disscussion disscussion);

        void showBestComments(CommentList list);

        void showBookDisscussionComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookDisscussionDetail(String id);

        void getBestComments(String disscussionId);

        void getBookDisscussionComments(String disscussionId, int start, int limit);

    }

}
