/**
 * Copyright 2016 JustWayward Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.view.pdfview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

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
                .setOnPageClickListener(clickListener)
                .create());
    }

    private OnPageClickListener clickListener = new OnPageClickListener() {

        @Override
        public void onPageTap(View view, float x, float y) {
            int item = getCurrentItem();
            int total = getChildCount();

            if (x < 0.33f && item > 0) {
                item -= 1;
                setCurrentItem(item);
            } else if (x >= 0.67f && item < total - 1) {
                item += 1;
                setCurrentItem(item);
            }
        }
    };

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
