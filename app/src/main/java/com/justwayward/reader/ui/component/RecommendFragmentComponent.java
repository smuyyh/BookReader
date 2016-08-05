package com.justwayward.reader.ui.component;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.module.RecommendFragmentModule;
import com.justwayward.reader.ui.fragment.RecommendFragment;

import dagger.Component;

@Component(modules = RecommendFragmentModule.class, dependencies = AppComponent.class)
public interface RecommendFragmentComponent {
    RecommendFragment inject(RecommendFragment recommendFragment);
}