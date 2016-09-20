package com.justwayward.reader.view.ReadView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.justwayward.reader.utils.ScreenUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.Vector;

public class PageFactory {
    /**
     * 屏幕宽高
     */
    private int mHeight, mWidth;
    /**
     * 文字区域宽高
     */
    private int mVisibleHeight, mVisibleWidth;
    /**
     * 间距
     */
    private int marginHeight, marginWidth;
    /**
     * 字体大小
     */
    private int mFontSize, mNumFontSize;

    /**
     * 每页行数
     */
    private int mPageLineCount;
    /**
     * 行间距
     **/
    private int mLineSpace = 2;
    /**
     * 字节长度
     */
    private int m_mpBufferLen;
    /**
     * MappedByteBuffer：高效的文件内存映射
     */
    private MappedByteBuffer m_mpBuff;
    // 页首页尾的位置
    private int m_mbBufEndPos = 0;
    private int m_mbBufBeginPos = 0;
    private Paint mNumPaint;
    private Paint mPaint;

    private Bitmap m_book_bg;
    private Vector<String> m_lines = new Vector<>();

    public PageFactory(Context context) {
        this(context, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), ScreenUtils.dpToPxInt(16));
    }

    public PageFactory(Context context, int width, int height, int fontSize) {
        mWidth = width;
        mHeight = height;
        mFontSize = fontSize;
        mNumFontSize = fontSize / 2;
        marginWidth = ScreenUtils.dpToPxInt(15);
        marginHeight = ScreenUtils.dpToPxInt(15);
        mVisibleHeight = mHeight - marginHeight * 2 - mFontSize;
        mVisibleWidth = mWidth - marginWidth * 2;
        mPageLineCount = mVisibleHeight / (mFontSize + 2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(Color.BLACK);
        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setTextSize(mNumFontSize);
        mNumPaint.setColor(Color.GRAY);
        // Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/FZBYSK.TTF");
        // mPaint.setTypeface(typeface);
        // mNumPaint.setTypeface(typeface);
    }

    public void openBook(String path) {
        openBook(path, new int[]{0, 0});
    }

    /**
     * 打开书籍文件
     *
     * @param path
     * @param position 阅读位置
     */
    public void openBook(String path, int[] position) {
        try {
            File file = new File(path);
            long length = file.length();
            m_mpBufferLen = (int) length;
            // 创建文件通道，映射为MappedByteBuffer
            m_mpBuff = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, length);
            m_mbBufEndPos = position[1];
            m_mbBufBeginPos = position[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制阅读页面
     *
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        if (m_lines.size() == 0) {
            m_mbBufEndPos = m_mbBufBeginPos;
            m_lines = pageDown();
        }
        if (m_lines.size() > 0) {
            int y = marginHeight;
            // 绘制背景
            if (m_book_bg != null) {
                Rect rectF = new Rect(0, 0, mWidth, mHeight);
                canvas.drawBitmap(m_book_bg, null, rectF, null);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            // 绘制阅读页面文字
            for (String line : m_lines) {
                y += mFontSize + mLineSpace;
                canvas.drawText(line, marginWidth, y, mPaint);
            }
            // 绘制提示内容
            float percent = (float) m_mbBufBeginPos * 100 / m_mpBufferLen;
            DecimalFormat sirPercent = new DecimalFormat("#0.00");
            canvas.drawText(sirPercent.format(percent) + "%", marginWidth + 2, mHeight - marginHeight, mNumPaint);
            GregorianCalendar calendar = new GregorianCalendar();
            String mTime = calendar.HOUR_OF_DAY + "时" + calendar.MINUTE + "分 ";
            int strLen = (int) mNumPaint.measureText(mTime);
            canvas.drawText(mTime, mWidth - marginWidth - strLen, mHeight - marginHeight, mNumPaint);
        }
    }

    /**
     * 指针移到上一页页首
     */
    private void pageUp() {
        String strParagraph = "";
        Vector<String> lines = new Vector<>(); // 页面行
        while ((lines.size() < mPageLineCount) && (m_mbBufBeginPos > 0)) {
            Vector<String> paraLines = new Vector<>(); // 段落行
            byte[] parabuffer = readParagraphBack(m_mbBufBeginPos); // 1.读取上一个段落

            m_mbBufBeginPos -= parabuffer.length; // 2.变换起始位置指针
            try {
                strParagraph = new String(parabuffer, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            strParagraph = strParagraph.replaceAll("\r\n", "  ");
            strParagraph = strParagraph.replaceAll("\n", " ");

            while (strParagraph.length() > 0) { // 3.逐行添加到lines
                int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
                paraLines.add(strParagraph.substring(0, paintSize));
                strParagraph = strParagraph.substring(paintSize);
            }
            lines.addAll(0, paraLines);

            while (lines.size() > mPageLineCount) { // 4.如果段落添加完，但是超出一页，则超出部分需删减
                try {
                    m_mbBufBeginPos += lines.get(0).getBytes("UTF-8").length; // 5.删减行数同时起始位置指针也要跟着偏移
                    lines.remove(0);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            m_mbBufEndPos = m_mbBufBeginPos; // 6.最后结束指针指向下一页的开始处
        }
    }

    /**
     * 根据起始位置指针，读取一页内容
     *
     * @return
     */
    private Vector<String> pageDown() {
        String strParagraph = "";
        Vector<String> lines = new Vector<String>();
        while ((lines.size() < mPageLineCount) && (m_mbBufEndPos < m_mpBufferLen)) {
            byte[] parabuffer = readParagraphForward(m_mbBufEndPos);
            m_mbBufEndPos += parabuffer.length;
            try {
                strParagraph = new String(parabuffer, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            strParagraph = strParagraph.replaceAll("\r\n", "  ");
            strParagraph = strParagraph.replaceAll("\n", " "); // 段落中的换行符去掉，绘制的时候再换行

            while (strParagraph.length() > 0) {
                int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
                lines.add(strParagraph.substring(0, paintSize));
                strParagraph = strParagraph.substring(paintSize);
                if (lines.size() >= mPageLineCount) {
                    break;
                }
            }
            if (strParagraph.length() != 0) {
                try {
                    m_mbBufEndPos -= (strParagraph).getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return lines;
    }

    /**
     * 读取下一段落
     *
     * @param m_mbBufEndPos 当前页结束位置指针
     * @return
     */
    private byte[] readParagraphForward(int m_mbBufEndPos) {
        byte b0;
        int i = m_mbBufEndPos;
        while (i < m_mpBufferLen) {
            b0 = m_mpBuff.get(i++);
            if (b0 == 0x0a) {
                break;
            }
        }
        int nParaSize = i - m_mbBufEndPos;
        byte[] buf = new byte[nParaSize];
        for (i = 0; i < nParaSize; i++) {
            buf[i] = m_mpBuff.get(m_mbBufEndPos + i);
        }
        return buf;
    }

    /**
     * 读取上一段落
     *
     * @param m_mbBufBeginPos 当前页起始位置指针
     * @return
     */
    private byte[] readParagraphBack(int m_mbBufBeginPos) {
        byte b0;
        int i = m_mbBufBeginPos - 1;
        while (i > 0) {
            b0 = m_mpBuff.get(i);
            if (b0 == 0x0a && i != m_mbBufBeginPos - 1) {
                i++;
                break;
            }
            i--;
        }
        int nParaSize = m_mbBufBeginPos - i;
        byte[] buf = new byte[nParaSize];
        for (int j = 0; j < nParaSize; j++) {
            buf[j] = m_mpBuff.get(i + j);
        }
        return buf;
    }

    /**
     * 跳转下一页
     */
    public void nextPage() {
        if (m_mbBufEndPos >= m_mpBufferLen) {
            return;
        } else {
            m_lines.clear();
            m_mbBufBeginPos = m_mbBufEndPos; // 起始指针移到结束位置
            m_lines = pageDown(); // 读取一页内容
        }
    }

    /**
     * 跳转上一页
     */
    public void prePage() {
        if (m_mbBufBeginPos <= 0) {
            return;
        }
        m_lines.clear();
        pageUp(); // 起始指针移到上一页开始处
        m_lines = pageDown(); // 读取一页内容
    }

    /**
     * 获取当前阅读位置
     *
     * @return index 0：起始位置 1：结束位置
     */
    public int[] getPosition() {
        return new int[]{m_mbBufBeginPos, m_mbBufEndPos};
    }

    /**
     * 设置字体大小
     *
     * @param fontsize 单位：px
     */
    public void setTextFont(int fontsize) {
        mFontSize = fontsize;
        mPaint.setTextSize(mFontSize);
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace);
        m_mbBufEndPos = m_mbBufBeginPos;
        nextPage();
    }

    public int getTextFont() {
        return mFontSize;
    }

    /**
     * 根据百分比，跳到目标位置
     *
     * @param persent
     */
    public void setPercent(int persent) {
        float a = (float) (m_mpBufferLen * persent) / 100;
        m_mbBufEndPos = (int) a;
        if (m_mbBufEndPos == 0) {
            nextPage();
        } else {
            nextPage();
            prePage();
            nextPage();
        }
    }

    public void setBgBitmap(Bitmap BG) {
        m_book_bg = BG;
    }
}
