/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justwayward.reader.R;

import java.util.List;


/**
 * Created by Administrator on 2016/8/4.
 */
public class RVPIndicator extends LinearLayout {

    // 指示器风格-图标
    private static final int STYLE_BITMAP = 0;
    // 指示器风格-下划线
    private static final int STYLE_LINE = 1;
    // 指示器风格-方形背景
    private static final int STYLE_SQUARE = 2;
    // 指示器风格-三角形
    private static final int STYLE_TRIANGLE = 3;

    /*
     * 系统默认:Tab数量
     */
    private static final int D_TAB_COUNT = 3;

    /*
     * 系统默认:文字正常时颜色
     */
    private static final int D_TEXT_COLOR_NORMAL = Color.parseColor("#000000");

    /*
     * 系统默认:文字选中时颜色
     */
    private static final int D_TEXT_COLOR_HIGHLIGHT = Color
            .parseColor("#FF0000");

    /*
     * 系统默认:指示器颜色
     */
    private static final int D_INDICATOR_COLOR = Color.parseColor("#f29b76");

    /**
     * tab上的内容
     */
    private List<String> mTabTitles;

    /**
     * 可见tab数量
     */
    private int mTabVisibleCount = D_TAB_COUNT;

    /**
     * 与之绑定的ViewPager
     */
    public ViewPager mViewPager;

    /**
     * 文字大小
     */
    private int mTextSize = 16;

    /**
     * 文字正常时的颜色
     */
    private int mTextColorNormal = D_TEXT_COLOR_NORMAL;

    /**
     * 文字选中时的颜色
     */
    private int mTextColorHighlight = D_TEXT_COLOR_HIGHLIGHT;

    /**
     * 指示器正常时的颜色
     */
    private int mIndicatorColor = D_INDICATOR_COLOR;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 矩形
     */
    private Rect mRectF;

    /**
     * bitmap
     */
    private Bitmap mBitmap;

    /**
     * 指示器高
     */
    private int mIndicatorHeight = 5;

    /**
     * 指示器宽
     */
    private int mIndicatorWidth = getWidth() / mTabVisibleCount;

    /**
     * 三角形的宽度为单个Tab的1/6
     */
    private static final float RADIO_TRIANGEL = 1.0f / 6;

    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;

    /**
     * 指示器风格
     */
    private int mIndicatorStyle = STYLE_LINE;

    /**
     * 曲线path
     */
    private Path mPath;

    /**
     * viewPage初始下标
     */
    private int mPosition = 0;

    public RVPIndicator(Context context) {
        this(context, null);
    }

