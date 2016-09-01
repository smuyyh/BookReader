package com.justwayward.reader.bean;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * Created by lfh on 2016/9/1.
 * 书荒区帖子列表
 */
public class HelpList extends Base{


    /**
     * _id : 57c63a9e641e6d0b762e3ac1
     * title : 【追书读书会】第一期·那些该死的快穿文
     * author : {"_id":"56e903c1febd4661455a0692",
     * "avatar":"/avatar/7b/e1/7be142f47d8ef8834727b1dd2c7bbbc1","nickname":"追书家的眼镜娘",
     * "type":"official","lv":8,"gender":"female"}
     * likeCount : 17
     * state : hot
     * updated : 2016-09-01T13:57:22.643Z
     * created : 2016-08-31T02:02:06.227Z
     * commentCount : 183
     */

    private List<HelpsBean> helps;

    public List<HelpsBean> getHelps() {
        return helps;
    }

    public void setHelps(List<HelpsBean> helps) {
        this.helps = helps;
    }

    public static class HelpsBean {
        private String _id;
        private String title;
        /**
         * _id : 56e903c1febd4661455a0692
         * avatar : /avatar/7b/e1/7be142f47d8ef8834727b1dd2c7bbbc1
         * nickname : 追书家的眼镜娘
         * type : official
         * lv : 8
         * gender : female
         */

        private AuthorBean author;
        private int likeCount;
        private String state;
        private String updated;
        private String created;
        private int commentCount;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public static class AuthorBean {
            private String _id;
            private String avatar;
            private String nickname;
            private String type;
            private int lv;
            private String gender;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getLv() {
                return lv;
            }

            public void setLv(int lv) {
                this.lv = lv;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }
        }
    }
}
