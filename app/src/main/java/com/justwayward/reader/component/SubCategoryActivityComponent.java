package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.SubCategoryListActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SubCategoryActivityComponent {
    SubCategoryListActivity inject(SubCategoryListActivity categoryListActivity);
}