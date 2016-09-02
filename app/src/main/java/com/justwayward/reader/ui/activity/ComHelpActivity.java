package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseCommuniteActivity;
import com.justwayward.reader.component.AppComponent;

import java.util.List;

public class ComHelpActivity extends BaseCommuniteActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ComHelpActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_help;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("书荒互助区");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    protected List<List<String>> getTabList() {
        return list1;
    }

    @Override
    public void configViews() {

    }
}
