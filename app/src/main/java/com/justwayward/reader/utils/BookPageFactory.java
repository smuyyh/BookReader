package com.justwayward.reader.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;

import com.justwayward.reader.bean.ChapterRead;

import org.apache.commons.collections4.map.LRUMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author yuyh.
 * @date 16/8/7.
 */
public class BookPageFactory {

    private int mWidth;
    private int mHeight;
    private int mMarginWidth = 12; // 左右与边缘的距离
    private int mMarginHeight = 20; // 上下与边缘的距离
    private float mVisibleHeight; // 绘制内容的宽
    private float mVisibleWidth; // 绘制内容的宽

    private Paint mPaint;
    private int mFontSize = 16; //dp
    private float mFontSizePx;
    private int mTextColor = Color.LTGRAY;

    private int mLineCount = 0; // 每页可以显示的行数
    private int mLineWordCount = 0; // 每行可以显示的字数

    private String bookId;

    private static LRUMap<String, ArrayList<String>> cache = new LRUMap<>(10);

    public BookPageFactory(String bookId) {
        this.bookId = bookId;
    }

    public BookPageFactory(String bookId, int lineHeight) {
        this.bookId = bookId;
        mWidth = ScreenUtils.getScreenWidth();
        mHeight = ScreenUtils.getScreenHeight();

        mVisibleWidth = mWidth - ScreenUtils.dpToPx(mMarginWidth) * 2;
        mVisibleHeight = mHeight - ScreenUtils.dpToPx(mMarginHeight) * 2 - ScreenUtils.getStatusBarHeight(AppUtils.getAppContext());

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(mTextColor);

        mFontSizePx = ScreenUtils.dpToPx(mFontSize);
        mLineWordCount = (int) (mVisibleWidth / mFontSizePx);
        mLineCount = (int) (mVisibleHeight / lineHeight); // 可显示的行数

        LogUtils.e("mLineCount = " + mLineCount);
        LogUtils.e("mFontSizePx = " + mFontSizePx);
        LogUtils.e("mLineWordCount = " + mLineWordCount);
        LogUtils.e("mVisibleWidth = " + mVisibleWidth);
        LogUtils.e("mVisibleHeight = " + mVisibleHeight);
    }

    public File getBookFile(int chapter) {
        return FileUtils.getChapterFile(bookId, chapter);
    }

    /**
     * 章节数据 写到文件
     *
     * @param chapter
     * @param chapterNo
     */
    public void append(final ChapterRead.Chapter chapter, int chapterNo) {
        File file = getBookFile(chapterNo);
        FileUtils.writeFile(file.getAbsolutePath(), chapter.body, false);
    }

    /**
     * 判断是否缓存文章分页结果
     *
     * @param chapter 文章no
     * @return
     */
    public boolean hasCache(int chapter) {
        ArrayList<String> chapterCache = cache.get(bookId + "-" + chapter);
        return chapterCache != null && chapterCache.size() > 0;
    }

    /**
     * 读取文章并进行分页处理。增加线程锁，避免同时对一篇文章进行分页
     *
     * @param chapter
     * @return
     */
    public synchronized ArrayList<String> readPage(int chapter) {
        ArrayList<String> split = cache.get(bookId + "-" + chapter);
        if (split != null && split.size() > 0) {
            LogUtils.d(bookId + "-" + chapter + ": from cache");
            return split;
        }
        String temp = readTxt(chapter);
        try {
            split = split(temp, mLineWordCount * 2, "GBK");
            cache.put(bookId + "-" + chapter, split);
            LogUtils.d(bookId + "-" + chapter + ": add cache");
            return split;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文章的段落集合
     */
    public String readTxt(int chapter) {
        String temp = "";

        BufferedReader bufferedReader = null;
        File txtFile = getBookFile(chapter);
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO 编码错误
            LogUtils.e("UnsupportedEncodingException:" + e.toString());
            return null;
        } catch (FileNotFoundException e) {
            // TODO 文件不存在
            LogUtils.e("FileNotFoundException:" + e.toString());
            return null;
        }

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                temp += line + "\n";
            }
        } catch (IOException e) {
            LogUtils.e("IOException:" + e.toString());
            return null;
        }
        return temp;
    }

    /**
     * 分页处理
     *
     * @param text
     * @param length
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public ArrayList<String> split(String text, int length, String encoding) throws UnsupportedEncodingException {
        ArrayList<String> texts = new ArrayList();
        String temp = "    ";
        String c;
        int lines = 0;
        int pos = 2;
        int startInd = 0;
        for (int i = 0; text != null && i < text.length(); ) {
            byte[] b = String.valueOf(text.charAt(i)).getBytes(encoding);
            pos += b.length;
            if (pos >= length) {
                int endInd;
                if (pos == length) {
                    endInd = ++i;
                } else {
                    endInd = i;
                }
                temp += text.substring(startInd, endInd); // 加入一行
                lines++;
                if (lines >= mLineCount) { // 超出一页
                    texts.add(temp); // 加入
                    temp = "";
                    lines = 0;
                }
                pos = 0;
                startInd = i;
            } else {
                c = new String(b, encoding);
                if (c.equals("\n")) {
                    temp += text.substring(startInd, i + 1);
                    lines++;
                    if (lines >= mLineCount) {
                        texts.add(temp);
                        temp = "";
                        lines = 0;
                    }
                    temp += "    ";
                    pos = 2;
                    startInd = i + 1;
                }
                i++;
            }
        }
        if (startInd < text.length()) {
            temp += text.substring(startInd);
            lines++;
        }
        if (!TextUtils.isEmpty(temp))
            texts.add(temp);
        return texts;
    }
}
