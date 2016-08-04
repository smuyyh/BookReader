package com.justwayward.reader.ui.activity;

import android.os.Bundle;

import com.justwayward.reader.AppComponent;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.module.MainActivityModule;
import com.justwayward.reader.ui.component.DaggerMainActivityComponent;
import com.justwayward.reader.ui.contract.MainContract;
import com.justwayward.reader.ui.presenter.MainActivityPresenter;

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
