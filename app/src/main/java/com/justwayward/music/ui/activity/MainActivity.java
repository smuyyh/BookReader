package com.justwayward.music.ui.activity;

import android.os.Bundle;

import com.justwayward.music.AppComponent;
import com.justwayward.music.R;
import com.justwayward.music.base.BaseActivity;
import com.justwayward.music.module.MainActivityModule;
import com.justwayward.music.ui.component.DaggerMainActivityComponent;
import com.justwayward.music.ui.contract.MainContract;
import com.justwayward.music.ui.presenter.MainActivityPresenter;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainContract.View{


    @Inject
    MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.getPlayerList();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }
}
