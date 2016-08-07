package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BookToc extends Base {

    /**
     * _id:577e528e2160421a02d7380d
     * name:优质书源
     * link:http://vip.zhuishushenqi.com/toc/577e528e2160421a02d7380d
     */

    public String _id;
    public String name;
    public String link;
    /**
     * title : 第一章 死在万花丛中
     * link : http://vip.zhuishushenqi.com/chapter/577e5290260289ff64a29213?cv=1467896464908
     * id : 577e5290260289ff64a29213
     * currency : 15
     * unreadble : false
     * isVip : false
     */

    public List<Chapters> chapters;

    public static class Chapters {
        public String title;
        public String link;
        public String id;
        public int currency;
        public boolean unreadble;
        public boolean isVip;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }

        public boolean isUnreadble() {
            return unreadble;
        }

        public void setUnreadble(boolean unreadble) {
            this.unreadble = unreadble;
        }

        public void setVip(boolean vip) {
            isVip = vip;
        }

        public boolean isVip() {
            return isVip;
        }
    }

}
