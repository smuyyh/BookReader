package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.MainActivity;
import com.justwayward.reader.ui.fragment.RecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MainComponent {
    MainActivity inject(MainActivity mainActivity);

    RecommendFragment inject(RecommendFragment recommendFragment);
}