/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
