package com.justwayward.reader.ui.component;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.ui.activity.MainActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MainActivityComponent {
    MainActivity inject(MainActivity mainActivity);
}