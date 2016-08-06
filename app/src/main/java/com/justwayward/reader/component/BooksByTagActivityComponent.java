package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.BooksByTagActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BooksByTagActivityComponent {
    BooksByTagActivity inject(BooksByTagActivity tagBookListActivity);
}