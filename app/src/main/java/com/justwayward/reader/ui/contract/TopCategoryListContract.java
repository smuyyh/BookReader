package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.CategoryList;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public interface TopCategoryListContract {

    interface View {
        void showCategoryList(CategoryList data);

        void complete();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getCategoryList();
    }

}
