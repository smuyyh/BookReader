package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.bean.BookReviewList;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public interface BookReviewContract {

    interface View extends BaseContract.BaseView {
        void showBookReviewList(List<BookReviewList.ReviewsBean> list, boolean isRefresh);

        void showError();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getBookReviewList(String sort, String type, String distillate, int start, int limit);
    }

}
