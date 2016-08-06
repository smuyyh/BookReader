package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.MainActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MainActivityComponent {
    MainActivity inject(MainActivity mainActivity);
}