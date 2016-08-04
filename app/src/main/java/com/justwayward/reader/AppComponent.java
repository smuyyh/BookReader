package com.justwayward.reader;

import android.content.Context;

import com.justwayward.reader.api.MusicApi;
import com.justwayward.reader.module.AppModule;
import com.justwayward.reader.module.MusicApiModule;

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