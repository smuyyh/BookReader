package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookReviewList;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public interface ComBookReviewContract {

    interface View {
        void showBookReviewList(List<BookReviewList.ReviewsBean> list);

        void complete();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookReviewList(String sort, String type,String distillate, int start, int limit);

    }

}
