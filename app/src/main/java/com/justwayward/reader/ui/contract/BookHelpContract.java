package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookHelpList;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public interface BookHelpContract {

    interface View {
        void showBookHelpList(List<BookHelpList.HelpsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getBookHelpList(String sort, String distillate, int start, int limit);
    }

}
