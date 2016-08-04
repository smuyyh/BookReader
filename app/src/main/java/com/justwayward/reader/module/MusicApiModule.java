package com.justwayward.reader.module;

import com.justwayward.reader.api.MusicApi;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class MusicApiModule {

    @Provides
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }

    @Provides
    protected MusicApi provideMusicService(OkHttpClient okHttpClient) {
        return MusicApi.getInstance(okHttpClient);
    }
}