package com.justwayward.reader.utils;

/**
 * Created by lfh on 2016/9/10.
 */
public class StringUtils {

    public static String creatAcacheKey(String tag, String sort,  String type,  String distillate,  int start,  int limit){
        String key = tag+"-"+sort+"-"+type+"-"+distillate+"-"+start+"-"+limit;
        return key;
    }

}
