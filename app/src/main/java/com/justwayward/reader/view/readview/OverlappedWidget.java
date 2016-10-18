package com.justwayward.reader.view.readview;

import android.content.Context;
import android.view.MotionEvent;

import com.justwayward.reader.bean.BookToc;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/10/18.
 */
public class OverlappedWidget extends BaseReadView {

    private float actiondownX, actiondownY;

    public OverlappedWidget(Context context, String bookId,
                            List<BookToc.mixToc.Chapters> chaptersList,
                            OnReadStateChangeListener listener) {
        super(context, bookId, chaptersList, listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void jumpToChapter(int chapter) {

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void prePage() {

    }

    @Override
    public void setFontSize(int fontSizePx) {

    }

    @Override
    public void setTextColor(int textColor, int titleColor) {

    }

    @Override
    public void setTheme(int theme) {

    }

    @Override
    public void setBattery(int battery) {

    }

    @Override
    public void setTime(String time) {

    }
}
