package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.bean.BookHelp;

/**
 * @author lfh.
 * @date 16/9/3
 */
public interface BookHelpDetailContract {

    interface View {

        void showBookHelpDetail(BookHelp data);

        void showBestComments(CommentList list);

        void showBookHelpComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookHelpDetail(String id);

        void getBestComments(String helpId);

        void getBookHelpComments(String helpId, int start, int limit);

    }

}
