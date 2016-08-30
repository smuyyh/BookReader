package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.CategoryListActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface CategoryListActivityComponent {
    CategoryListActivity inject(CategoryListActivity categoryListActivity);
}