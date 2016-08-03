package com.justwayward.music;

import android.content.Context;

import com.justwayward.music.api.MusicApi;
import com.justwayward.music.module.AppModule;
import com.justwayward.music.module.MusicApiModule;

import dagger.Component;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
@Component(modules = {AppModule.class, MusicApiModule.class})
public interface AppComponent {

    Context getContext();

    MusicApi getMusicApi();

}