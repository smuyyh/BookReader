package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.RankingList;
import com.justwayward.reader.ui.contract.TopRankContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
public class TopRankPresenter implements TopRankContract.Presenter<TopRankContract.View> {

    private Context context;
    private BookApi bookApi;

    private TopRankContract.View view;

    @Inject
    public TopRankPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }


    @Override
    public void getRankList() {
        bookApi.getRanking().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankingList>() {
                    @Override
                    public void onNext(RankingList data) {
                        if (data != null && view != null) {
                            view.showRankList(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRankList:" + e.toString());
                    }
                });
    }

    @Override
    public void attachView(TopRankContract.View view) {
        this.view = view;
    }
}
