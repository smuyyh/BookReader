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
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.ui.contract.SearchByAuthorContract;
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
 * @date 2016/9/8.
 */
public class SearchByAuthorPresenter extends RxPresenter<SearchByAuthorContract.View> implements SearchByAuthorContract.Presenter {

    private BookApi bookApi;

    @Inject
    public SearchByAuthorPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getSearchResultList(String author) {
        String key = StringUtils.creatAcacheKey("search-by-author", author);
        Observable<BooksByTag> fromNetWork = bookApi.searchBooksByAuthor(author)
                .compose(RxUtil.<BooksByTag>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, BooksByTag.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BooksByTag>() {
                    @Override
                    public void onNext(BooksByTag booksByTag) {
                        if (mView != null)
                            mView.showSearchResultList(booksByTag.books);
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.i("complete");
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getSearchResultList:" + e.toString());
                        if (mView != null)
                            mView.showError();
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
