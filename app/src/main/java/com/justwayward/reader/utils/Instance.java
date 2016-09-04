package com.justwayward.reader.utils;

/**
 * @author yuyh.
 * @date 16/9/4.
 */
public class Instance {

    public static <T> T createInstance(Class<T> cls) {
        T obj = null;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }
}
