package com.justwayward.reader.ui.component;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.ui.activity.SearchActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SearchActivityComponent {
    SearchActivity inject(SearchActivity searchActivity);
}