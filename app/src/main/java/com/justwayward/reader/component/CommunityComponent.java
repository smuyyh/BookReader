package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.BookDiscussionDetailActivity;
import com.justwayward.reader.ui.activity.BookHelpDetailActivity;
import com.justwayward.reader.ui.activity.BookReviewDetailActivity;
import com.justwayward.reader.ui.fragment.BookDiscussionFragment;
import com.justwayward.reader.ui.fragment.BookHelpFragment;
import com.justwayward.reader.ui.fragment.BookReviewFragment;

import dagger.Component;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
@Component(dependencies = AppComponent.class)
public interface CommunityComponent {

    BookDiscussionFragment inject(BookDiscussionFragment fragment);

    BookDiscussionDetailActivity inject(BookDiscussionDetailActivity activity);

    BookReviewFragment inject(BookReviewFragment fragment);

    BookReviewDetailActivity inject(BookReviewDetailActivity activity);

    BookHelpFragment inject(BookHelpFragment fragment);

    BookHelpDetailActivity inject(BookHelpDetailActivity activity);
}
