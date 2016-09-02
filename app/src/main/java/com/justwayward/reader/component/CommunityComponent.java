package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.ComOverallDetailActivity;
import com.justwayward.reader.ui.fragment.CommunityBookHelpFragment;
import com.justwayward.reader.ui.fragment.CommunityBookReviewFragment;
import com.justwayward.reader.ui.fragment.CommunityOverallFragment;

import dagger.Component;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
@Component(dependencies = AppComponent.class)
public interface CommunityComponent {

    CommunityOverallFragment inject(CommunityOverallFragment fragment);

    ComOverallDetailActivity inject(ComOverallDetailActivity activity);

    CommunityBookReviewFragment inject(CommunityBookReviewFragment fragment);

    CommunityBookHelpFragment inject(CommunityBookHelpFragment fragment);
}
