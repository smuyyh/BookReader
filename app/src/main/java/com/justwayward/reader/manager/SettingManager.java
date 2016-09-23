package com.justwayward.reader.manager;

import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.ScreenUtils;
import com.justwayward.reader.utils.SharedPreferencesUtil;

/**
 * @author yuyh.
 * @date 2016/9/23.
 */
public class SettingManager {

    private volatile static SettingManager manager;

    public static SettingManager getInstance() {
        return manager != null ? manager : (manager = new SettingManager());
    }

    public int getReadFontSize() {
        return SharedPreferencesUtil.getInstance().getInt("readFontSize", ScreenUtils.dpToPxInt(16));
    }

    public void saveFontSize(int fontSizePx) {
        SharedPreferencesUtil.getInstance().putInt("readFontSize", fontSizePx);
    }

    public int getReadBrightness() {
        return SharedPreferencesUtil.getInstance().getInt("readLightness",
                (int) ScreenUtils.getScreenBrightness(AppUtils.getAppContext()));
    }

    /**
     * 保存阅读界面屏幕亮度
     *
     * @param percent 亮度比例 0~100
     */
    public void saveReadBrightness(int percent) {
        SharedPreferencesUtil.getInstance().putInt("readLightness", percent);
    }

    public synchronized void saveReadProgress(String bookId, int currentChapter, int m_mbBufBeginPos, int m_mbBufEndPos) {
        SharedPreferencesUtil.getInstance().putInt(bookId + "-chapter", currentChapter);
        SharedPreferencesUtil.getInstance().putInt(bookId + "-startPos", m_mbBufBeginPos);
        SharedPreferencesUtil.getInstance().putInt(bookId + "-endPos", m_mbBufEndPos);
    }

    /**
     * 获取上次阅读章节及位置
     *
     * @param bookId
     * @return
     */
    public int[] getReadProgress(String bookId) {
        int lastChapter = SharedPreferencesUtil.getInstance().getInt(bookId + "-chapter", 1);
        int startPos = SharedPreferencesUtil.getInstance().getInt(bookId + "-startPos", 0);
        int endPos = SharedPreferencesUtil.getInstance().getInt(bookId + "-endPos", 0);

        return new int[]{lastChapter, startPos, endPos};
    }

    public void saveReadTheme(int theme) {
        SharedPreferencesUtil.getInstance().putInt("readTheme", theme);
    }

    public int getReadTheme() {
        return SharedPreferencesUtil.getInstance().getInt("readTheme", 0);
    }
}
