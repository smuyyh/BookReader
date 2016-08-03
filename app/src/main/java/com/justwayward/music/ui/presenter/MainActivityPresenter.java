package com.justwayward.music.ui.presenter;

import android.content.Context;
import android.widget.Toast;

import com.justwayward.music.api.MusicApi;
import com.justwayward.music.bean.PlayerList;
import com.justwayward.music.ui.contract.MainContract;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class MainActivityPresenter implements MainContract.Presenter {

    private Context context;
    private MusicApi musicApi;

    @Inject
    public MainActivityPresenter(Context context, MusicApi musicApi){
        this.context = context;
        this.musicApi = musicApi;
    }

    public void getPlayerList(){
        musicApi.getPlayerList("387699584").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlayerList>() {
                    @Override
                    public void onNext(PlayerList playerList) {

                    }

                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "请求完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e){

                    }
                });
    }
}
