package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookReview;
import com.justwayward.reader.bean.CommentList;

/**
 * @author lfh.
 * @date 16/9/3
 */
public interface BookReviewDetailContract {

    interface View {

        void showBookReviewDetail(BookReview data);

        void showBestComments(CommentList list);

        void showBookReviewComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookReviewDetail(String id);

        void getBestComments(String bookReviewId);

        void getBookReviewComments(String bookReviewId, int start, int limit);

    }

}
