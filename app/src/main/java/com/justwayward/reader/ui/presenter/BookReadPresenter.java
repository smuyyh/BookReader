/**
 * Copyright 2016 JustWayward Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.BookMixAToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.ui.contract.BookReadContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RxUtil;
import com.justwayward.reader.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BookReadPresenter extends RxPresenter<BookReadContract.View>
        implements BookReadContract.Presenter<BookReadContract.View> {

    private Context mContext;
    private BookApi bookApi;

    @Inject
    public BookReadPresenter(Context mContext, BookApi bookApi) {
        this.mContext = mContext;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookMixAToc(final String bookId, String viewChapters) {
        String key = StringUtils.creatAcacheKey("book-toc", bookId, viewChapters);
        Observable<BookMixAToc.mixToc> fromNetWork = bookApi.getBookMixAToc(bookId, viewChapters)
                .map(new Func1<BookMixAToc, BookMixAToc.mixToc>() {
                    @Override
                    public BookMixAToc.mixToc call(BookMixAToc data) {
                        return data.mixToc;
                    }
                })
                .compose(RxUtil.<BookMixAToc.mixToc>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable
                .concat(RxUtil.rxCreateDiskObservable(key, BookMixAToc.mixToc.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookMixAToc.mixToc>() {
                    @Override
                    public void onNext(BookMixAToc.mixToc data) {
                        List<BookMixAToc.mixToc.Chapters> list = data.chapters;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showBookToc(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.netError(0);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getChapterRead(String url, final int chapter) {
        Subscription rxSubscription = bookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onNext(ChapterRead data) {
                        if (data.chapter != null && mView != null) {
                            mView.showChapterRead(data.chapter, chapter);
                        } else {
                            mView.netError(chapter);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.netError(chapter);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}