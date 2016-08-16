package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * Created by lfh on 2016/8/15.
 */
public class BookListDetail extends Base{

    /**
     * _id : 57a81bb323eda7b201353972
     * updated : 2016-08-08T05:42:11.266Z
     * title : 青春不老，我们不散
     * author : {"_id":"550bf71a4d4494ca5843fdf1",
     * "avatar":"/avatar/1f/d5/1fd5c729f873fc31d708fd08e854a631","nickname":"鹿晓","type":"normal",
     * "lv":7}
     * desc : 那些曾经让我们潸然泪下的书
     * gender : female
     * created : 2016-08-08T05:42:11.258Z
     * tags : []
     * stickStopTime : null
     * isDraft : false
     * isDistillate : null
     * collectorCount : 2500
     * books : [{"book":{"_id":"562ac4ec638d5091155758e2","title":"深海里的星星1","author":"独木舟",
     * "longIntro":"女孩程落薰与周慕晨相恋，第一次勇敢的爱上一个人，.....","cover":"/agent/http://wfqqreader.3g.qq
     * .com/cover/534/444534/t7_444534.jpg","cat":"都市言情","site":"chuangshi","banned":0,
     * "latelyFollower":1435,"latelyFollowerBase":0,"wordCount":150000,"minRetentionRatio":0,
     * "retentionRatio":14.9},"comment":"。"}],"shareLink":"http://share.zhuishushenqi.com/booklist/57a81bb323eda7b201353972","id":"57a81bb323eda7b201353972"}
     * ok : true
     */

    public static class BookListBean {
        public String _id;
        public String updated;
        public String title;
        /**
         * _id : 550bf71a4d4494ca5843fdf1
         * avatar : /avatar/1f/d5/1fd5c729f873fc31d708fd08e854a631
         * nickname : 鹿晓
         * type : normal
         * lv : 7
         */

        public AuthorBean author;
        public String desc;
        public String gender;
        public String created;
        public Object stickStopTime;
        public boolean isDraft;
        public Object isDistillate;
        public int collectorCount;
        public String shareLink;
        public String id;
        public List<?> tags;
        /**
         * book : {"_id":"562ac4ec638d5091155758e2","title":"深海里的星星1","author":"独木舟",
         * "longIntro":"女孩程落薰与周慕晨相恋，第一次勇敢的爱上一个人，....","cover":"/agent/http://wfqqreader
         * .3g.qq.com/cover/534/444534/t7_444534.jpg","cat":"都市言情","site":"chuangshi","banned":0,
         * "latelyFollower":1435,"latelyFollowerBase":0,"wordCount":150000,"minRetentionRatio":0,
         * "retentionRatio":14.9}
         * comment : 。
         */

        public List<BooksBean> books;

        public static class AuthorBean {
            public String _id;
            public String avatar;
            public String nickname;
            public String type;
            public int lv;
        }

        public static class BooksBean {
            /**
             * _id : 562ac4ec638d5091155758e2
             * title : 深海里的星星1
             * author : 独木舟
             * longIntro : 女孩程落薰与周慕晨相恋，第一次勇敢的爱上一个人，也第一次遭遇了人生的背叛。
             * 周暮晨因为别的女孩放弃了她，而学校彻查的“粉笔灰”事件，让程落薰在失恋的同时遭受了双重打击。但好在伤筋痛骨的17岁，还有麻辣好友康婕为伴。
             * 她们就像倔强的野草，在这座城市迎风生长，遇到了欢笑，也遇到了眼泪；遇到了生死不离的挚友，也遇到了分崩离析的背叛；遇到了刻骨铭心的爱情，也遇到了锥心裂肺的离别……
             * 曾经的誓言与陪伴，后来一一消散在风里。 曾经满载美好的城，后来只剩下谁的孑然一身。 有一年，我在这座城市里同时失去了你与自己，从此人生只剩下夜晚，没有一颗星。
             * 时光如数剥落，我在末路孤独仰望，你却在来路不慎迷失……
             * cover : /agent/http://wfqqreader.3g.qq.com/cover/534/444534/t7_444534.jpg
             * cat : 都市言情
             * site : chuangshi
             * banned : 0
             * latelyFollower : 1435
             * latelyFollowerBase : 0
             * wordCount : 150000
             * minRetentionRatio : 0
             * retentionRatio : 14.9
             */

            public BookBean book;
            public String comment;

            public static class BookBean {
                public String _id;
                public String title;
                public String author;
                public String longIntro;
                public String cover;
                public String cat;
                public String site;
                public int banned;
                public int latelyFollower;
                public int latelyFollowerBase;
                public int wordCount;
                public int minRetentionRatio;
                public double retentionRatio;
            }
        }
    }
}
