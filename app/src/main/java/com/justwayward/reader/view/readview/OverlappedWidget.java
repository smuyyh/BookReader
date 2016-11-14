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
package com.justwayward.reader.view.readview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;

import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.manager.ThemeManager;
import com.justwayward.reader.utils.ToastUtils;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/10/18.
 */
public class OverlappedWidget extends BaseReadView {

    private Path mPath0;

    GradientDrawable mBackShadowDrawableLR;
    GradientDrawable mBackShadowDrawableRL;

    private float actiondownX, actiondownY;

    public OverlappedWidget(Context context, String bookId,
                            List<BookToc.mixToc.Chapters> chaptersList,
                            OnReadStateChangeListener listener) {
        super(context, bookId, chaptersList, listener);

        mTouch.x = 0.01f;
        mTouch.y = 0.01f;

        mPath0 = new Path();

        int[] mBackShadowColors = new int[]{0xaa666666, 0x666666};
        mBackShadowDrawableRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors);
        mBackShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mBackShadowDrawableLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }

    @Override
    protected void drawCurrentPageArea(Canvas canvas) {
        mPath0.reset();
        mPath0.moveTo(mTouch.x, 0);
        mPath0.lineTo(mTouch.x, mScreenHeight);
        mPath0.lineTo(mScreenWidth, mScreenHeight);
        mPath0.lineTo(mScreenWidth, 0);
        mPath0.lineTo(mTouch.x, 0);
        mPath0.close();

        canvas.save();
        if (actiondownX > mScreenWidth >> 1) {
            canvas.clipPath(mPath0, Region.Op.XOR);
            canvas.drawBitmap(mCurPageBitmap, -(mScreenWidth - mTouch.x), 0, null);
        } else {
            canvas.clipPath(mPath0);
            canvas.drawBitmap(mCurPageBitmap, mTouch.x, 0, null);
        }
        try {
            canvas.restore();
        } catch (Exception e) {

        }
    }

    @Override
    protected void drawCurrentPageShadow(Canvas canvas) {
        canvas.save();
        GradientDrawable shadow;
        if (actiondownX > mScreenWidth >> 1) {
            shadow = mBackShadowDrawableLR;
        } else {
            shadow = mBackShadowDrawableRL;
        }
        shadow.setBounds((int) mTouch.x - 5, 0, (int) mTouch.x + 5, mScreenHeight);
        shadow.draw(canvas);
        try {
            canvas.restore();
        } catch (Exception e) {

        }
    }

    @Override
    protected void drawCurrentBackArea(Canvas canvas) {
        // none
    }

    @Override
    protected void drawNextPageAreaAndShadow(Canvas canvas) {
        canvas.save();
        if (actiondownX > mScreenWidth >> 1) {
            canvas.clipPath(mPath0);
            canvas.drawBitmap(mNextPageBitmap, 0, 0, null);
        } else {
            canvas.clipPath(mPath0, Region.Op.XOR);
            canvas.drawBitmap(mNextPageBitmap, 0, 0, null);
        }
        try {
            canvas.restore();
        } catch (Exception e) {

        }
    }

    @Override
    protected void calcPoints() {

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            mTouch.x = x;
            mTouch.y = y;
            postInvalidate();
        }
    }

    private void startAnimation() {
        int dx;
        if (actiondownX > mScreenWidth / 2) {
            dx = (int) (-mTouch.x);
        } else {
            dx = (int) (mScreenWidth - mTouch.x);
        }
        mScroller.startScroll((int) mTouch.x, (int) mTouch.y, dx, 0, 700);
    }

    public void abortAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
    }

    public void restoreAnimation() {
        int dx;
        if (actiondownX > mScreenWidth / 2) {
            dx = (int) (mScreenWidth - mTouch.x);
        } else {
            dx = (int) (-mTouch.x);
        }
        mScroller.startScroll((int) mTouch.x, (int) mTouch.y, dx, 0, 300);
    }

    private int dx, dy;
    private long et = 0;
    private boolean cancel = false;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                et = System.currentTimeMillis();
                dx = (int) e.getX();
                dy = (int) e.getY();
                mTouch.x = dx;
                mTouch.y = dy;
                actiondownX = dx;
                actiondownY = dy;
                if (actiondownX >= mScreenWidth / 3 && actiondownX <= mScreenWidth * 2 / 3
                        && actiondownY >= mScreenHeight / 3 && actiondownY <= mScreenHeight * 2 / 3) {
                    listener.onCenterClick();
                    return false;//停止向下分发事件
                }
                pagefactory.onDraw(mCurrentPageCanvas);
                if (actiondownX < mScreenWidth / 2) {// 从左翻
                    if (!pagefactory.prePage()) {
                        ToastUtils.showSingleToast("没有上一页啦");
                        return false;
                    }
                    abortAnimation();
                    pagefactory.onDraw(mNextPageCanvas);
                } else if (actiondownX >= mScreenWidth / 2) {// 从右翻
                    if (!pagefactory.nextPage()) {
                        ToastUtils.showSingleToast("没有下一页啦");
                        return false;
                    }
                    abortAnimation();
                    pagefactory.onDraw(mNextPageCanvas);
                }
                listener.onFlip();
                setBitmaps(mCurPageBitmap, mNextPageBitmap);
                break;
            case MotionEvent.ACTION_MOVE:
                int mx = (int) e.getX();
                int my = (int) e.getY();
                if ((actiondownX < mScreenWidth / 2 && mx < mTouch.x) || (actiondownX > mScreenWidth / 2 && mx > mTouch.x)) {
                    cancel = true;
                } else {
                    cancel = false;
                }
                mTouch.x = mx;
                mTouch.y = my;
                this.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:

                long t = System.currentTimeMillis();
                int ux = (int) e.getX();
                int uy = (int) e.getY();

                if ((Math.abs(ux - dx) < 10) && (Math.abs(uy - dy) < 10)) {
                    if ((t - et < 1000)) { // 单击
                        startAnimation();
                    } else { // 长按
                        pagefactory.cancelPage();
                        restoreAnimation();
                    }
                    postInvalidate();
                    return true;
                }
                if (cancel) {
                    pagefactory.cancelPage();
                    restoreAnimation();
                    postInvalidate();
                } else {
                    startAnimation();
                    postInvalidate();
                }
                cancel = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                cancel = false;
                break;
            default:
                break;
        }
        return true;
    }

    public void setBitmaps(Bitmap bm1, Bitmap bm2) {
        mCurPageBitmap = bm1;
        mNextPageBitmap = bm2;
    }

    @Override
    public synchronized void setTheme(int theme) {
        mTouch.x = 0.1f;
        mTouch.y = 0.1f;
        Bitmap bg = ThemeManager.getThemeDrawable(theme);
        if (bg != null) {
            pagefactory.setBgBitmap(bg);
            if (isPrepared) {
                pagefactory.onDraw(mCurrentPageCanvas);
                pagefactory.onDraw(mNextPageCanvas);
                postInvalidate();
            }
        }
        if (theme < 5) {
            SettingManager.getInstance().saveReadTheme(theme);
        }
    }
}
