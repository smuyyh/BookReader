package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BooksByTag;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public interface BooksByTagContract {

    interface View {
        void showBooksByTag(List<BooksByTag.TagBook> list, boolean isRefresh);

        void onLoadComplete(boolean isSuccess, String msg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{

        void getBooksByTag(String tags, String start, String limit);
    }

}
