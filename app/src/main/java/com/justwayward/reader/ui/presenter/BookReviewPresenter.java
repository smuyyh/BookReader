/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.BookReviewList;
import com.justwayward.reader.ui.contract.BookReviewContract;
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
 * @date 16/9/3.
 */
public class BookReviewPresenter extends RxPresenter<BookReviewContract.View> implements BookReviewContract.Presenter {

    private BookApi bookApi;

    @Inject
    public BookReviewPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookReviewList(final String sort, final String type, final String distillate, final int start, final int limit) {
        String key = StringUtils.creatAcacheKey("book-review-list", sort, type, distillate, start, limit);
        Observable<BookReviewList> fromNetWork = bookApi.getBookReviewList("all", sort, type, start + "", limit + "", distillate)
                .compose(RxUtil.<BookReviewList>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, BookReviewList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookReviewList>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: "+e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(BookReviewList list) {
                        LogUtils.d("onNext: get data finish");
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookReviewList(list.reviews, isRefresh);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
