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
 * 书荒区帖子列表
 */
public class BookHelpList extends Base{


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

    public List<HelpsBean> helps;

    public static class HelpsBean {
        public String _id;
        public String title;
        /**
         * _id : 56e903c1febd4661455a0692
         * avatar : /avatar/7b/e1/7be142f47d8ef8834727b1dd2c7bbbc1
         * nickname : 追书家的眼镜娘
         * type : official
         * lv : 8
         * gender : female
         */

        public AuthorBean author;
        public int likeCount;
        public String state;
        public String updated;
        public String created;
        public int commentCount;

        public static class AuthorBean {
            public String _id;
            public String avatar;
            public String nickname;
            public String type;
            public int lv;
            public String gender;
        }
    }
}
