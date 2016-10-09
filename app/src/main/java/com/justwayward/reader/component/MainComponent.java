package com.justwayward.reader.component;

import com.justwayward.reader.ui.activity.MainActivity;
import com.justwayward.reader.ui.activity.SettingActivity;
import com.justwayward.reader.ui.activity.WifiBookActivity;
import com.justwayward.reader.ui.fragment.RecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MainComponent {
    MainActivity inject(MainActivity activity);

    RecommendFragment inject(RecommendFragment fragment);

    SettingActivity inject(SettingActivity activity);
    WifiBookActivity inject(WifiBookActivity activity);
}