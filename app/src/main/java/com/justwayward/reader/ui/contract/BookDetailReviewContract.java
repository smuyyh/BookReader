package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.HotReview;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/9/7.
 */
public interface BookDetailReviewContract {

    interface View {
        void showBookDetailReviewList(List<HotReview.Reviews> list, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookDetailReviewList(String bookId,String sort,int start, int limit);
    }
}
