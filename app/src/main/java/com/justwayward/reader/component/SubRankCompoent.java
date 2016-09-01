package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.SubRankActivity;

import dagger.Component;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
@Component(dependencies = AppComponent.class)
public interface SubRankCompoent {
    SubRankActivity inject(SubRankActivity rankActivity);
}
