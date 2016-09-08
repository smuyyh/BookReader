package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BooksByTag;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public interface SearchByAuthorContract {

    interface View {
        void showSearchResultList(List<BooksByTag.TagBook> list);

        void showError();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getSearchResultList(String author);
    }

}
