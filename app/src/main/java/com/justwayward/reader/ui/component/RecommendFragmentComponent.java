package com.justwayward.reader.ui.component;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.ui.fragment.RecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface RecommendFragmentComponent {
    RecommendFragment inject(RecommendFragment recommendFragment);
}