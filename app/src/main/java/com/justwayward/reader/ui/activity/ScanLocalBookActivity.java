package com.justwayward.reader.ui.activity;

import android.widget.ListView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 扫描本地书籍
 *
 * @author yuyh.
 * @date 2016/10/9.
 */
public class ScanLocalBookActivity extends BaseActivity {

    @Bind(R.id.lvScanBookList)
    ListView lvScanBookList;

    private ArrayList<File> list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_local_book;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("扫描本地书籍");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        list = new ArrayList<>();
    }

    @Override
    public void configViews() {

    }

}
