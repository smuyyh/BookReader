package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.SubCategoryListActivity;
import com.justwayward.reader.ui.activity.SubRankActivity;
import com.justwayward.reader.ui.activity.SubjectBookListActivity;
import com.justwayward.reader.ui.activity.SubjectBookListDetailActivity;
import com.justwayward.reader.ui.activity.TopCategoryListActivity;
import com.justwayward.reader.ui.activity.TopRankActivity;
import com.justwayward.reader.ui.fragment.SubCategoryFragment;
import com.justwayward.reader.ui.fragment.SubRankFragment;
import com.justwayward.reader.ui.fragment.SubjectFragment;

import dagger.Component;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
@Component(dependencies = AppComponent.class)
public interface FindComponent {

    /** 分类 **/
    TopCategoryListActivity inject(TopCategoryListActivity activity);

    SubCategoryListActivity inject(SubCategoryListActivity activity);

    SubCategoryFragment inject(SubCategoryFragment fragment);

    /** 排行 **/
    TopRankActivity inject(TopRankActivity activity);

    SubRankActivity inject(SubRankActivity activity);

    SubRankFragment inject(SubRankFragment fragment);

    /** 主题书单 **/
    SubjectBookListActivity inject(SubjectBookListActivity subjectBookListActivity);

    SubjectFragment inject(SubjectFragment subjectFragment);

    SubjectBookListDetailActivity inject(SubjectBookListDetailActivity categoryListActivity);
}
