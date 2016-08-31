package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookListTags;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public interface SubjectBookListContract {

    interface View {
        void showBookListTags(BookListTags data);

        void complete();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListTags();
    }
}
