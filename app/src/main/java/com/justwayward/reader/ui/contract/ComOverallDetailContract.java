package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.bean.Disscussion;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public interface ComOverallDetailContract {

    interface View {

        void showDisscussion(Disscussion disscussion);

        void showBestComments(CommentList list);

        void showDisscussionComments(CommentList list);

        void complete();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getDisscussionDetail(String id);

        void getBestComments(String disscussionId);

        void getDisscussionComments(String disscussionId,int start,int limit);

    }

}
