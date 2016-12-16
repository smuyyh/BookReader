package com.justwayward.reader.view.epubview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.justwayward.reader.ui.activity.ReadEPubActivity;
import com.justwayward.reader.ui.fragment.EPubReaderFragment;

/**
 * @author yuyh.
 * @date 2016/12/13.
 */
public class ObservableWebView extends WebView {

    private ReaderCallback mActivityCallback;
    private EPubReaderFragment fragment;

    private float MOVE_THRESHOLD_DP;
    private boolean mMoveOccured = false;

    private float mDownPosX;
    private float mDownPosY;

    private ScrollListener mScrollListener;
    private SizeChangedListener mSizeChangedListener;

    public interface ScrollListener {
        void onScrollChange(int percent);
    }

    public interface SizeChangedListener {
        void onSizeChanged(int height);
    }

    public ObservableWebView(Context context) {
        this(context, null);
    }

    public ObservableWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        MOVE_THRESHOLD_DP = 20 * getResources().getDisplayMetrics().density;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObservableWebView(Context context, AttributeSet attrs,
                             int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollListener(ScrollListener listener) {
        mScrollListener = listener;
    }

    public void setSizeChangedListener(SizeChangedListener listener) {
        mSizeChangedListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        mActivityCallback = (ReadEPubActivity) getContext();
        mActivityCallback.hideToolBarIfVisible();
        if (mScrollListener != null)
            mScrollListener.onScrollChange(t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        if (mSizeChangedListener != null) {
            mSizeChangedListener.onSizeChanged(h);
        }
    }

    public int getContentHeightVal() {
        int height = (int) Math.floor(this.getContentHeight() * this.getScale());
        return height;
    }

    public int getWebviewHeight() {
        return this.getMeasuredHeight();
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        return this.dummyActionMode();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        if (mActivityCallback == null)
            mActivityCallback = (ReadEPubActivity) getContext();

        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mMoveOccured = false;
                mDownPosX = event.getX();
                mDownPosY = event.getY();
                fragment.removeCallback();
                break;
            case MotionEvent.ACTION_UP:
                if (!mMoveOccured) {
                    mActivityCallback.toggleToolBarVisible();
                }

                fragment.startCallback();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX() - mDownPosX) > MOVE_THRESHOLD_DP
                        || Math.abs(event.getY() - mDownPosY) > MOVE_THRESHOLD_DP) {
                    mMoveOccured = true;
                    fragment.fadeInSeekbarIfInvisible();
                }
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.dummyActionMode();
    }

    public ActionMode dummyActionMode() {
        return new ActionMode() {
            @Override
            public void setTitle(CharSequence title) {
            }

            @Override
            public void setTitle(int resId) {
            }

            @Override
            public void setSubtitle(CharSequence subtitle) {
            }

            @Override
            public void setSubtitle(int resId) {
            }

            @Override
            public void setCustomView(View view) {
            }

            @Override
            public void invalidate() {
            }

            @Override
            public void finish() {
            }

            @Override
            public Menu getMenu() {
                return null;
            }

            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Override
            public CharSequence getSubtitle() {
                return null;
            }

            @Override
            public View getCustomView() {
                return null;
            }

            @Override
            public MenuInflater getMenuInflater() {
                return null;
            }
        };
    }

    public void setFragment(EPubReaderFragment fragment) {
        this.fragment = fragment;
    }
}