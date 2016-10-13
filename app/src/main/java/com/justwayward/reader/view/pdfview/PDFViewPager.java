package com.justwayward.reader.view.pdfview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

public class PDFViewPager extends ViewPager {

    protected Context context;

    public PDFViewPager(Context context, String pdfPath) {
        super(context);
        this.context = context;
        init(pdfPath);
    }

    protected void init(String pdfPath) {
        setClickable(true);
        initAdapter(context, pdfPath);
    }

    protected void initAdapter(Context context, String pdfPath) {
        setAdapter(new PDFPagerAdapter.Builder(context)
                .setPdfPath(pdfPath)
                .setOffScreenSize(getOffscreenPageLimit())
                .create());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
