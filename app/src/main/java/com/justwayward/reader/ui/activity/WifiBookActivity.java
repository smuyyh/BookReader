package com.justwayward.reader.ui.activity;

import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerMainComponent;
import com.justwayward.reader.utils.NetworkUtils;

import butterknife.Bind;

/**
 * Created by xiaoshu on 2016/10/9.
 */
public class WifiBookActivity extends BaseActivity {

    @Bind(R.id.mTvWifiName)
    TextView mTvWifiName;
    @Bind(R.id.mTvWifiIp)
    TextView mTvWifiIp;
    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi_book;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("Wi-Fi传书");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mTvWifiName.setText(NetworkUtils.getConnectWifiSsid(mContext).replace("\"",""));
        mTvWifiIp.setText("http://"+NetworkUtils.getConnectWifiIp(mContext)+":5000");
    }

    @Override
    public void configViews() {

    }
}
