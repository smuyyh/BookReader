package com.justwayward.reader.ui.presenter;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.ui.contract.RecommendContract;
import com.justwayward.reader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class RecommendPresenter implements RecommendContract.Presenter<RecommendContract.View> {

    private Context context;
    private BookApi bookApi;

    private RecommendContract.View view;

    @Inject
    public RecommendPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(RecommendContract.View view) {
        this.view = view;
    }

    @Override
    public void getRecommendList() {
        bookApi.getRecommend("male").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onNext(Recommend recommend) {
                        //jsonStr.replaceAll(" ", "");
                        //Recommend recommend = new Gson().fromJson(jsonStr, Recommend.class);
                        if (recommend != null) {
                            List<Recommend.RecommendBooks> list = recommend.books;
                            if (list != null && !list.isEmpty() && view != null) {
                                view.showRecommendList(list);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.i("complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                });
    }
}
