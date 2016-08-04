package com.justwayward.reader.api;

import com.justwayward.reader.bean.PlayerList;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class MusicApi {

    public static MusicApi instance;

    private MusicApiService service;

    public MusicApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://music.163.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(MusicApiService.class);
    }

    public static MusicApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new MusicApi(okHttpClient);
        return instance;
    }

    public Observable<PlayerList> getPlayerList(String id) {
        return service.getPlayerList(id);
    }

}
