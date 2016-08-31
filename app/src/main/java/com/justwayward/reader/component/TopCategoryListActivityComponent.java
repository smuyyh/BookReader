package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.TopCategoryListActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface TopCategoryListActivityComponent {
    TopCategoryListActivity inject(TopCategoryListActivity categoryListActivity);
}