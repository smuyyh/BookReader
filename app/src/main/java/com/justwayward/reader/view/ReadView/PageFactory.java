package com.justwayward.reader.view.ReadView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Vector;

public class PageFactory {
    private int mHeight, mWidth;
    private int mVisibleHeight, mVisibleWidth;

    // 每页行数
    private int mPageLineCount;
    // 行间距
    private int mLineSpace = 2;
    // 字节长
    private int m_mpBufferLen;
    private MappedByteBuffer m_mpBuff;
    // 页首页尾的行数
    private int m_mbBufEndPos = 0;
    private int m_mbBufBeginPos = 0;
    private Paint mNumPaint;
    private Paint mPaint;
    private int marginHeight = 30;
    private int marginWeight = 30;
    // 字号
    private int mFontSize = 30;
    private int mNumFontSize = 45;

    private Bitmap m_book_bg;
    private Vector<String> m_lines = new Vector<String>();

    public PageFactory(Context context, int w, int h, int font_size) {
        mWidth = w;
        mHeight = h;
        mFontSize = font_size;
        mVisibleHeight = mHeight - marginHeight * 2 - mFontSize;
        mVisibleWidth = mWidth - marginWeight * 2;
        //Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/FZBYSK.TTF");
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mFontSize);
        //	mPaint.setTypeface(typeface);
        mPaint.setColor(Color.BLACK);
        //每页行数
        mPageLineCount = mVisibleHeight / (mFontSize + 2);
        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setTextSize(mNumFontSize);
        //mNumPaint.setTypeface(typeface);
        mNumPaint.setColor(Color.GRAY);
    }

    public void openBook(String path, int[] position) {
        try {
            File file = new File(path);
            long length = file.length();
            m_mpBufferLen = (int) length;
            // 创建文件通道
            m_mpBuff = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, length);
            m_mbBufEndPos = position[1];
            m_mbBufBeginPos = position[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDraw(Canvas canvas) {
        if (m_lines.size() == 0) {
            m_mbBufEndPos = m_mbBufBeginPos;
            m_lines = pageDown();
        }
        if (m_lines.size() > 0) {
            int y = marginHeight;
            if (m_book_bg != null) {
                Rect rectF = new Rect(0, 0, mWidth, mHeight);
                canvas.drawBitmap(m_book_bg, null, rectF, null);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            for (String line : m_lines) {
                // y是每行的位置
                y += mFontSize + mLineSpace;
                canvas.drawText(line, marginWeight, y, mPaint);
            }
            float percent = (float) m_mbBufBeginPos * 100 / m_mpBufferLen;
            DecimalFormat sirPercent = new DecimalFormat("#0.00");
            canvas.drawText(sirPercent.format(percent) + "%", marginWeight + 2, mHeight - marginHeight, mNumPaint);
            Time time = new Time();
            time.setToNow();
            String mTime = time.hour + "时" + time.minute + "分 ";
            int strLen = (int) mNumPaint.measureText(mTime);
            canvas.drawText(mTime, mWidth - marginWeight - strLen, mHeight - marginHeight, mNumPaint);
        }
    }

    private void pageUp() {
        String strParagraph = "";
        Vector<String> lines = new Vector<>();
        while ((lines.size() < mPageLineCount) && (m_mbBufBeginPos > 0)) {
            Vector<String> paraLines = new Vector<String>();

            byte[] parabuffer = readParagraphBack(m_mbBufBeginPos);

            m_mbBufBeginPos -= parabuffer.length;
            try {
                strParagraph = new String(parabuffer, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            strParagraph = strParagraph.replaceAll("\r\n", "  ");
            strParagraph = strParagraph.replaceAll("\n", " ");

            while (strParagraph.length() > 0) {
                int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
                paraLines.add(strParagraph.substring(0, paintSize));
                strParagraph = strParagraph.substring(paintSize);
            }
            lines.addAll(0, paraLines);

            while (lines.size() > mPageLineCount) {
                try {
                    m_mbBufBeginPos += lines.get(0).getBytes("UTF-8").length;
                    lines.remove(0);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            m_mbBufEndPos = m_mbBufBeginPos;
        }
    }

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
            strParagraph = strParagraph.replaceAll("\n", " ");

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

    private byte[] readParagraphForward(int m_mbBufPos) {
        byte b0, b1;
        int i = m_mbBufPos;
        while (i < m_mpBufferLen) {
            b0 = m_mpBuff.get(i++);
            if (b0 == 0x0a) {
                break;
            }
        }
        int nParaSize = i - m_mbBufPos;
        byte[] buf = new byte[nParaSize];
        for (i = 0; i < nParaSize; i++) {
            buf[i] = m_mpBuff.get(m_mbBufPos + i);
        }
        return buf;
    }

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

    public void nextPage() {
        if (m_mbBufEndPos >= m_mpBufferLen) {
            return;
        } else {
            m_lines.clear();
            m_mbBufBeginPos = m_mbBufEndPos;
            m_lines = pageDown();
        }
    }

    public void prePage() {
        if (m_mbBufBeginPos <= 0) {
            return;
        }
        m_lines.clear();
        pageUp();
        m_lines = pageDown();
    }

    public int[] getPosition() {
        int[] a = new int[]{m_mbBufBeginPos, m_mbBufEndPos};
        return a;
    }

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
