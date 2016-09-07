package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.BookDetailActivity;
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.activity.BooksByTagActivity;
import com.justwayward.reader.ui.activity.SearchActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BookComponent {
    BookDetailActivity inject(BookDetailActivity bookDetailActivity);

    BookReadActivity inject(BookReadActivity bookReadActivity);

    BooksByTagActivity inject(BooksByTagActivity tagBookListActivity);

    SearchActivity inject(SearchActivity searchActivity);
}