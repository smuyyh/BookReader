package com.justwayward.reader.manager;

import com.justwayward.reader.base.Constant;
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

    /**
     * 保存字体大小
     *
     * @param bookId     需根据bookId对应，避免由于字体大小引起的分页不准确
     * @param fontSizePx
     * @return
     */
    public void saveFontSize(String bookId, int fontSizePx) {
        SharedPreferencesUtil.getInstance().putInt(getFontSizeKey(bookId), fontSizePx);
    }

    public int getReadFontSize(String bookId) {
        return SharedPreferencesUtil.getInstance().getInt(getFontSizeKey(bookId), ScreenUtils.dpToPxInt(16));
    }

    private String getFontSizeKey(String bookId) {
        return bookId + "-readFontSize";
    }

    public int getReadBrightness() {
        return SharedPreferencesUtil.getInstance().getInt(getLightnessKey(),
                (int) ScreenUtils.getScreenBrightness(AppUtils.getAppContext()));
    }

    /**
     * 保存阅读界面屏幕亮度
     *
     * @param percent 亮度比例 0~100
     */
    public void saveReadBrightness(int percent) {
        SharedPreferencesUtil.getInstance().putInt(getLightnessKey(), percent);
    }

    private String getLightnessKey() {
        return "readLightness";
    }

    public synchronized void saveReadProgress(String bookId, int currentChapter, int m_mbBufBeginPos, int m_mbBufEndPos) {
        SharedPreferencesUtil.getInstance()
                .putInt(getChapterKey(bookId), currentChapter)
                .putInt(getStartPosKey(bookId), m_mbBufBeginPos)
                .putInt(getEndPosKey(bookId), m_mbBufEndPos);
    }

    /**
     * 获取上次阅读章节及位置
     *
     * @param bookId
     * @return
     */
    public int[] getReadProgress(String bookId) {
        int lastChapter = SharedPreferencesUtil.getInstance().getInt(getChapterKey(bookId), 1);
        int startPos = SharedPreferencesUtil.getInstance().getInt(getStartPosKey(bookId), 0);
        int endPos = SharedPreferencesUtil.getInstance().getInt(getEndPosKey(bookId), 0);

        return new int[]{lastChapter, startPos, endPos};
    }

    public void removeReadProgress(String bookId) {
        SharedPreferencesUtil.getInstance()
                .remove(getChapterKey(bookId))
                .remove(getStartPosKey(bookId))
                .remove(getEndPosKey(bookId));
    }

    private String getChapterKey(String bookId) {
        return bookId + "-chapter";
    }

    private String getStartPosKey(String bookId) {
        return bookId + "-startPos";
    }

    private String getEndPosKey(String bookId) {
        return bookId + "-endPos";
    }

    public void saveReadTheme(int theme) {
        SharedPreferencesUtil.getInstance().putInt("readTheme", theme);
    }

    public int getReadTheme() {
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            return ThemeManager.NIGHT;
        }
        return SharedPreferencesUtil.getInstance().getInt("readTheme", 3);
    }

    /**
     * 是否可以使用音量键翻页
     *
     * @param enable
     */
    public void saveVolumeFlipEnable(boolean enable) {
        SharedPreferencesUtil.getInstance().putBoolean("volumeFlip", enable);
    }

    public boolean isVolumeFlipEnable() {
        return SharedPreferencesUtil.getInstance().getBoolean("volumeFlip", true);
    }

    public void saveAutoBrightness(boolean enable) {
        SharedPreferencesUtil.getInstance().putBoolean("autoBrightness", enable);
    }

    public boolean isAutoBrightness() {
        return SharedPreferencesUtil.getInstance().getBoolean("autoBrightness", false);
    }
}
