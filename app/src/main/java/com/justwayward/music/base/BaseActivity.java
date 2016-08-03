package com.justwayward.music.base;

import android.app.Activity;
import android.os.Bundle;

import com.justwayward.music.AppComponent;
import com.justwayward.music.MusicApplication;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(MusicApplication.getsInstance().getAppComponent());
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);
}
