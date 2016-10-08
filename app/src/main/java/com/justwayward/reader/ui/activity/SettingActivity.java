package com.justwayward.reader.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerMainComponent;
import com.justwayward.reader.manager.CacheManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xiaoshu on 2016/10/8.
 */
public class SettingActivity extends BaseActivity{


    @Bind(R.id.mTvSort)
    TextView mTvSort;
    @Bind(R.id.tvCacheSize)
    TextView mTvCacheSize;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
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
        mCommonToolbar.setTitle("设置");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String cachesize=CacheManager.getInstance().getCacheSize();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvCacheSize.setText(cachesize);
                    }
                });

            }
        }).start();

    }


    @Override
    public void configViews() {

    }

    @OnClick(R.id.bookshelfSort)
    public void onClickBookShelfSort(){
       new AlertDialog.Builder(mContext)
               .setTitle("书架排序方式")
               .setSingleChoiceItems(getResources().getStringArray(R.array.setting_dialog_choice), 0, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       mTvSort.setText(getResources().getStringArray(R.array.setting_dialog_choice)[which]);
                       dialog.dismiss();
                   }
               })
               .create().show();
    }

    @OnClick(R.id.cleanCache)
    public void onClickCleanCache(){
         final boolean selected[]={false};
        new AlertDialog.Builder(mContext)
                .setTitle("清除缓存")
                .setCancelable(true)
                .setMessage("是否要清除缓存")
                .setMultiChoiceItems(new String[]{"同时删除阅读记录"}, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        switch (which){
                            case 0:
                                selected[0]=true;
                                break;
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CacheManager.getInstance().clearCache();
                                final String cacheSize=CacheManager.getInstance().getCacheSize();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvCacheSize.setText(cacheSize);
                                    }
                                });
                            }
                        }).start();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
}
