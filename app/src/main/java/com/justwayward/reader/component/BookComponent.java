package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.BookDetailActivity;
import com.justwayward.reader.ui.activity.BookReadActivity;
import com.justwayward.reader.ui.activity.BooksByTagActivity;
import com.justwayward.reader.ui.activity.SearchActivity;
import com.justwayward.reader.ui.activity.SearchByAuthorActivity;
import com.justwayward.reader.ui.fragment.BookDetailDiscussionFragment;
import com.justwayward.reader.ui.fragment.BookDetailReviewFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BookComponent {
    BookDetailActivity inject(BookDetailActivity activity);

    BookReadActivity inject(BookReadActivity activity);

    BooksByTagActivity inject(BooksByTagActivity activity);

    SearchActivity inject(SearchActivity activity);

    SearchByAuthorActivity inject(SearchByAuthorActivity activity);

    BookDetailReviewFragment inject(BookDetailReviewFragment fragment);

    BookDetailDiscussionFragment inject(BookDetailDiscussionFragment fragment);
}