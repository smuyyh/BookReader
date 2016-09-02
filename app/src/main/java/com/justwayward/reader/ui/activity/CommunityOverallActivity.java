package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.view.SelectionLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CommunityOverallActivity extends BaseActivity implements SelectionLayout.OnSelectListener {


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CommunityOverallActivity.class));
    }

    @Bind(R.id.slOverall)
    SelectionLayout slOverall;

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_overall;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("综合讨论区");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        List<String> list0 = new ArrayList<String>() {{
            add("全部");
            add("精品");
        }};
        List<String> list1 = new ArrayList<String>() {{
            add("默认排序");
            add("最新发布");
            add("最多评论");
        }};
        slOverall.setData(list0, list1);
        slOverall.setOnSelectListener(this);
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onSelect(int index, int position, String title) {

    }
}
