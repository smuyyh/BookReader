package com.justwayward.reader.utils;

import android.graphics.Color;
import android.graphics.Paint;

import com.justwayward.reader.bean.ChapterRead;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/7.
 */
public class BookPageFactory {

    private int mWidth;
    private int mHeight;
    private int mMarginWidth = 15; // 左右与边缘的距离
    private int mMarginHeight = 20; // 上下与边缘的距离
    private float mVisibleHeight; // 绘制内容的宽
    private float mVisibleWidth; // 绘制内容的宽

    private Paint mPaint;
    private int mFontSize = 20;
    private int mTextColor = Color.LTGRAY;

    private List<String> mList = new ArrayList<>();

    private int mLineCount; // 每页可以显示的行数
    private int currentPage = 1;
    private int currentChapter = 1;

    private String bookId;
    private File bookFile;

    private String basePath = FileUtils.createRootPath(AppUtils.getAppContext()) + "/book/";

    public BookPageFactory(String bookId) {
        this.bookId = bookId;
        mWidth = ScreenUtils.getScreenWidth();
        mHeight = ScreenUtils.getScreenHeight();

        mVisibleWidth = mWidth - mMarginWidth;
        mVisibleHeight = mHeight - mMarginHeight;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(mTextColor);

        mLineCount = (int) (mVisibleHeight / mFontSize); // 可显示的行数

        bookFile = new File(bookId + ".txt");
        if (!bookFile.exists())
            FileUtils.createFile(bookFile);

    }

    public void append(List<ChapterRead.Chapter> chapters) {
        if (chapters != null && !chapters.isEmpty()) {

            for (ChapterRead.Chapter chapter : chapters) {
                File file = new File(basePath + bookId + "/" + currentChapter + ".txt");
                FileUtils.writeFile(file.getAbsolutePath(), chapter.body, true);
            }

        }
    }

    public void append(final ChapterRead.Chapter chapter) {
        append(new ArrayList<ChapterRead.Chapter>() {{
            add(chapter);
        }});
    }

    public void readNext() {
        String chapter = mList.get(currentChapter);
    }


}
