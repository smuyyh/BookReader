package com.justwayward.music.ui.component;

import com.justwayward.music.AppComponent;
import com.justwayward.music.module.MainActivityModule;
import com.justwayward.music.ui.activity.MainActivity;

import dagger.Component;

@Component(modules = MainActivityModule.class, dependencies = AppComponent.class)
public interface MainActivityComponent {
    MainActivity inject(MainActivity mainActivity);
}