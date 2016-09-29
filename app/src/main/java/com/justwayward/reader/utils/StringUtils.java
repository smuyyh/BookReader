package com.justwayward.reader.utils;

/**
 * Created by lfh on 2016/9/10.
 */
public class StringUtils {

    public static String creatAcacheKey(Object... param) {
        String key = "";
        for (Object o : param) {
            key += "-" + o;
        }
        return key.replaceFirst("-","");
    }

}
