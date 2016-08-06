package com.justwayward.reader.component;

import com.justwayward.reader.ui.fragment.RecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface RecommendFragmentComponent {
    RecommendFragment inject(RecommendFragment recommendFragment);
}