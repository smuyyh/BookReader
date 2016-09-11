package com.justwayward.reader.ui.contract;

import com.justwayward.reader.base.BaseContract;
import com.justwayward.reader.bean.CategoryList;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public interface TopCategoryListContract {

    interface View extends BaseContract.BaseView {
        void showCategoryList(CategoryList data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryList();
    }

}
