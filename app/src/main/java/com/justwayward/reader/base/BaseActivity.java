package com.justwayward.reader.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.justwayward.reader.ReaderApplication;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.utils.StatusBarCompat;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected int statusBarColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (statusBarColor != 0) {
            StatusBarCompat.compat(this, statusBarColor);
        } else
            StatusBarCompat.compat(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mContext = this;
        ButterKnife.bind(this);
        setupActivityComponent(ReaderApplication.getsInstance().getAppComponent());
        initToolBar();
        initDatas();
        configViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract int getLayoutId();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public abstract void initToolBar();

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    protected void gone(final View... views) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (views != null && views.length > 0) {
                    for (View view : views) {
                        if (view != null) {
                            view.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    protected void visible(final View... views) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (views != null && views.length > 0) {
                    for (View view : views) {
                        if (view != null) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}
