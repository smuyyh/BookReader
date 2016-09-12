package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.BookListTags;
import com.justwayward.reader.ui.contract.SubjectBookListContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RxUtil;
import com.justwayward.reader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubjectBookListPresenter extends RxPresenter<SubjectBookListContract.View> implements SubjectBookListContract.Presenter<SubjectBookListContract.View> {

    private BookApi bookApi;

    @Inject
    public SubjectBookListPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookListTags() {
        String key = StringUtils.creatAcacheKey("book-list-tags");
        Observable<BookListTags> fromNetWork = bookApi.getBookListTags()
                .compose(RxUtil.<BookListTags>rxCacheHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, BookListTags.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookListTags>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookListTags:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(BookListTags tags) {
                        mView.showBookListTags(tags);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
