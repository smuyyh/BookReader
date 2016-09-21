package com.justwayward.reader.view.ReadView;

/**
 * @author yuyh.
 * @date 2016/9/21.
 */
public interface OnReadStateChangeListener {

    void onChapterChanged(int chapter);

    void onPageChanged(int chapter, int page);

    void onLoadChapterFailure(int chapter);

    void onCenterClick();
}
