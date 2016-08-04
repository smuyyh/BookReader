package com.justwayward.reader.base;

import android.app.Activity;
import android.os.Bundle;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.MusicApplication;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(MusicApplication.getsInstance().getAppComponent());
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);
}
