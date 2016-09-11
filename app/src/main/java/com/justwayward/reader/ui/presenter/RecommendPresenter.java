package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.ui.contract.RecommendContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RxUtil;
import com.justwayward.reader.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class RecommendPresenter extends RxPresenter<RecommendContract.View> implements RecommendContract.Presenter<RecommendContract.View> {

    private BookApi bookApi;

    @Inject
    public RecommendPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRecommendList() {
        String key = StringUtils.creatAcacheKey("recommend-list", "male");
        Observable<Recommend> fromNetWork = bookApi.getRecommend("male")
                .compose(RxUtil.<Recommend>rxCacheHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, Recommend.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onNext(Recommend recommend) {
                        if (recommend != null) {
                            List<Recommend.RecommendBooks> list = recommend.books;
                            if (list != null && !list.isEmpty() && mView != null) {
                                mView.showRecommendList(list);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRecommendList", e.toString());
                        mView.showError();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
