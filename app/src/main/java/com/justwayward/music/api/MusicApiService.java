package com.justwayward.music.api;


import com.justwayward.music.bean.PlayerList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MusicApiService {

    @GET("/api/playlist/detail")
    Observable<PlayerList> getPlayerList(@Query("id") String id);

}