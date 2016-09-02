package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.Disscussion;
import com.justwayward.reader.ui.contract.ComOverallDetailContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class ComOverallDetailPresenter implements ComOverallDetailContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private ComOverallDetailContract.View view;

    @Inject
    public ComOverallDetailPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getDisscussionDetail(String id) {
        bookApi.getDisscussionDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Disscussion>() {
                    @Override
                    public void onCompleted() {
                        view.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getDisscussionDetail:" + e.toString());
                        view.complete();
                    }

                    @Override
                    public void onNext(Disscussion disscussion) {
                        view.showDisscussion(disscussion);
                    }
                });
    }

    @Override
    public void attachView(ComOverallDetailContract.View view) {
        this.view = view;
    }
}
