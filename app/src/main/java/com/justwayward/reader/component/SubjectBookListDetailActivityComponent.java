package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.SubjectBookListDetailActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SubjectBookListDetailActivityComponent {
    SubjectBookListDetailActivity inject(SubjectBookListDetailActivity categoryListActivity);
}