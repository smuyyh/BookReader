package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class BooksByCats extends Base {
    /**
     * _id : 555abb2d91d0eb814e5db04f
     * title : 全职法师
     * author : 乱
     * shortIntro : 一觉醒来，世界大变。 熟悉的高中传授的是魔法，告诉大家要成为一名出色的魔法师。 居住的都市之外游荡着袭击人类的魔物妖兽，虎视眈眈。
     * 崇尚科学的世界变成了崇尚魔法...
     * cover : /agent/http://image.cmfu.com/books/3489766/3489766.jpg
     * site : zhuishuvip
     * majorCate : 玄幻
     * latelyFollower : 109257
     * latelyFollowerBase : 0
     * minRetentionRatio : 0
     * retentionRatio : 72.88
     * lastChapter : 第1173章 文泰之死
     * tags : ["腹黑","玄幻","异界大陆"]
     */

    public List<BooksBean> books;

    public static class BooksBean {
        public String _id;
        public String title;
        public String author;
        public String shortIntro;
        public String cover;
        public String site;
        public String majorCate;
        public int latelyFollower;
        public int latelyFollowerBase;
        public String minRetentionRatio;
        public String retentionRatio;
        public String lastChapter;
        public List<String> tags;

        public BooksBean(String _id, String cover, String title, String author, String majorCate, String shortIntro, int latelyFollower, String retentionRatio) {
            this._id = _id;
            this.cover = cover;
            this.title = title;
            this.author = author;
            this.majorCate = majorCate;
            this.shortIntro = shortIntro;
            this.latelyFollower = latelyFollower;
            this.retentionRatio = retentionRatio;
        }
    }
}
