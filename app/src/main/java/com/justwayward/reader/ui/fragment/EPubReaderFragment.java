package com.justwayward.reader.ui.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseFragment;
import com.justwayward.reader.component.AppComponent;
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
        mPosition = getArguments().getInt(BUNDLE_POSITION);
        mBook = (Book) getArguments().getSerializable(BUNDLE_BOOK);
        mEpubFileName = getArguments().getString(BUNDLE_EPUB_FILE_NAME);
        mIsSmilAvailable = getArguments().getBoolean(BUNDLE_IS_SMIL_AVAILABLE);
    }

    @Override
    public void configViews() {
        initSeekbar();

        initWebView();
    }

    private void initSeekbar() {
        mScrollSeekbar.getProgressDrawable()
                .setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent),
                        PorterDuff.Mode.SRC_IN);
    }

    private void initWebView() {

        mWebview.getViewTreeObserver().
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height =
                                (int) Math.floor(mWebview.getContentHeight() * mWebview.getScale());
                        int webViewHeight = mWebview.getMeasuredHeight();
                        mScrollSeekbar.setMaximum(height - webViewHeight);
                    }
                });

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
                mScrollSeekbar.setProgressAndThumb(percent);
            }
        });

        mWebview.getSettings().setDefaultTextEncodingName("utf-8");
    }
}
