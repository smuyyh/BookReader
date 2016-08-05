package com.justwayward.reader.ui.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.AutoComplete;
import com.justwayward.reader.bean.HotWord;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.ui.contract.SearchContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/6.
 */
public class SearchPresenter implements SearchContract.Presenter<SearchContract.View> {

    private Context context;
    private BookApi bookApi;

    private SearchContract.View view;

    private static final String TAG = "SearchPresenter";

    @Inject
    public SearchPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(SearchContract.View view) {
        this.view = view;
    }

    public void getHotWordList() {
        Log.i(TAG, "-------getHotWordList--------");
        bookApi.getHotWord().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotWord>() {
                    @Override
                    public void onNext(HotWord hotWord) {
                        List<String> list = hotWord.hotWords;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showHotWordList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "请求完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e);
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void getAutoCompleteList() {
        bookApi.getAutoComplete("武").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoComplete>() {
                    @Override
                    public void onNext(AutoComplete autoComplete) {
                        List<String> list = autoComplete.keywords;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showAutoCompleteList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "请求完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void getSearchResultList() {
        bookApi.getSearchResult("武").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchDetail>() {
                    @Override
                    public void onNext(SearchDetail bean) {
                        List<SearchDetail.SearchBooks> list = bean.books;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showSearchResultList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "请求完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
