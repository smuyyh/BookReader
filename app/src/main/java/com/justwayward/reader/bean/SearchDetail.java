package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/4.
 */
public class SearchDetail extends Base {


    /**
     * _id : 55e2850dbda85d9f74e6f73b
     * hasCp : false
     * title : w
     * cat : 奇幻
     * author : w
     * site : faloo
     * cover : /agent/http://img.faloo.com/Novel/166x235/0/71/000071091.jpg
     * shortIntro : 最终之海的传说，可怕的烧烧能力，危险的黑暗能力，还有恐怖的冰冰能力。  看小Down怎样找到去另一个世界的大门。本书绝对会全本，更新时间固定每周六晚 绝对大更。...
     * lastChapter : 抢劫海贼
     * retentionRatio : null
     * latelyFollower : 4
     * wordCount : 5075
     */

    public List<SearchBooks> books;

    public static class SearchBooks {
        public String _id;
        public boolean hasCp;
        public String title;
        public String cat;
        public String author;
        public String site;
        public String cover;
        public String shortIntro;
        public String lastChapter;
        public String retentionRatio;
        public int latelyFollower;
        public int wordCount;
    }
}
