package com.justwayward.reader.component;

import com.justwayward.reader.ui.fragment.SubCategoryFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SubCategoryFragmentComponent {
    SubCategoryFragment inject(SubCategoryFragment recommendFragment);
}