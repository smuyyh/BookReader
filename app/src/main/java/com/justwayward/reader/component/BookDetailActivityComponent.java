package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.BookDetailActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BookDetailActivityComponent {
    BookDetailActivity inject(BookDetailActivity bookDetailActivity);
}