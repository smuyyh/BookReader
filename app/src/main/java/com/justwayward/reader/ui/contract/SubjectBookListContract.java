package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.bean.BookListTags;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public interface SubjectBookListContract {

    interface View extends BaseContract.BaseView {
        void showBookListTags(BookListTags data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListTags();
    }
}
