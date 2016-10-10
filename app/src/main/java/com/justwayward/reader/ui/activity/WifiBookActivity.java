package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerMainComponent;
import com.justwayward.reader.utils.NetworkUtils;
import com.justwayward.reader.wifitransfer.custom.Defaults;
import com.justwayward.reader.wifitransfer.nano.ServerRunner;

import butterknife.Bind;

/**
 * Created by xiaoshu on 2016/10/9.
 */
public class WifiBookActivity extends BaseActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WifiBookActivity.class));
    }

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
        mCommonToolbar.setTitle("WiFi传书");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mTvWifiName.setText(NetworkUtils.getConnectWifiSsid(mContext).replace("\"", ""));
        mTvWifiIp.setText("http://" + NetworkUtils.getConnectWifiIp(mContext) + ":" + Defaults.getPort());

        //WebService.start(this);
        ServerRunner.startServer(8080);
    }

    @Override
    public void configViews() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //WebService.stop(this);
        ServerRunner.stopServer();
    }
}
