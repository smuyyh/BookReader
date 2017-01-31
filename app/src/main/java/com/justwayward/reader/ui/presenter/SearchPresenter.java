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
import com.justwayward.reader.bean.AutoComplete;
import com.justwayward.reader.bean.HotWord;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.ui.contract.SearchContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RxUtil;
import com.justwayward.reader.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/6.
 */
public class SearchPresenter extends RxPresenter<SearchContract.View> implements SearchContract.Presenter<SearchContract.View> {

    private BookApi bookApi;

    @Inject
    public SearchPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    public void getHotWordList() {
        String key = StringUtils.creatAcacheKey("hot-word-list");
        Observable<HotWord> fromNetWork = bookApi.getHotWord()
                .compose(RxUtil.<HotWord>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, HotWord.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotWord>() {
                    @Override
                    public void onNext(HotWord hotWord) {
                        List<String> list = hotWord.hotWords;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showHotWordList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getAutoCompleteList(String query) {
        Subscription rxSubscription = bookApi.getAutoComplete(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoComplete>() {
                    @Override
                    public void onNext(AutoComplete autoComplete) {
                        LogUtils.e("getAutoCompleteList" + autoComplete.keywords);
                        List<String> list = autoComplete.keywords;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showAutoCompleteList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getSearchResultList(String query) {
        Subscription rxSubscription = bookApi.getSearchResult(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchDetail>() {
                    @Override
                    public void onNext(SearchDetail bean) {
                        List<SearchDetail.SearchBooks> list = bean.books;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showSearchResultList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
