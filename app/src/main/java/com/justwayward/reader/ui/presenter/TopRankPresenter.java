package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.RankingList;
import com.justwayward.reader.ui.contract.TopRankContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
public class TopRankPresenter extends RxPresenter<TopRankContract.View> implements TopRankContract.Presenter<TopRankContract.View> {

    private BookApi bookApi;

    @Inject
    public TopRankPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRankList() {
        Subscription rxSubscription = bookApi.getRanking().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankingList>() {
                    @Override
                    public void onNext(RankingList data) {
                        if (data != null && mView != null) {
                            mView.showRankList(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRankList:" + e.toString());
                        mView.complete();
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
