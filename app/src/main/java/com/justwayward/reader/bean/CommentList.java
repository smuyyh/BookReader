package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * Created by lfh on 2016/9/1.
 * 评论列表
 */
public class CommentList extends Base {


    /**
     * _id : 57bf0be8c2099ef01d9d35d6
     * content : 看过 但不是很喜欢 太压抑 虽然感情戏写的不错 但虐过头了
     * author : {"_id":"55c43916ad75a05059fd23d7",
     * "avatar":"/avatar/5a/1f/5a1fb41215c3e7f9cedb8310ad76d3d8","nickname":"恋旧的人",
     * "type":"normal","lv":8,"gender":"female"}
     * floor : 105
     * likeCount : 0
     * created : 2016-08-25T15:16:56.437Z
     * replyTo : {"_id":"57bf04e65889e74a6f0808bf","floor":104,
     * "author":{"_id":"5794ad7ffda61987396d6216","nickname":"从未改变"}}
     */

    public List<CommentsBean> comments;

    public static class CommentsBean {
        public String _id;
        public String content;
        /**
         * _id : 55c43916ad75a05059fd23d7
         * avatar : /avatar/5a/1f/5a1fb41215c3e7f9cedb8310ad76d3d8
         * nickname : 恋旧的人
         * type : normal
         * lv : 8
         * gender : female
         */

        public AuthorBean author;
        public int floor;
        public int likeCount;
        public String created;
        /**
         * _id : 57bf04e65889e74a6f0808bf
         * floor : 104
         * author : {"_id":"5794ad7ffda61987396d6216","nickname":"从未改变"}
         */

        public ReplyToBean replyTo;

        public static class AuthorBean {
            public String _id;
            public String avatar;
            public String nickname;
            public String type;
            public int lv;
            public String gender;
        }

        public static class ReplyToBean {
            public String _id;
            public int floor;
            /**
             * _id : 5794ad7ffda61987396d6216
             * nickname : 从未改变
             */

            public AuthorBean author;

            public static class AuthorBean {
                public String _id;
                public String nickname;
            }
        }
    }
}
