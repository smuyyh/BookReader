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

/**
 * @author yuyh.
 * @date 2016/8/4.
 */
public class ChapterRead extends Base {

    /**
     * title : 1，相亲
     * body : 月湾咖啡厅，苏锦一进门就瞧见了那个相亲对象。
     他正坐在向阳的9号桌上。
     身上穿的是一件大海蓝的衬衫，没系领带，发型清爽，五官端正，淡淡的阳光照在他脸上，衬得他冷峻毕露。
     乍一看，威势十足，竟惹得边上几个年轻小姑娘频频侧目。
     这人，的确长着一副好皮囊，怪不得王阿婆一个劲儿的向她夸：
     “你只要见，保管觉得好。那种人，只有他挑人，没人会挑他的。”
     现在看来好像有点道理。
     门口，有道长廊镜，苏锦转头看了看自己这身打扮。
     长款黑色雪纺衬衣，底下配一条白色九分裤，露着白藕似的胳膊，最能显示女性魅力的中发被她扎成了马尾，清水芙蓉似的脸孔，没上妆，显得素净清秀。
     先头，王阿婆一个劲儿的叮嘱她：“去的时候，打扮打扮！那孩子眼界高着呢！”
     她没打扮，素面朝天，这才是最真实的她。
     “你好，我叫苏锦！你是靳恒远先生吗？”
     苏锦走了过去，声音温温婉婉。
     正在用手机看着股市行情的男人抬起头，看到她时，目光闪了一下，站起时收了手机微一笑。
     这一笑，让他那显得疏离的脸孔多了几分亲切。
     “对，我是靳恒远！苏小姐是吗？请坐！”
     靳恒远很绅士的给她拉开椅子，嗓音低低富有磁性，极为好听。
     “谢谢！”
     苏锦坐下，点了一杯咖啡，呷了几口，才说话：“靳先生的一些生平，我多少听王阿婆说了一些。”
     男人正打量她，她有点不自在的捋了捋耳边的发。
     相亲相亲，总得让人看的。
     好在，他的目光并不让人觉得讨厌。
     “哦，不知道王婆是怎么自卖自夸的？”
     靳恒远挺风趣的反问。
     苏锦扯了扯嘴角。
     其实她所知甚少。
     “靳先生今年三十二岁了是吗？”
     比她大了足足六岁，算是个老男人了。
     不过见到本人，看着挺年轻。
     “嗯！”
     “像靳先生这样仪表堂堂的男士，怎么会至今未婚？”
     “工作太忙，耽误了，等到想要结婚的时候，才发现和自己年纪相仿的异性，孩子都满地跑了……”
     靳恒远微一笑，喝了一口咖啡。
     “苏小姐对相亲对象有什么要求吗？”
     “我要求不高！”苏锦说：“品性要正，责任感要强，必须忠于婚姻。”
     “对车房没要求？”
     靳恒远睨了一眼，那一眼，好像很有深意。
     苏锦微微笑了一个。
     现在女性找老公，车房已经成了标配，哪怕在这样一个小县城：女孩子谁不想嫁个家境好的男人，把日子过舒坦了。
     要不然怎么会说结婚是女人第二次投胎？
     第一次谁也没得选择，第二次必须千挑万选，这要选错了，那一辈子就全毁了。
     只是苏锦的心，早死了，如今的她，相亲，只是为了给母亲一个交代。
     “没要求。”
     她吐出三字。
     ……
     淡淡的咖啡香，飘散在空气中，两个陌生的男女，不咸不淡聊着天。
     过了一会儿，苏锦看了一下手表：
     “靳先生，该了解的，我们都已经谈了，如果你认为我们合适，我想明天就去领证。”
     靳恒远眉一挑，但笑不笑：“苏小姐就这么急着结婚，不怕我是骗子？”
     苏锦淡一笑：“王阿婆眼光一向很挑，她介绍的人，一定不差。”
     “苏小姐就这么相信王阿婆？”
     “我信。另外，我妈妈得了肝癌，晚期。她希望在临走之前看到我找到一个好归宿。在时间上，我耗不起。”
     一般来说，没有人肯这么草率同意婚事的。
     苏锦以为他会拒绝，这样她就可以顺水推舟了，王阿婆那边也好交待。
     “明天不行。明天我要出差，这几天都会在外地。要领下午就去。”
     靳恒远看了看手机上的钟点。
     “你要没意见，我现在回去拿证件，两点半，我们在民政局见。”
     苏锦：“……”
     - - - 题外话
     - - -
     待续……
     新文，全新尝试，喜欢的亲请多多支持，谢谢！
     */

    public Chapter chapter;

    public static class Chapter implements Serializable{
        public String title;
        public String body;
        public String cpContent;

        public Chapter(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public Chapter(String title, String body, String cpContent) {
            this.title = title;
            this.body = body;
            this.cpContent = cpContent;
        }
    }
}
