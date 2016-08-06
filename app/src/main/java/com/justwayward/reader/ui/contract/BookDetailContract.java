package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookDetail;
import com.justwayward.reader.bean.HotReview;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/8/6.
 */
public interface BookDetailContract {

    interface View {
        void showBookDetail(BookDetail data);
        void showHotReview(List<HotReview.Reviews> list);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getBookDetail(String bookId);
        void getHotReview(String book);
    }

}
