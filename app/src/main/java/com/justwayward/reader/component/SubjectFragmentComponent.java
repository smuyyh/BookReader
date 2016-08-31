package com.justwayward.reader.component;

import com.justwayward.reader.ui.fragment.SubjectFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface SubjectFragmentComponent {
    SubjectFragment inject(SubjectFragment subjectFragment);
}