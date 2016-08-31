package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookListDetail;

/**
 * @author lfh.
 * @date 2016/8/31.
 */
public interface SubjectBookListDetailContract {

    interface View {
        void showBookListDetail(BookListDetail data);

        void complete();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListDetail(String bookListId);
    }
}
