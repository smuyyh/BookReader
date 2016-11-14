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

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/4.
 */
public class BookDetail implements Serializable {


    /**
     * _id : 525253d094336b3155000dd8
     * author : w风雪
     * banned : 0
     * cover : /agent/http://image.cmfu.com/books/2797907/2797907.jpg
     * creater : iPhone 5 (GSM+CDMA)
     * dramaPoint : null
     * followerCount : 35
     * gradeCount : 0
     * isSerial : true
     * lastChapter : 请安装【追书神器】，本应用已停用
     * latelyFollower : 2385
     * longIntro : 您当前所使用的软件已改名为【追书神器】。
     请搜索“追书神器”下载安装最新版【追书神器】。
     无广告；不闪退；章节更新自动通知。
     * postCount : 111
     * serializeWordCount : 4614
     * site : zhuishuvip
     * tags : ["热血","洪荒封神","洪荒","架空","修炼","仙侠"]
     * title : 洪荒造化
     * tocs : ["525253d194336b3155000dd9","525253e6a4e35219120006a6","525253e6a4e35219120006a7","525253e6a4e35219120006a8","525253e6a4e35219120006a9","525253e6a4e35219120006ab","52c65225c1988af227000251","52c68d2cf91d99312b000d92","52c690bc0f3d8bda2b000873","532cf9dd39493253790020f4"]
     * totalPoint : null
     * type : wxxx
     * updated : 2016-04-03T13:48:05.907Z
     * writingPoint : null
     * hasNotice : false
     * tagStuck : 0
     * chaptersCount : 1294
     * tocCount : 15
     * tocUpdated : 2016-03-25T21:03:42.962Z
     * retentionRatio : 20.56
     * hasCmread : true
     * thirdFlagsUpdated : 2014-09-01T06:01:24.259Z
     * categories : ["洪荒封神","仙侠"]
     * wordCount : 5947980
     * cat : 仙侠
     * gender : ["male"]
     * majorCate : 仙侠
     * minorCate : 洪荒封神
     * reviewCount : 9
     * monthFollower : {"11":2170}
     * totalFollower : 2170
     * cpOnly : false
     * hasCp : true
     * _le : false
     */

    public String _id;
    public String author;
    public int banned;
    public String cover;
    public String creater;
    public Object dramaPoint;
    public int followerCount;
    public int gradeCount;
    public boolean isSerial;
    public String lastChapter;
    public int latelyFollower;
    public String longIntro;
    public int postCount; // 社区帖子数
    public int serializeWordCount;
    public String site;
    public String title;
    public Object totalPoint;
    public String type;
    public String updated;
    public Object writingPoint;
    public boolean hasNotice;
    public int tagStuck;
    public int chaptersCount;
    public int tocCount;
    public String tocUpdated;
    public String retentionRatio;
    public boolean hasCmread;
    public String thirdFlagsUpdated;
    public int wordCount;
    public String cat;
    public String majorCate;
    public String minorCate;
    public int reviewCount;
    public int totalFollower;
    public boolean cpOnly;
    public boolean hasCp;
    public boolean _le;
    public List<String> tags;
    public List<String> tocs;
    public List<String> categories;
    public Object gender; // MLGB, 偶尔是String，偶尔是Array
}
