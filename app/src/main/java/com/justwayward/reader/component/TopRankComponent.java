package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.TopRankActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface TopRankComponent {
    TopRankActivity inject(TopRankActivity rankActivity);
}