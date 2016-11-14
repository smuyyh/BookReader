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
 * Created by lfh on 2016/9/1.
 * 书评列表
 */
public class BookReviewList extends Base {


    /**
     * _id : 57c78c949869613834c1c519
     * title : 【书评两星，主要是不爱看这类的书，没有贬低此书的意思，见谅】
     * book : {"_id":"5086510f9dacd30e3a000026","cover":"/agent/http://image.cmfu
     * .com/books/109222/109222.jpg","title":"无限恐怖","site":"zhuishuvip","type":"khly"}
     * helpful : {"total":-38,"no":45,"yes":7}
     * likeCount : 1
     * state : normal
     * updated : 2016-09-01T13:07:57.530Z
     * created : 2016-09-01T02:04:04.487Z
     */

    public List<ReviewsBean> reviews;

    public static class ReviewsBean {
        public String _id;
        public String title;
        /**
         * _id : 5086510f9dacd30e3a000026
         * cover : /agent/http://image.cmfu.com/books/109222/109222.jpg
         * title : 无限恐怖
         * site : zhuishuvip
         * type : khly
         */

        public BookBean book;
        /**
         * total : -38
         * no : 45
         * yes : 7
         */

        public HelpfulBean helpful;
        public int likeCount;
        public String state;
        public String updated;
        public String created;

        public static class BookBean {
            public String _id;
            public String cover;
            public String title;
            public String site;
            public String type;
        }

        public static class HelpfulBean {
            public int total;
            public int no;
            public int yes;
        }
    }
}
