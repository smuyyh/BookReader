package com.justwayward.reader.ui.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.ui.activity.ReadEPubActivity;
import com.justwayward.reader.view.epubview.ObservableWebView;
import com.justwayward.reader.view.epubview.VerticalSeekbar;

import butterknife.Bind;
import nl.siegmann.epublib.domain.Book;

/**
 * @author yuyh.
 * @date 2016/12/14.
 */
public class EPubReaderFragment extends BaseFragment {

    private static final String BUNDLE_POSITION = "position";
    private static final String BUNDLE_BOOK = "book";
    private static final String BUNDLE_EPUB_FILE_NAME = "filename";
    private static final String BUNDLE_IS_SMIL_AVAILABLE = "smilavailable";

    @Bind(R.id.scrollSeekbar)
    VerticalSeekbar mScrollSeekbar;
    @Bind(R.id.contentWebView)
    ObservableWebView mWebview;

    private int mPosition = -1;
    private Book mBook = null;
    private String mEpubFileName = null;
    private boolean mIsSmilAvailable;

    private int mScrollY;

    private ReadEPubActivity activity;

    private Animation mFadeInAnimation, mFadeOutAnimation;
    private Handler mHandler = new Handler();

    public static Fragment newInstance(int position, Book book, String epubFileName, boolean isSmilAvailable) {
        EPubReaderFragment fragment = new EPubReaderFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_POSITION, position);
        args.putSerializable(BUNDLE_BOOK, book);
        args.putString(BUNDLE_EPUB_FILE_NAME, epubFileName);
        args.putSerializable(BUNDLE_IS_SMIL_AVAILABLE, isSmilAvailable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if ((state != null) && state.containsKey(BUNDLE_POSITION)
                && state.containsKey(BUNDLE_BOOK)) {
            mPosition = state.getInt(BUNDLE_POSITION);
            mBook = (Book) state.getSerializable(BUNDLE_BOOK);
            mEpubFileName = state.getString(BUNDLE_EPUB_FILE_NAME);
            mIsSmilAvailable = state.getBoolean(BUNDLE_IS_SMIL_AVAILABLE);
        }
        return super.onCreateView(inflater, container, state);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_epub_reader;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        activity = (ReadEPubActivity) getActivity();

        mPosition = getArguments().getInt(BUNDLE_POSITION);
        mBook = (Book) getArguments().getSerializable(BUNDLE_BOOK);
        mEpubFileName = getArguments().getString(BUNDLE_EPUB_FILE_NAME);
        mIsSmilAvailable = getArguments().getBoolean(BUNDLE_IS_SMIL_AVAILABLE);
    }

    @Override
    public void configViews() {
        initAnimations();

        initSeekbar();

        initWebView();
    }

    private void initSeekbar() {

        mScrollSeekbar.setFragment(this);
        if (mScrollSeekbar.getProgressDrawable() != null)
            mScrollSeekbar.getProgressDrawable()
                    .setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent),
                            PorterDuff.Mode.SRC_IN);

        mScrollSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, boolean fromUser) {
                if (fromUser) {
                    mWebview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWebview.setScrollY((int) (mWebview.getContentHeight() * mWebview.getScale() * progress / seekBar.getMax()));
                        }
                    }, 200);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initWebView() {

        mWebview.setFragment(this);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setVerticalScrollBarEnabled(false);
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.setHorizontalScrollBarEnabled(false);
        mWebview.addJavascriptInterface(this, "Highlight");

        mWebview.setScrollListener(new ObservableWebView.ScrollListener() {
            @Override
            public void onScrollChange(int percent) {
                if (mWebview.getScrollY() != 0) {
                    mScrollY = mWebview.getScrollY();
                }

                int height = (int) Math.floor(mWebview.getContentHeight() * mWebview.getScale());
                int webViewHeight = mWebview.getMeasuredHeight();
                mScrollSeekbar.setMax(height - webViewHeight);

                mScrollSeekbar.setProgress(percent);
            }
        });

        mWebview.getSettings().setDefaultTextEncodingName("utf-8");

        String herf = activity.getPageHref(mPosition);

        mWebview.loadUrl("file://" + herf);
    }

    private void initAnimations() {
        mFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        mFadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mScrollSeekbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        mFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mScrollSeekbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private Runnable mHideSeekbarRunnable = new Runnable() {
        @Override
        public void run() {
            fadeoutSeekbarIfVisible();
        }
    };

    public void fadeInSeekbarIfInvisible() {
        if (mScrollSeekbar != null && !isVisible(mScrollSeekbar)) {
            mScrollSeekbar.startAnimation(mFadeInAnimation);
        }
    }

    public void fadeoutSeekbarIfVisible() {
        if (mScrollSeekbar != null && isVisible(mScrollSeekbar)) {
            mScrollSeekbar.startAnimation(mFadeOutAnimation);
        }
    }

    public void removeCallback() {
        mHandler.removeCallbacks(mHideSeekbarRunnable);
    }

    public void startCallback() {
        mHandler.postDelayed(mHideSeekbarRunnable, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
