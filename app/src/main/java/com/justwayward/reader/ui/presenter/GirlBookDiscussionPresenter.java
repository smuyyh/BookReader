package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.ui.contract.GirlBookDiscussionContract;
import com.justwayward.reader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 16/9/8.
 */
public class GirlBookDiscussionPresenter implements GirlBookDiscussionContract.Presenter {

    private Context context;
    private BookApi bookApi;

    private GirlBookDiscussionContract.View view;

    @Inject
    public GirlBookDiscussionPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getGirlBookDisscussionList(String sort, String distillate, final int start, int limit) {
        bookApi.getGirlBookDisscussionList("girl", "all", sort, "all", start + "", limit + "", distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscussionList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getGirlBookDisscussionList:" + e.toString());
                        view.showError();
                    }

                    @Override
                    public void onNext(DiscussionList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        view.showGirlBookDisscussionList(list.posts, isRefresh);
                    }
                });
    }

    @Override
    public void attachView(GirlBookDiscussionContract.View view) {
        this.view = view;
    }
}
