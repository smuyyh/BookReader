package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.CategoryList;
import com.justwayward.reader.ui.contract.CategoryListContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/30.
 */
public class CategoryListPresenter implements CategoryListContract.Presenter<CategoryListContract.View> {

    private Context context;
    private BookApi bookApi;

    private CategoryListContract.View view;

    @Inject
    public CategoryListPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(CategoryListContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategoryList() {
        bookApi.getCategoryList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryList>() {
                    @Override
                    public void onNext(CategoryList data) {
                        if (data != null && view != null) {
                            view.showCategoryList(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.i("complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                });
    }
}
