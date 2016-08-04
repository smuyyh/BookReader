package com.justwayward.reader;

import android.content.Context;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.module.AppModule;
import com.justwayward.reader.module.BookApiModule;

import dagger.Component;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
@Component(modules = {AppModule.class, BookApiModule.class})
public interface AppComponent {

    Context getContext();

    BookApi getMusicApi();

}