package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.CategoryList;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public interface CategoryListContract {

    interface View {
        void showCategoryList(CategoryList data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getCategoryList();
    }

}
