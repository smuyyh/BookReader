package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.base.BaseView;
import com.justwayward.reader.bean.BooksByCats;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public interface SubCategoryFragmentContract {

    interface View extends BaseView {
        void showCategoryList(BooksByCats data, boolean isRefresh);

        void showError();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryList(String gender, String major, String minor, String type, int start, int limit);
    }

}
