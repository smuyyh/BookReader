package com.justwayward.reader.bean.support;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 2016/11/18.
 */
public class BookMark implements Serializable {

    public int chapter;

    public String title;

    public int startPos;

    public int endPos;

    public String desc = "";
}
