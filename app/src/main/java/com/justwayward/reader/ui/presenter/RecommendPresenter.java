package com.justwayward.reader.ui.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.ui.contract.RecommendContract;

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
        Log.i("TAG", "---------------");
        bookApi.getRecommend("male").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onNext(Recommend recommend) {
                        List<Recommend.RecommendBooks> list = recommend.books;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showRecommendList(list);
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
