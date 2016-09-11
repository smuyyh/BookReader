package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.ui.contract.BookDetailReviewContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RxUtil;
import com.justwayward.reader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author lfh.
 * @date 16/9/7.
 */
public class BookDetailReviewPresenter extends RxPresenter<BookDetailReviewContract.View> implements BookDetailReviewContract.Presenter<BookDetailReviewContract.View> {

    private BookApi bookApi;

    @Inject
    public BookDetailReviewPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    public void getBookDetailReviewList(String bookId, String sort, final int start, int limit) {
        String key = StringUtils.creatAcacheKey("book-detail-review-list", bookId, sort, start, limit);
        Observable<HotReview> fromNetWork = bookApi.getBookDetailReviewList(bookId, sort, start + "", limit + "")
                .compose(RxUtil.<HotReview>rxCacheHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, HotReview.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDetailReviewList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(HotReview list) {
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookDetailReviewList(list.reviews, isRefresh);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