    public RVPIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RVPIndicator);

        mTabVisibleCount = a.getInt(R.styleable.RVPIndicator_item_count,
                D_TAB_COUNT);
        mTextColorNormal = a
                .getColor(R.styleable.RVPIndicator_text_color_normal,
                        D_TEXT_COLOR_NORMAL);
        mTextColorHighlight = a.getColor(
                R.styleable.RVPIndicator_text_color_hightlight,
                D_TEXT_COLOR_HIGHLIGHT);
        mTextSize = a.getDimensionPixelSize(R.styleable.RVPIndicator_text_size,
                16);
        mIndicatorColor = a.getColor(R.styleable.RVPIndicator_indicator_color,
                D_INDICATOR_COLOR);
        mIndicatorStyle = a.getInt(R.styleable.RVPIndicator_indicator_style,
                STYLE_LINE);

        Drawable drawable = a
                .getDrawable(R.styleable.RVPIndicator_indicator_src);

        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                mBitmap = ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof NinePatchDrawable) {
                // .9图处理
                Bitmap bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                mBitmap = bitmap;

            }
        } else {
            mBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.heart_love);
        }

        a.recycle();

        /**
         * 设置画笔
         */
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Style.FILL);

    }

    /**
     * 初始化指示器
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        switch (mIndicatorStyle) {

            case STYLE_LINE:

			/*
			 * 下划线指示器:宽与item相等,高是item的1/10
			 */
                mIndicatorWidth = w / mTabVisibleCount;
                mIndicatorHeight = h / 10;
                mTranslationX = 0;
                mRectF = new Rect(0, 0, mIndicatorWidth, mIndicatorHeight);

                break;
            case STYLE_SQUARE:
            case STYLE_BITMAP:

			/*
			 * 方形/图标指示器:宽,高与item相等
			 */
                mIndicatorWidth = w / mTabVisibleCount;
                mIndicatorHeight = h;
                mTranslationX = 0;
                mRectF = new Rect(0, 0, mIndicatorWidth, mIndicatorHeight);

                break;
            case STYLE_TRIANGLE:

			/*
			 * 三角形指示器:宽与item(1/4)相等,高是item的1/4
			 */
                //mIndicatorWidth = w / mTabVisibleCount / 4;
                // mIndicatorHeight = h / 4;
                mIndicatorWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGEL);// 1/6 of  width  ;
                mIndicatorHeight = (int) (mIndicatorWidth / 2 / Math.sqrt(2));
                mTranslationX = 0;

                break;
        }
        // 初始化tabItem
        initTabItem();

        // 高亮
        setHighLightTextView(mPosition);
    }

    /**
     * 绘制指示器(子view)
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 保存画布
        canvas.save();

        switch (mIndicatorStyle) {

            case STYLE_BITMAP:

                canvas.translate(mTranslationX, 0);
                canvas.drawBitmap(mBitmap, null, mRectF, mPaint);

                break;
            case STYLE_LINE:

                canvas.translate(mTranslationX, getHeight() - mIndicatorHeight);
                canvas.drawRect(mRectF, mPaint);

                break;
            case STYLE_SQUARE:

                canvas.translate(mTranslationX, 0);
                canvas.drawRect(mRectF, mPaint);

                break;
            case STYLE_TRIANGLE:

                canvas.translate(mTranslationX, 0);
                // 笔锋圆滑度
                // mPaint.setPathEffect(new CornerPathEffect(10));
                mPath = new Path();
                int midOfTab = getWidth() / mTabVisibleCount / 2;
                mPath.moveTo(midOfTab, getHeight() - mIndicatorHeight);
                mPath.lineTo(midOfTab - mIndicatorWidth / 2, getHeight());
                mPath.lineTo(midOfTab + mIndicatorWidth / 2, getHeight());
                mPath.close();
                canvas.drawPath(mPath, mPaint);

                break;
        }

        // 恢复画布
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 设置tab上的内容
     *
     * @param datas
     */
    public void setTabItemTitles(List<String> datas) {
        this.mTabTitles = datas;
    }

    /**
     * 设置关联viewPager
     *
     * @param viewPager
     * @param pos
     */
    @SuppressWarnings("deprecation")
    public void setViewPager(ViewPager viewPager, int pos) {
        this.mViewPager = viewPager;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 设置文本高亮
                setHighLightTextView(position);

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

                // scoll
                onScoll(position, positionOffset);

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

        // 设置当前页
        mViewPager.setCurrentItem(pos);
        mPosition = pos;

    }

    /**
     *
     * 初始化tabItem
     *
     * @author Ruffian
     */
    private void initTabItem() {

        if (mTabTitles != null && mTabTitles.size() > 0) {
            if (this.getChildCount() != 0) {
                this.removeAllViews();
            }

            for (String title : mTabTitles) {
                addView(createTextView(title));
            }

            // 设置点击事件
            setItemClickEvent();
        }

    }

    /**
     * 设置点击事件
     */
    private void setItemClickEvent() {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    /**
     * 设置文本高亮
     *
     * @param position
     */
    private void setHighLightTextView(int position) {

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                if (i == position) {
                    ((TextView) view).setTextColor(mTextColorHighlight);
                } else {
                    ((TextView) view).setTextColor(mTextColorNormal);
                }
            }
        }
    }

    /**
     * 滚动
     *
     * @param position
     * @param offset
     */
    private void onScoll(int position, float offset) {

        // 不断改变偏移量，invalidate
        mTranslationX = getWidth() / mTabVisibleCount * (position + offset);

        int tabWidth = getWidth() / mTabVisibleCount;

        // 容器滚动，当移动到倒数第二个的时候，开始滚动
        if (offset > 0 && position >= (mTabVisibleCount - 2)
                && getChildCount() > mTabVisibleCount
                && position < (getChildCount() - 2)) {
            if (mTabVisibleCount != 1) {

                int xValue = (position - (mTabVisibleCount - 2)) * tabWidth
                        + (int) (tabWidth * offset);
                this.scrollTo(xValue, 0);

            } else {
                // 为count为1时 的特殊处理
                this.scrollTo(position * tabWidth + (int) (tabWidth * offset),
                        0);
            }
        }

        invalidate();
    }

    /**
     * 创建标题view
     *
     * @param text
     * @return
     */
    private TextView createTextView(String text) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getWidth() / mTabVisibleCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(mTextColorNormal);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 对外的ViewPager的回调接口
     *
     * @author Ruffian
     *
     */
    public interface PageChangeListener {
        void onPageScrolled(int position, float positionOffset,
                            int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }

}
