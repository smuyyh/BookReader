package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.CategoryListLv2;
import com.justwayward.reader.ui.contract.SubCategoryActivityContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubCategoryActivityPresenter implements SubCategoryActivityContract.Presenter<SubCategoryActivityContract.View> {

    private SubCategoryActivityContract.View view;

    private Context context;
    private BookApi bookApi;

    @Inject
    public SubCategoryActivityPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getCategoryListLv2() {
        bookApi.getCategoryListLv2().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryListLv2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getCategoryListLv2:"+e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(CategoryListLv2 categoryListLv2) {
                        view.showCategoryList(categoryListLv2);
                        view.complete();
                    }
                });
    }

    @Override
    public void attachView(SubCategoryActivityContract.View view) {
        this.view = view;
    }
}
