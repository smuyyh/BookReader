package com.justwayward.reader.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.justwayward.reader.utils.ScreenUtils;

/**
 * Created by qzsang on 2016/8/11.
 */

public class BookReadFrameLayout extends FrameLayout {

    private float preX;
    private float preY;
    private boolean isChange;//用于标记位置是否滑动过
    private OnScreenClickListener mOnScreenClickListener;

    public BookReadFrameLayout(Context context) {
        super(context);
    }

    public BookReadFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookReadFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BookReadFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = ev.getRawX();
                preY = ev.getRawY();
                isChange = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isChange && (preX != ev.getRawX() || preY != ev.getRawY())) {
                    isChange = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                //位置没变过就默认为点击事件
                if (!isChange && preX == ev.getRawX() && preY == ev.getRawY()
                        && mOnScreenClickListener != null) {
                    int sHeight = ScreenUtils.getScreenHeight();
                    int sWidth = ScreenUtils.getScreenWidth();
                    int heightOff = sHeight/4;
                    int widthOff =  sWidth/3;
                    if (preX < widthOff || preY < heightOff) {
                        mOnScreenClickListener.onSideClick(true);
                        break;
                    }

                    if (preX > widthOff*2 || preY > heightOff*3) {
                        mOnScreenClickListener.onSideClick(false);
                        break;
                    }

                    mOnScreenClickListener.onCenterClick();
                }

                break;

        }


        return super.dispatchTouchEvent(ev);
    }


    public void setOnScreenClickListener(OnScreenClickListener mOnScreenClickListener) {
        this.mOnScreenClickListener = mOnScreenClickListener;
    }

    public interface OnScreenClickListener {

        void onSideClick(boolean isLeft) ;

        void onCenterClick() ;
    }
}
