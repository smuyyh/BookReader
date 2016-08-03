package com.justwayward.music.module;

import com.justwayward.music.ui.ActivityScope;
import com.justwayward.music.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @ActivityScope
    MainActivity provideMainActivity() {
        return mainActivity;
    }


//    @Provides
//    @ActivityScope
//    MainActivityPresenter provideMainActivityPresenter(MainActivity mainActivity) {
//        return new MainActivityPresenter(mainActivity);
//    }
}