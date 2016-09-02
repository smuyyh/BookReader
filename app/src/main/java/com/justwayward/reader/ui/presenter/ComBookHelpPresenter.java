package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.HelpList;
import com.justwayward.reader.ui.contract.ComBookHelpContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class ComBookHelpPresenter implements ComBookHelpContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private ComBookHelpContract.View view;

    @Inject
    public ComBookHelpPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getHelpList(String sort, String distillate, int start, int limit) {
        bookApi.getHelpList("all", sort, start + "", limit + "", distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HelpList>() {
                    @Override
                    public void onCompleted() {
                        view.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getHelpList:" + e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(HelpList list) {
                        view.showHelpList(list.helps);
                    }
                });
    }

    @Override
    public void attachView(ComBookHelpContract.View view) {
        this.view = view;
    }
}
