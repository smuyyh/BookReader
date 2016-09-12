package com.justwayward.reader.ui.presenter;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.base.RxPresenter;
import com.justwayward.reader.bean.AutoComplete;
import com.justwayward.reader.bean.HotWord;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.ui.contract.SearchContract;
import com.justwayward.reader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

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
        Subscription rxSubscription = bookApi.getHotWord().subscribeOn(Schedulers.io())
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
