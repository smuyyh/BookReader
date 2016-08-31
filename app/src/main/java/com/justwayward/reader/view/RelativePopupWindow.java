package com.justwayward.reader.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.widget.PopupWindowCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RelativePopupWindow extends PopupWindow {

    @IntDef({
            VerticalPosition.CENTER,
            VerticalPosition.ABOVE,
            VerticalPosition.BELOW,
            VerticalPosition.ALIGN_TOP,
            VerticalPosition.ALIGN_BOTTOM,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface VerticalPosition {
        int CENTER = 0;
        int ABOVE = 1;
        int BELOW = 2;
        int ALIGN_TOP = 3;
        int ALIGN_BOTTOM = 4;
    }

    @IntDef({
            HorizontalPosition.CENTER,
            HorizontalPosition.LEFT,
            HorizontalPosition.RIGHT,
            HorizontalPosition.ALIGN_LEFT,
            HorizontalPosition.ALIGN_RIGHT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HorizontalPosition {
        int CENTER = 0;
        int LEFT = 1;
        int RIGHT = 2;
        int ALIGN_LEFT = 3;
        int ALIGN_RIGHT = 4;
    }

    public RelativePopupWindow(Context context) {
        super(context);
    }

    public RelativePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RelativePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RelativePopupWindow() {
        super();
    }

    public RelativePopupWindow(View contentView) {
        super(contentView);
    }

    public RelativePopupWindow(int width, int height) {
        super(width, height);
    }

    public RelativePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public RelativePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    /**
     * Show at relative position to anchor View.
     *
     * @param anchor   Anchor View
     * @param vertPos  Vertical Position Flag
     * @param horizPos Horizontal Position Flag
     */
    public void showOnAnchor(@NonNull View anchor, @VerticalPosition int vertPos, @HorizontalPosition int horizPos) {
        showOnAnchor(anchor, vertPos, horizPos, 0, 0);
    }

    /**
     * Show at relative position to anchor View with translation.
     *
     * @param anchor   Anchor View
     * @param vertPos  Vertical Position Flag
     * @param horizPos Horizontal Position Flag
     * @param x        Translation X
     * @param y        Translation Y
     */
    public void showOnAnchor(@NonNull View anchor, @VerticalPosition int vertPos, @HorizontalPosition int horizPos, int x, int y) {
        View contentView = getContentView();
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int measuredW = contentView.getMeasuredWidth();
        final int measuredH = contentView.getMeasuredHeight();
        switch (vertPos) {
            case VerticalPosition.ABOVE:
                y -= measuredH + anchor.getHeight();
                break;
            case VerticalPosition.ALIGN_BOTTOM:
                y -= measuredH;
                break;
            case VerticalPosition.CENTER:
                y -= anchor.getHeight() / 2 + measuredH / 2;
                break;
            case VerticalPosition.ALIGN_TOP:
                y -= anchor.getHeight();
                break;
            case VerticalPosition.BELOW:
                // Default position.
                break;
        }
        switch (horizPos) {
            case HorizontalPosition.LEFT:
                x -= measuredW;
                break;
            case HorizontalPosition.ALIGN_RIGHT:
                x -= measuredW - anchor.getWidth();
                break;
            case HorizontalPosition.CENTER:
                x += anchor.getWidth() / 2 - measuredW / 2;
                break;
            case HorizontalPosition.ALIGN_LEFT:
                // Default position.
                break;
            case HorizontalPosition.RIGHT:
                x += anchor.getWidth();
                break;
        }
        PopupWindowCompat.showAsDropDown(this, anchor, x, y, Gravity.NO_GRAVITY);
    }

}