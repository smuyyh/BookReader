package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.ui.contract.SubjectFragmentContract;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.ToastUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubjectFragmentPresenter implements SubjectFragmentContract.Presenter<SubjectFragmentContract.View> {

    private SubjectFragmentContract.View view;

    private Context context;
    private BookApi bookApi;

    @Inject
    public SubjectFragmentPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(SubjectFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void getBookLists(String duration, String sort, final int start, int limit, String tag, String gender) {
        bookApi.getBookLists(duration, sort, start + "", limit + "", tag, gender).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookLists>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookLists:" + e.toString());
                        view.showError();
                    }

                    @Override
                    public void onNext(BookLists tags) {
                        view.showBookList(tags.bookLists, start == 0 ? true : false);
                        if (tags.bookLists == null || tags.bookLists.size() <= 0) {
                            ToastUtils.showSingleToast("暂无相关书单");
                        }
                    }
                });
    }
}
