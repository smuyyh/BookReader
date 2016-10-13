package com.justwayward.reader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.view.pdfview.PDFViewPager;

import butterknife.Bind;

public class ReadPDFActivity extends BaseActivity {

    private PDFViewPager pdfViewPager;

    @Bind(R.id.llPdfRoot)
    LinearLayout llPdfRoot;
    private int startX = 0;
    private int startY = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_pdf;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        String filePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        mCommonToolbar.setTitle(fileName);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            String filePath = Uri.decode(getIntent().getDataString().replace("file://", ""));

            pdfViewPager = new PDFViewPager(this, filePath);
            llPdfRoot.addView(pdfViewPager);
        }
    }

    @Override
    public void configViews() {

    }
}
