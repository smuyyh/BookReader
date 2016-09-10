package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookReviewList;
import com.justwayward.reader.ui.contract.BookReviewContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RxUtil;
import com.justwayward.reader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class BookReviewPresenter implements BookReviewContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private BookReviewContract.View view;

    @Inject
    public BookReviewPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookReviewList(final String sort, final String type, final String distillate, final int start, final int limit) {
        String key = StringUtils.creatAcacheKey("book-review-list", sort, type, distillate, start, limit);
        Observable<BookReviewList> fromNetWork = bookApi.getBookReviewList("all", sort, type, start + "", limit + "", distillate)
                .compose(RxUtil.<BookReviewList>rxCacheHelper(key));
        //依次检查disk、network
        Observable.concat(RxUtil.rxCreateDiskObservable(key,BookReviewList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookReviewList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookReviewList:" + e.toString());
                        view.showError();
                    }

                    @Override
                    public void onNext(BookReviewList list) {
                        LogUtils.d("getBookReviewList", "onNext:get data finish");
                        boolean isRefresh = start == 0 ? true : false;
                        view.showBookReviewList(list.reviews, isRefresh);
                    }
                });
    }

    @Override
    public void attachView(BookReviewContract.View view) {
        this.view = view;
    }

}
