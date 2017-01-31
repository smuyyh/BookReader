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

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/4.
 */
public class Recommend extends Base {

    public List<RecommendBooks> books;

    public static class RecommendBooks implements Serializable {

        /**
         * _id : 526e8e3e7cfc087140004df7
         * author : 太一生水
         * cover : /agent/http://image.cmfu.com/books/3347382/3347382.jpg
         * shortIntro : 十大封号武帝之一，绝世古飞扬在天荡山脉陨落，于十五年后转世重生，化为天水国公子李云霄，开启了一场与当世无数天才相争锋的逆天之旅。武道九重，十方神境，从此整个世界...
         * title : 万古至尊
         * hasCp : true
         * latelyFollower : 3053
         * retentionRatio : 42.59
         * updated : 2016-07-25T15:29:51.703Z
         * chaptersCount : 2406
         * lastChapter : 第2406章 千载风云尽付一笑（大结局）
         */

        public String _id;
        public String author;
        public String cover;
        public String shortIntro;
        public String title;
        public boolean hasCp;
        public boolean isTop = false;
        public boolean isSeleted = false;
        public boolean showCheckBox = false;
        public boolean isFromSD = false;
        public String path = "";
        public int latelyFollower;
        public double retentionRatio;
        public String updated = "";
        public int chaptersCount;
        public String lastChapter;
        public String recentReadingTime = "";

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof RecommendBooks) {
                RecommendBooks bean = (RecommendBooks) obj;
                return this._id.equals(bean._id);
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return this._id.hashCode();
        }
    }
}
