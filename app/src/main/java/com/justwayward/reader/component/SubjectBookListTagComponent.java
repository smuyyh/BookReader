package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.SubjectBookListActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SubjectBookListTagComponent {
    SubjectBookListActivity inject(SubjectBookListActivity subjectBookListActivity);
}