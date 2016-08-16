package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * Created by lfh on 2016/8/15.
 */
public class CategoryList extends Base{


    /**
     * male : [{"name":"玄幻","bookCount":188244},{"name":"奇幻","bookCount":24183}]
     * ok : true
     */

    private List<MaleBean> male;
    /**
     * name : 古代言情
     * bookCount : 125103
     */

    private List<MaleBean> female;

    public static class MaleBean {
        private String name;
        private int bookCount;
    }
}
