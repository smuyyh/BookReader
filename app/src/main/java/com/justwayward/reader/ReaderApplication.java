package com.justwayward.reader;

import android.app.Application;

import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerAppComponent;
import com.justwayward.reader.module.AppModule;
import com.justwayward.reader.module.BookApiModule;
import com.justwayward.reader.utils.AppUtils;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class ReaderApplication extends Application {

    private static ReaderApplication sInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;
        initCompoent();
        AppUtils.init(this);
    }

    public static ReaderApplication getsInstance() {
        return sInstance;
    }

    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .bookApiModule(new BookApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
