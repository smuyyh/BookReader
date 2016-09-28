package com.justwayward.reader.manager;

import com.justwayward.reader.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/9/28.
 */
public class SPManager {

    private static SPManager manager;

    public static SPManager getInstance() {
        return manager == null ? (manager = new SPManager()) : manager;
    }

    public List<String> getSearchHistory() {
        return SharedPreferencesUtil.getInstance().getObject("searchHistory", List.class);
    }

    public void saveSearchHistory(Object obj) {
        SharedPreferencesUtil.getInstance().putObject("searchHistory", obj);
    }

}
