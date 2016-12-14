package com.justwayward.reader.view.epubview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * @author yuyh.
 * @date 2016/12/13.
 */
public class VerticalSeekbar extends SeekBar {

    private OnSeekBarChangeListener mOnChangeListener;
    private int mLastProgress = 0;

    public VerticalSeekbar(Context context) {
        this(context, null);
    }

    public VerticalSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalSeekbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(90);
        c.translate(0, -getWidth());

        super.onDraw(c);
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mOnChangeListener) {
        this.mOnChangeListener = mOnChangeListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mOnChangeListener != null)
                    mOnChangeListener.onStartTrackingTouch(this);
                setPressed(true);
                setSelected(true);
                break;
            case MotionEvent.ACTION_MOVE:
                super.onTouchEvent(event);
                int progress = getMax() - (int) (getMax() * event.getY() / getHeight());

                if (progress < 0) {
                    progress = 0;
                }
                if (progress > getMax()) {
                    progress = getMax();
                }
                setProgress(progress);
                if (progress != mLastProgress) {
                    mLastProgress = progress;
                    if (mOnChangeListener != null)
                        mOnChangeListener.onProgressChanged(this, progress, true);
                }

                onSizeChanged(getWidth(), getHeight(), 0, 0);
                setPressed(true);
                setSelected(true);
                break;
            case MotionEvent.ACTION_UP:
                if (mOnChangeListener != null)
                    mOnChangeListener.onStopTrackingTouch(this);
                setPressed(false);
                setSelected(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                super.onTouchEvent(event);
                setPressed(false);
                setSelected(false);
                break;
        }
        return true;
    }

    public synchronized void setProgressAndThumb(int progress) {
        setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        if (progress != mLastProgress) {
            mLastProgress = progress;
            if (mOnChangeListener != null)
                mOnChangeListener.onProgressChanged(this, progress, true);
        }
    }

    public synchronized void setMaximum(int maximum) {
        setMax(maximum);
    }

    public synchronized int getMaximum() {
        return getMax();
    }

}