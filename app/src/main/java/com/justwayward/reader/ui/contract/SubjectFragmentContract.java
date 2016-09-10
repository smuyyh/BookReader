package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.base.BaseView;
import com.justwayward.reader.bean.BookLists;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public interface SubjectFragmentContract {

    interface View extends BaseView {
        void showBookList(List<BookLists.BookListsBean> bookLists, boolean isRefresh);

        void showError();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookLists(String duration, String sort, int start, int limit, String tag, String gender);
    }
}
