package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.BookReadActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BookReadActivityComponent {
    BookReadActivity inject(BookReadActivity bookReadActivity);
}