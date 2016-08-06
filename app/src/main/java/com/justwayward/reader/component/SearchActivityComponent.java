package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.SearchActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SearchActivityComponent {
    SearchActivity inject(SearchActivity searchActivity);
}