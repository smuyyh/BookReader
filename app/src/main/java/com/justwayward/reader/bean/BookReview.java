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

/**
 * Created by lfh on 2016/9/1.
 * 书评详情
 */
public class BookReview extends Base {

    /**
     * _id : 57c70079eac0de2b0f5302ed
     * rating : 5
     * content :
     * 看完最后一章最后一句，心里的激动已所剩无几，取代而之的是不停的怀念和数不尽的唏嘘。纵观雪中，那座时时代代都会有高手交替出现的江湖会被所有看完雪中的人所留恋。我所留恋的是那座有剑的江湖，先有吕祖仗剑无敌于人间，后有老李一剑破甲两千六，再有隋白眉三口吐尽百年剑气，还有柴青山，王小屏，齐仙侠等等无数剑客，正是有了他们，江湖才算是真正有剑的江湖，而我最喜欢的还是那个手持桃花倒骑毛驴的中年大叔，那个最不像高手的桃花剑神，正是因为有了他，李淳刚后的剑道才没有再次沦为长夜。
     十二飞剑钉死天上仙人，借老李一剑入陆地剑仙战平拓拔菩萨，南海访仙归来一剑挑翻南海，万里一剑追杀飞鱼，天门外斩落八十一名天上仙人，千年已详杀力第一，更是被李当心说为世间除了老婆女儿唯一能破其大金刚境之人。虽然全书剑道风采几乎被李淳刚一人占尽，可我仍是喜欢老邓那狂放不羁的个性以及一柄杀力第一的太阿，有了他雪中的江湖才更多了一分逍遥快活。
     四大宗师中，小年背负了太多，看似最强实则最悲；西楚霸王一生只为一位女子而活，看似最风流实则最无奈；拓拔菩萨只想将江湖庙堂皆收入囊中其实早已失去了位列宗师的资格；而他邓太阿不恋江湖意气，不管武榜排名，只想那个徒弟过得是否安好，只在意自己身旁是否有三尺剑相伴。
     天不生我李淳刚，剑道万古如长夜。
     天不生我邓太阿，剑术杀意何人知？
     * title : 我有一剑，名为太阿
     * type : review
     * book : {"_id":"50975b961db679b876000029","cover":"/agent/http://static.zongheng
     * .com/upload/cover/2012/07/1342262264139.jpg","title":"雪中悍刀行","id":"50975b961db679b876000029"}
     * author : {"_id":"5655095abbb1cb9b7b100377",
     * "avatar":"/avatar/df/32/df32b5817e989477ed479edb3ed960bf","nickname":"yapppppp",
     * "type":"normal","lv":8,"gender":"male","rank":null,"created":"2015-11-25T01:05:30.000Z",
     * "id":"5655095abbb1cb9b7b100377"}
     * helpful : {"total":45,"yes":58,"no":13}
     * state : normal
     * updated : 2016-09-01T12:09:54.128Z
     * created : 2016-08-31T16:06:17.364Z
     * commentCount : 35
     * shareLink : http://share.zhuishushenqi.com/post/57c70079eac0de2b0f5302ed
     * id : 57c70079eac0de2b0f5302ed
     */

    public ReviewBean review;

    public static class ReviewBean {
        public String _id;
        public int rating;
        public String content;
        public String title;
        public String type;
        /**
         * _id : 50975b961db679b876000029
         * cover : /agent/http://static.zongheng.com/upload/cover/2012/07/1342262264139.jpg
         * title : 雪中悍刀行
         * id : 50975b961db679b876000029
         */

        public BookBean book;
        /**
         * _id : 5655095abbb1cb9b7b100377
         * avatar : /avatar/df/32/df32b5817e989477ed479edb3ed960bf
         * nickname : yapppppp
         * type : normal
         * lv : 8
         * gender : male
         * rank : null
         * created : 2015-11-25T01:05:30.000Z
         * id : 5655095abbb1cb9b7b100377
         */

        public AuthorBean author;
        /**
         * total : 45
         * yes : 58
         * no : 13
         */

        public HelpfulBean helpful;
        public String state;
        public String updated;
        public String created;
        public int commentCount;
        public String shareLink;
        public String id;

        public static class BookBean {
            public String _id;
            public String cover;
            public String title;
            public String id;
        }

        public static class AuthorBean {
            public String _id;
            public String avatar;
            public String nickname;
            public String type;
            public int lv;
            public String gender;
            public Object rank;
            public String created;
            public String id;
        }

        public static class HelpfulBean {
            public int total;
            public int yes;
            public int no;
        }
    }
}
