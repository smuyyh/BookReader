package com.justwayward.reader.bean.support;

/**
 * Created by lfh on 2016/8/16.
 */
public class FindBean {

    private String title;
    private int iconResId;


    public FindBean(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
