package com.justwayward.reader.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class Ranking {


    /**
     * _id : 54d42d92321052167dfb75e3
     * updated : 2016-08-14T21:20:21.090Z
     * title : 追书最热榜 Top100
     * tag : zhuishuLatelyFollowerMale
     * cover : /ranking-cover/142319144267827
     * __v : 509
     * monthRank : 564d820bc319238a644fb408
     * totalRank : 564d8494fe996c25652644d2
     * isSub : false
     * collapse : false
     * new : true
     * gender : male
     * priority : 250
     * books : [{"_id":"53855a750ac0b3a41e00c7e6","title":"择天记","author":"猫腻",
     * "shortIntro":"太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。
     * 数千年后，十四岁的少年孤儿陈长生，为治病...","cover":"/agent/http://image.cmfu.com/books/3347595/3347595.jpg",
     * "site":"qidian","cat":"玄幻","banned":0,"latelyFollower":182257,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.48"},{"_id":"5091fbcf8d834c0f190000cd",
     * "author":"鱼人二代","cover":"/agent/http://image.cmfu.com/books/1931432/1931432.jpg",
     * "shortIntro":"一个大山里走出来的绝世高手，一块能预知未来的神秘玉佩\u2026\u2026
     * 林逸是一名普通学生，不过，他还身负另外一个重任，那就是追校花！而且还是奉校花老爸之命！ 虽然林逸很不想...","title":"校花的贴身高手","site":"qidian",
     * "cat":"都市","banned":0,"latelyFollower":153917,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.29"},{"_id":"5373898f1032be0155019e73",
     * "title":"儒道至圣","author":"永恒之火","shortIntro":"这是一个读书人掌握天地之力的世界。 才气在身，诗可杀敌，词能灭军，文章安天下。
     * 秀才提笔，纸上谈兵；举人杀敌，出口成章；进士一怒，唇枪舌剑。 圣人驾临，口诛笔伐...","cover":"/agent/http://image.cmfu
     * .com/books/3173393/3173393.jpg","site":"zhuishuvip","cat":"玄幻","banned":0,
     * "latelyFollower":118695,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"62
     * .7"},{"_id":"526e8e3e7cfc087140004df7","author":"太一生水","cover":"/agent/http://image.cmfu
     * .com/books/3347382/3347382.jpg",
     * "shortIntro
     * ":"十大封号武帝之一，绝世古飞扬在天荡山脉陨落，于十五年后转世重生，化为天水国公子李云霄，开启了一场与当世无数天才相争锋的逆天之旅。武道九重，十方神境，从此整个世界...",
     * "site":"zhuishuvip","title":"万古至尊","cat":"玄幻","banned":0,"latelyFollower":166088,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"42.89"},
     * {"_id":"50874828abf1ced53c00003b","author":"萧鼎","cover":"/agent/http://wap.cmread
     * .com/r/cover_file/7182/390607182/20131226142254/cover180240.jpg",
     * "shortIntro":"普通少年张小凡为救红颜，手持烧火棍与整个世界为敌，何为正，何为邪，可笑万物如刍狗，谁为覆雨谁翻云！","title":"诛仙",
     * "site":"zhuishuvip","cat":"仙侠","banned":0,"latelyFollower":152544,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"42.17"},{"_id":"559b51abadcc20911c4a3b16",
     * "title":"万古神帝","author":"飞天鱼","shortIntro":"八百年前，明帝之子张若尘，被他的未婚妻池瑶公主杀死，一代天骄，就此陨落。
     * 八百年后，张若尘重新活了过来，却发现曾经杀死他的未婚妻， 已经统一昆仑界，开辟出第一中...","cover":"/agent/http://img1.chuangshi.qq
     * .com/upload/cover/20150710/cb_559fc855bd0b4.jpg","cat":"玄幻","site":"zhuishuvip",
     * "banned":0,"latelyFollower":99465,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"56.25"},{"_id":"54831bc7cc6633040d8a6256","title":"天域苍穹",
     * "author":"风凌天下","shortIntro":"笑尽天下英雄，宇内我为君主！
     * 天域君主叶笑，以一人之力战三大宗门，以一敌万，大杀四方，但终究寡不敌众。一朝醒来，发现重生世俗界，更得到极品灵宝天晶灵髓，无尽空间...",
     * "cover":"/agent/http://img1.chuangshi.qq.com/upload/cover/20150916/cb_55f918fb5a617.jpg",
     * "site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":79085,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"50.92"},{"_id":"56e174ef0dba234122588ef0",
     * "title":"英雄联盟之全民解说","author":"我的长枪依在",
     * "shortIntro":"职业选手王磊重生了，他反思过去，决定分享自己在联盟中得到的快乐，于是他成为了一名解说。
     * 平时打打直播，没事调戏调戏美女主播，和女房客搞搞暧昧，教育教育文化女青年...","cover":"/agent/http://image.cmfu
     * .com/books/1001776467/1001776467.jpg","cat":"网游","site":"qidian","banned":0,
     * "latelyFollower":67316,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58
     * .97"},{"_id":"542a5838a5ae10f815039a7f","title":"逆天邪神","author":"火星引力",
     * "shortIntro":"掌天毒之珠，承邪神之血，修逆天之力，一代邪神，君临天下！","cover":"/agent/http://static.zongheng
     * .com/upload/cover/2014/11/1416425191645.jpg","site":"zhuishuvip","cat":"玄幻","banned":0,
     * "latelyFollower":110678,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"36
     * .71"},{"_id":"5589c5d1cc6b9d3d794cbdb9","title":"女神的近身护卫","author":"肥茄子",
     * "shortIntro":"一次意外，萧正被大众眼中的冰山女BOSS倒追逼婚。并顺利成为号称女儿国的新奥公司一员，从此展开万花丛中过的精彩人生。
     * 冰山女神、暴龙霸王花、温柔女上司、俏皮学生...","cover":"/agent/http://img.17k
     * .com/images/bookcover/2015/6273/31/1254615-1436894038000.jpg","cat":"都市",
     * "site":"zhuishuvip","banned":0,"latelyFollower":58492,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"64.19"},{"_id":"50c541538380e4f81500001a",
     * "author":"风凌天下","cover":"/agent/http://image.cmfu.com/books/1524659/1524659.jpg",
     * "shortIntro":"世间毁誉，世人冷眼，与我何干？我自淡然一笑； 以吾本性，快意恩仇，以吾本心，遨游世间，我命由我不由天！
     * 一代牛人穿越异界，看其如何踏上异世巅峰，成为一代邪君！","title":"异世邪君","site":"qidian","cat":"玄幻","banned":0,
     * "latelyFollower":57151,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"62
     * .78"},{"_id":"56e181a1238b4ed376f3dd04","title":"超级仙学院","author":"尺长寸短",
     * "shortIntro":"都市中有这样一所学院，它不教人数理化，而是教人武功秘法，神功异能。学院的老师也不是一般的老师，而是来源于各个武侠小说以及影视作品里面的响当当人物。
     * 岳不群：\u201c今...","cover":"/agent/http://image.cmfu.com/books/1001667168/1001667168.jpg",
     * "cat":"都市","site":"qidian","banned":0,"latelyFollower":49994,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"63.72"},{"_id":"5790bc09e72826f52571f63c",
     * "title":"美食供应商","author":"会做菜的猫","shortIntro":"\u201c在遥远的东方，存在着一个数次拒绝了米其林三星评价的奇怪小店。
     * 那里价格昂贵，一碗配汤蛋炒饭288RMB，哦忘了还有一碟泡菜，但就算是这样也有很多人排队等候。 ...","cover":"/cover/147122961340677",
     * "cat":"都市","site":"qidian","banned":0,"latelyFollower":42141,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"61.98"},{"_id":"56eaf7fe4fb43b1203fab269",
     * "title":"超级怪兽工厂","author":"匣中藏剑","shortIntro":"我们这个工厂，不讲科技，它讲技能，讲工具。
     * 大锤（卓越品质）属性：物体硬度减少70%，5%几率发动万吨水压机冲击波。 吼吼～我是怪兽，我们是混蛋，我们有铜墙铁壁...",
     * "cover":"/agent/http://image.cmfu.com/books/1002959239/1002959239.jpg","cat":"都市",
     * "site":"zhuishuvip","banned":0,"latelyFollower":61649,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"55.14"},{"_id":"5121d75e1b7e32db54000011",
     * "author":"南希北庆","cover":"/agent/http://image.cmfu.com/books/2499752/2499752.jpg",
     * "shortIntro":"身为一家超五星级酒店首席大厨的李奇，因为喝了点小酒，竟然奇迹般的穿越到了北宋末年。
     * 来到这个无亲无故的陌生世界，无奈之下，李奇只好抄起了老本行，在汴京一家即将贱...","title":"北宋小厨师","site":"zhuishuvip","cat":"历史",
     * "banned":0,"latelyFollower":48965,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"61.69"},{"_id":"57416eeb95895b3b4ae06f8e","title":"史上最强店主",
     * "author":"南极烈日","shortIntro":"商通万界，让所有人给我打工。 周阳意外获得万界商铺系统，交易万千位面。
     * \u201c我曾举行一次小型拍卖会，来到的皇帝就有一百多位，听说还有两个叫李世民的皇帝。\u201d \u201c我曾...",
     * "cover":"/agent/http://image.cmfu.com/books/1003504656/1003504656.jpg","cat":"无限",
     * "site":"qidian","banned":0,"latelyFollower":44001,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"65.08"},{"_id":"518c93dc5f5a2f4e4300000a",
     * "author":"梁少","cover":"/agent/http://static.zongheng
     * .com/upload/cover/2014/05/1399893353629.jpg",
     * "shortIntro
     * ":"文武双科状元萧阳意外穿越到现代都市，以【伴读小书童】以及【女生寝室门卫】双重身份进入大学校园，开始香艳惑色的护花之旅。力撰反穿越第一精品，继《唐伯虎现代寻芳记》...",
     * "title":"护花状元在现代","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":45942,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"63.84"},
     * {"_id":"531169b3173bfacb4904ca67","author":"耳根","cover":"/agent/http://image.cmfu
     * .com/books/3106580/3106580.jpg","shortIntro":"我若要有 天不可无 我若要无 天不许有！ 这是一个起始于第八山与第九山之间的故事
     * 一个\u201c我命如妖欲封天\u201d的世界！ 这是耳根继《仙逆》《求魔》后，创作的第三部长篇...","title":"我欲封天","site":"zhuishuvip",
     * "cat":"仙侠","banned":0,"latelyFollower":54412,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"49.83"},{"_id":"56c44b82121f2cb228745ef2","title":"灭世魔帝",
     * "author":"沉默的糕点","shortIntro":"大三男生兰陵被流星砸中，穿越异世，被索氏家族女武士所救。
     * 仿佛是天意，他和天水城少主索伦长得很像，而这个纨绔已经死于非命。 为了报救命之恩，主角答应冒充少主索伦...","cover":"/agent/http://image.cmfu
     * .com/books/3686556/3686556.jpg","cat":"玄幻","site":"qidian","banned":0,
     * "latelyFollower":41542,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"62
     * .65"},{"_id":"52385133777d4d8455002666","author":"我是墨水","cover":"/agent/http://image.cmfu
     * .com/books/2939492/2939492.jpg","shortIntro":"我若执魔，天地无仙！我若执天，天地无魔！
     * 这是一个起始于雨之仙界的故事，一个\u2018我命如蝶斩轮回\u2019的世界！ 回首凡尘如烟，一笑淡了明月...只为她，横行雨界！ 天道...",
     * "site":"zhuishuvip","title":"执魔","cat":"仙侠","banned":0,"latelyFollower":62884,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"30.21"},
     * {"_id":"52eb685430174377060003d1","author":"颓废的烟121","cover":"/agent/http://images.zhulang
     * .com/book_cover/image/27/9/270971.jpg","shortIntro":"带着异世界的吞天武灵，废物少爷绝地逆袭，一跃成为震惊大陆的武学天才！
     * 强大的武技信手拈来，强横的敌人踩在脚下。 神秘的家族，未知的领域，一切的精彩，尽在武灵天下...","title":"武灵天下","site":"zhuishuvip",
     * "cat":"玄幻","banned":0,"latelyFollower":40669,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"62.87"},{"_id":"5717393d79eeca5e2854566a","title":"本座东方不败",
     * "author":"星辰雨","shortIntro":"武侠大世界中，秦、隋、宋、明四国共立，一个人穿越成东方不败的故事，且看东方不败败尽天下。 主角是个很正常的男人。",
     * "cover":"/agent/http://image.cmfu.com/books/1003389975/1003389975.jpg","cat":"武侠",
     * "site":"qidian","banned":0,"latelyFollower":33966,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"65.39"},{"_id":"5108725a7a2138ed06000001",
     * "author":"蛊真人","cover":"/agent/http://image.cmfu.com/books/2527417/2527417.jpg",
     * "shortIntro":"人是万物之灵，蛊是天地真精。 三观不正，魔头重生。 昔日旧梦，同名新作。 一个穿越者不断重生的故事。 一个养蛊、炼蛊、用蛊的奇特世界。
     * 春秋蝉、月光蛊、酒虫、一...","title":"蛊真人","site":"zhuishuvip","cat":"仙侠","banned":0,
     * "latelyFollower":39623,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56
     * .06"},{"_id":"5762847a36a7b3d03511b3b8","title":"不朽凡人","author":"鹅是老五",
     * "shortIntro":"我，只有凡根，一介凡人！ 我，叫莫无忌！ 我，要不朽！","cover":"/agent/http://image.cmfu
     * .com/books/1003307568/1003307568.jpg","cat":"仙侠","site":"qidian","banned":0,
     * "latelyFollower":37478,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58
     * .99"},{"_id":"5662ca66b8cb23ce21ba25f2","title":"大魏宫廷","author":"贱宗首席弟子",
     * "shortIntro":"生作大魏皇子， 愿当盛世闲王。 志在偎红倚翠犬马声色， 胸怀家国百姓社稷安危。 若兄贤，若弟明， 尔为人王吾偷闲。 若尔不能使国强， 吾来登基做帝王！
     * \u2014\u2014\u2014\u2014...","cover":"/agent/http://image.cmfu.com/books/3662715/3662715
     * .jpg","cat":"历史","site":"qidian","banned":0,"latelyFollower":34816,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"54.5"},{"_id":"56e6a5278181622047dc5a11",
     * "title":"英雄联盟之灾变时代","author":"会说话的肘子","shortIntro":"吕尘带着来自地球的一身LOL技巧穿了！
     * 同样是打英雄联盟，只不过这次，他要变超人！","cover":"/agent/http://image.cmfu.com/books/1002399247/1002399247
     * .jpg","cat":"网游","site":"qidian","banned":0,"latelyFollower":42315,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"51.69"},{"_id":"57297937661cbd303df9a39b",
     * "title":"龙皇武神","author":"步征","shortIntro":"佛道魔同修！体内蕴真龙！ 拥有太古龙神血脉，获得上古三皇传承！ 斩妖邪，除恶魔，一路逆天修行！
     * 我从修真世界而来，我将乘龙飞仙而去！","cover":"/agent/http://image.cmfu.com/books/2510964/2510964.jpg",
     * "cat":"都市","site":"qidian","banned":0,"latelyFollower":39780,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.67"},{"_id":"5091fcda8d834c0f190000dc",
     * "author":"鱼人二代","cover":"/agent/http://image.cmfu.com/books/1042235/1042235.jpg",
     * "shortIntro":"杨明是一名普通的学生，某一天，他收到一份礼物，一只神奇的眼镜，从此生活变得丰富多彩。","title":"很纯很暧昧",
     * "site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":38033,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"54.27"},{"_id":"508a2b97f5cf27d115000006",
     * "author":"只是小虾米","cover":"/agent/http://img.17k.com/images/bookcover/2012/962/4/192453
     * .jpg","shortIntro":"沉眠三载，不知岁月流江。 废材？不是，是天才！ 帝脉天赐，指天斥神张扬。 废体？不是，是神体！ 天下为敌，为伊孤战八方。
     * 男人的尊严，需自己找回！ 武逆修神，古...","title":"武逆","site":"w17k","cat":"奇幻","banned":0,
     * "latelyFollower":35908,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56
     * .96"},{"_id":"52159104c8f1be8010000015","author":"寂寞的舞者","cover":"/agent/http://img.17k
     * .com/images/bookcover/2014/2086/10/417239-1404360621000.jpg",
     * "shortIntro":"\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014 兵王之王，方为兵皇，兵皇入世，无限嚣张！
     * 一代兵皇，为身世回归都市，化身玩美房东，成为美女房客的贴身兵皇，狂啃窝边草！ 萧风扬言：谁敢吃我的...","site":"zhuishuvip","title":"贴身兵皇",
     * "cat":"都市","banned":0,"latelyFollower":35809,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"57.45"},{"_id":"571087e3430ce3417cdd079d","title":"帝临武侠",
     * "author":"隔壁老王01","shortIntro":"一颗紫色圆珠，一段不朽传奇，一个穿梭诸天位面的人。
     * 冯睿第一次穿越异位面，是一个修真位面，费尽千辛万苦，终于拜入七大门派之一清虚门，但因为在藏经阁偷盗功法，差点...","cover":"/agent/http://image
     * .cmfu.com/books/1003374935/1003374935.jpg","cat":"无限","site":"qidian","banned":0,
     * "latelyFollower":24521,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"69
     * .96"},{"_id":"55c48abcb905e5930c84ab61","title":"我在末世有套房","author":"晨星LL",
     * "shortIntro":"核战之后的末世，到处都是一片狼藉。
     * 如果你一不小心活了下来，那么接下来你将不得不面对饥饿与疾病的恐惧，一到夜晚就会发狂的丧尸，还有那些因辐射而变得奇形怪状的异种...",
     * "cover":"/agent/http://image.cmfu.com/books/3569244/3569244.jpg","cat":"末世",
     * "site":"zhuishuvip","banned":0,"latelyFollower":38066,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"51.13"},{"_id":"566e4d347e62feae50d41c52",
     * "title":"仙界网络直播间","author":"38大虾","shortIntro":"作为一个网络男主播，张小东突然发现自己直播间突然冒出了一批土神仙！
     * 一段郭德纲十年前的相声，\u201c好！！再来一段！\u201d财神赵公明哥哥这个冤大头扔下了一片元宝打赏。 \u201c...",
     * "cover":"/agent/http://image.cmfu.com/books/3662021/3662021.jpg","cat":"都市",
     * "site":"qidian","banned":0,"latelyFollower":34738,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"49.42"},{"_id":"56cd10bb32dc12e56764cf25",
     * "title":"天骄战纪","author":"萧瑾瑜",
     * "shortIntro
     * ":"苍图大陆，茫茫无垠。有古老宗门盘踞十方古域，有世外仙道屹立青冥之上，有太古妖神统领黑暗大渊，缔造了不知多少的辉煌篇章。就在这大千世界中，一个名叫林寻的少年，独自...",
     * "cover":"/agent/http://static.zongheng.com/upload/cover/2016/02/1456282051781.png",
     * "cat":"玄幻","site":"zongheng","banned":0,"latelyFollower":26314,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"64.01"},{"_id":"5761f7c1e8b588f50974f84b",
     * "title":"懒散初唐","author":"北冥老鱼",
     * "shortIntro
     * ":"武德五年，大唐初立，李渊呆在美女如云的后宫之中，忙着享受自己得来不易的胜利果实，李建成忙着稳固自己的太子之位，李世民忙着觊觎大哥的位子，武将们忙着打仗，文臣们忙...",
     * "cover":"/agent/http://image.cmfu.com/books/1003565025/1003565025.jpg","cat":"历史",
     * "site":"qidian","banned":0,"latelyFollower":29830,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"59.31"},{"_id":"508662b8d7a545903b000027",
     * "author":"忘语","cover":"/agent/http://image.cmfu.com/books/107580/107580.jpg",
     * "shortIntro":"一个普通山村小子，偶然下进入到当地江湖小门派，成了一名记名弟子。他以这样身份，如何在门派中立足,
     * 如何以平庸的资质进入到修仙者的行列，从而笑傲三界之中！","title":"凡人修仙传","site":"zhuishuvip","cat":"仙侠","banned":0,
     * "latelyFollower":33430,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"55
     * .44"},{"_id":"50c537838380e4f815000005","author":"王小蛮","cover":"/agent/http://wfqqreader
     * .3g.qq.com/cover/89/120089/t7_120089.jpg",
     * "shortIntro
     * ":"穿越异界附身世家落魄少爷，脑海带着本时灵时不灵的残破符法，心眼里藏着点小心思。且看他在残酷修真界中左右逢源，誓做纨绔的修仙生活。《盖世仙尊》已发！书友群群号58...",
     * "title":"修仙狂徒","site":"chuangshi","cat":"仙侠","banned":0,"latelyFollower":31135,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"60.7"},
     * {"_id":"53116126173bfacb49049b41","author":"犁天","cover":"/agent/http://image.cmfu
     * .com/books/3366482/3366482.jpg","shortIntro":"天帝之子江尘，转生在一个被人欺凌的诸侯少年身上，从此踏上一段轰杀各种天才的逆袭之路。
     * 在江尘面前，谁也没资格自称天才，因为，没有哪一个天才，能比天帝之子更懂天。...","title":"三界独尊","site":"zhuishuvip","cat":"玄幻",
     * "banned":0,"latelyFollower":33924,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"56.61"},{"_id":"50c54e158380e4f81500002e","author":"安山狐狸",
     * "cover":"/agent/http://img.17k.com/images/bookcover/2012/448/2/89718.jpg",
     * "shortIntro":"受尽别人白眼的季枫，在人生的低谷之际，得到了未来的科技产品，拥有了神奇的能力，从此他的人生变得不再平凡！
     * 透视功能，赌石无往不利。未来科技，让季枫成就商业帝国，...","title":"校园全能高手","site":"zhuishuvip","cat":"都市",
     * "banned":0,"latelyFollower":31674,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"57.19"},{"_id":"578b13f28ae3b47c3ce95bc9","title":"神级幸运星",
     * "author":"辰机唐红豆","shortIntro":"无意中获得了一枚运气骰子，还穿越到娱乐业匮乏的平行位面，王昊这下牛逼了。
     * 运气骰子的六个面分别是\u201c非常倒霉，倒霉，普通，好运，非常好运，神级好运\u201d，每天零点刷新...","cover":"/agent/http://qidian
     * .qpic.cn/qdbimg/349573/1003656831/180","cat":"都市","site":"qidian","banned":0,
     * "latelyFollower":38629,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"40
     * .66"},{"_id":"56d445d412e8175a3fefa45a","title":"穿越宁采臣","author":"西瓜有皮不好吃",
     * "shortIntro":"宁飞做了一个梦，梦见自己身处兰若寺中，有一个叫做聂小倩的女鬼，有一个叫做燕赤霞的大胡子、还有
     * 一棵千年树妖......然后，宁飞醒了过来，却发现自己成了宁采臣，...","cover":"/agent/http://image.cmfu
     * .com/books/1001756434/1001756434.jpg","cat":"仙侠","site":"qidian","banned":0,
     * "latelyFollower":25441,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"63
     * .76"},{"_id":"561a1133533f63f91ba0dcca","title":"冠军之心","author":"林海听涛",
     * "shortIntro":"\u201c周先生，我们注意到您小时候曾经涉猎过篮球、乒乓球、羽毛球、围棋、跳棋、五子棋、中国象棋、国际象棋、书法绘画、吉他音乐\u2026\u2026
     * 等多方面的领域，那这是否意味着就算您当...","cover":"/agent/http://image.cmfu.com/books/3623405/3623405.jpg",
     * "cat":"体育","site":"zhuishuvip","banned":0,"latelyFollower":26860,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"63.55"},{"_id":"50ce6b1263d90df45a000001",
     * "author":"我吃西红柿","cover":"/agent/http://image.cmfu.com/books/2502372/2502372.jpg",
     * "shortIntro":"纪宁死后来到阴曹地府，经判官审前生判来世，投胎到了部族纪氏。 这里， 有夸父逐日\u2026\u2026 有后羿射金乌\u2026\u2026
     * 更有为了逍遥长生，历三灾九劫，纵死无悔的无数修仙者\u2026\u2026 ...","title":"莽荒纪","site":"zhuishuvip","cat":"仙侠",
     * "banned":0,"latelyFollower":33897,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"53.87"},{"_id":"512199de221b4d2f30000100","author":"风青阳",
     * "cover":"/agent/http://img.17k.com/images/bookcover/2015/1955/9/391013-1423808477000.jpg",
     * "shortIntro":"上古世纪，天地初开，龙祭大陆乃神龙一族之天下，时至今日神龙灭绝殆尽，神秘浩劫再度降临，天下苍生岌岌可危！
     * 少年龙辰，背负父辈荣耀使命，追逐神龙灭绝谜团。机缘巧合...","title":"龙血战神","site":"zhuishuvip","cat":"奇幻","banned":0,
     * "latelyFollower":31802,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54
     * .25"},{"_id":"5757a7bf04eef8d3352049fc","title":"神级位面教师","author":"烟火色",
     * "shortIntro":"作为一名地球出品的位面教师，苏牧风的学生遍布多元宇宙，有战国时代的诸侯，有三十三天外的真仙，有银河母舰的舰长，有魔导帝国的皇帝。
     * 苏牧风的日常工作主要有三项，第...","cover":"/agent/http://image.cmfu.com/books/1003540925/1003540925
     * .jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":27330,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"55.22"},{"_id":"508751bef98e8f7446000024",
     * "author":"辰东","cover":"/agent/http://image.cmfu.com/books/63856/63856.jpg",
     * "shortIntro":"一个死去万载岁月的平凡青年从远古神墓中复活而出\u2026\u2026","title":"神墓","site":"zhuishuvip",
     * "cat":"玄幻","banned":0,"latelyFollower":33492,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"49.88"},{"_id":"56cc69ded6db3bbe47c23e99","title":"我的时空穿梭手机",
     * "author":"金色茉莉花",
     * "shortIntro":"当安阳发现自己买的手机被调包了之后，他一开始是拒绝，当他发现这部手机可以带他穿越无尽的时空，电影、电视剧、小说、游戏\u2026\u2026
     * 他再也无法拒绝了！ 人生自此开始转变。","cover":"/agent/http://image.cmfu.com/books/1001649143/1001649143
     * .jpg","cat":"无限","site":"qidian","banned":0,"latelyFollower":29526,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"48.72"},{"_id":"5517a0d6179d91e8774040e5",
     * "title":"修真四万年","author":"卧牛真人",
     * "shortIntro":"\u201c这是一艘\u2018天狼\u2019
     * 级晶石战舰，重三亿九千万吨，由一千五百二十三名炼气期修真者操纵，主控晶脑每秒运算九千万道神念，\u2018计算力\u2019
     * 堪比元婴老怪，可以瞬息镇压一个星球！\u201d...","cover":"/agent/http://image.cmfu.com/books/3439785/3439785
     * .jpg","cat":"科幻","site":"zhuishuvip","banned":0,"latelyFollower":33331,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"49.81"},
     * {"_id":"57085b3e10e3125756685a75","title":"位面电梯","author":"千翠百恋",
     * "shortIntro":"一次偶然，东方玉发现老旧住宿楼的电梯，在特定的时间，能够随机传送到小说，电视剧，电影，乃至动漫的世界。 从此，人生变得精彩了\u2026\u2026
     * V信书友聊天群已经建立，添加V...","cover":"/agent/http://image.cmfu.com/books/1003353824/1003353824
     * .jpg","cat":"无限","site":"qidian","banned":0,"latelyFollower":23703,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"56.53"},{"_id":"56b1c5b918ee9a7b1b374c3b",
     * "title":"都市至尊系统","author":"杯中窥香.",
     * "shortIntro
     * ":"华夏顶尖豪门杨家，唯一男丁竟然是个默默无名的废物？还是个没女人缘的苦逼男？荒唐！至尊系统附体，完成华丽转身。横推高富帅，逆袭白富美，为自己正名！用杨宁的话说，积分能解决的问题，那都不是事！","cover":"/agent/http://118.192.170.16:29998/group1/M00/10/EE/wKgGlVY4UQOEYiIIAAAAAKv4h4Y740.jpg","site":"zhuishuvip","banned":0,"latelyFollower":24418,"latelyFollowerBase":0,"minRetentionRatio":57.82,"retentionRatio":"31"},{"_id":"56a8b79326e87a5359f2eb60","title":"诛仙青云志","author":"萧鼎","shortIntro":"这世间本是没有什么神仙的，但自太古以来，人类眼见周遭世界，诸般奇异之事，电闪雷鸣，狂风暴雨，又有天灾人祸，伤亡无数，哀鸿遍野，决非人力所能为，所能抵挡。遂以为九...","cover":"/agent/http://static.zongheng.com/upload/cover/2015/05/1432263230870.jpg","cat":"仙侠","site":"zongheng","banned":0,"latelyFollower":46110,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"34.35"},{"_id":"53a26e4760b091a831bdc1f3","title":"农家仙田","author":"南山隐士","shortIntro":"神秘空间，偏僻山村。 从繁华都市回归的小农民，开启了他的传奇人生。 荒野庙宇，武林宗派，让平静的山林渐变喧嚣。 李青云说，其实我只是个农二代\u2026\u2026 闲时进山打猎三...","cover":"/agent/http://image.cmfu.com/books/3198729/3198729.jpg","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":28341,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.36"},{"_id":"56f63350fda30e2e40091fff","title":"电影世界大盗","author":"七只跳蚤","shortIntro":"窃玉者诛，窃国者侯，盗窃一方世界又何如！ 穿梭电影世界，盗女主，盗宝物，盗一方气运，无所不盗。 主角们一个个哭喊着： \u201c我的大气运！\u201d \u201c我的大造化！\u201d \u201c我的...","cover":"/agent/http://image.cmfu.com/books/1003290643/1003290643.jpg","cat":"无限","site":"qidian","banned":0,"latelyFollower":25632,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.67"},{"_id":"57089f6faa85a26e604ebdf0","title":"最强反派系统","author":"封七月","shortIntro":"什么是反派？ 是李沉舟拳倾天下，还是上官金虹搅动风云？ 是叶孤城天外飞仙，还是元十三限小箭伤心？ 重生一世，大反派系统加身，苏信可以获得前世武侠世界当中所有的反...","cover":"/agent/http://image.cmfu.com/books/1003123274/1003123274.jpg","cat":"武侠","site":"qidian","banned":0,"latelyFollower":26092,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.63"},{"_id":"528347fedfd459163304adf2","author":"张扬的五月","cover":"/agent/http://image.cmfu.com/books/3025145/3025145.jpg","shortIntro":"因为不能购买游戏头盔，穷凶极恶的李怀林一发狠，自制了一个游戏仓进入了新网游《荣耀之心》，但是没有想到居然把自己的游戏人物卡成了BUG。 别人打怪加经验，他打怪扣...","site":"zhuishuvip","title":"网游之倒行逆施","cat":"网游","banned":0,"latelyFollower":32843,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"48.5"},{"_id":"55f6be45361775607f278575","title":"神门","author":"薪意","shortIntro":"这是一个诡异莫名的世界\u2026\u2026 山，水，石，花，草，树，木，太阳，月亮，星辰\u2026\u2026天地万物都是力量的来源。 这里有着前世所有的经典名著，但却有了一个新的名字《道典》！...","cover":"/agent/http://image.cmfu.com/books/3600493/3600493.jpg","cat":"玄幻","site":"zhuishuvip","banned":0,"latelyFollower":21187,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"50.84"},{"_id":"56244b8dc46bb5ba62e0378c","title":"重生之2006","author":"雨去欲续","shortIntro":"陆恒，一个来自2015年的汽车销售职场精英，重生回到2006年高考前夕。 上辈子，因为他的堕落，导致父亲去世，母亲一夜白发，爱情婚姻一事无成。 这一世陆恒发誓要...","cover":"/agent/http://image.cmfu.com/books/3623463/3623463.jpg","cat":"都市","site":"zhuishuvip","banned":0,"latelyFollower":28635,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"53.36"},{"_id":"571186f0bbb150845b4a22e2","title":"斗鱼之顶级主播","author":"观众老爷","shortIntro":"重生带系统，任务做主播\u2026\u2026奖励顶级能力和意识？带着上帝视角打游戏？送韩服王者账号？别拦我，扶我起来，朕要直播！Q.Q群号557314393","cover":"/agent/http://image.cmfu.com/books/1003371700/1003371700.jpg","cat":"网游","site":"qidian","banned":0,"latelyFollower":24105,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.35"},{"_id":"50b45582aab49e9d04000035","author":"虾米XL","cover":"/agent/http://img.17k.com/images/bookcover/2012/548/2/109666.jpg","shortIntro":"开新书了，开新书了，《诸天万界》，兄弟们投花的投花，收藏的收藏哇！传送门：http://www.17k.com/book/840179.html \u2026\u2026\u2026\u2026\u2026\u2026\u2026...","title":"吞噬苍穹","site":"w17k","cat":"奇幻","banned":0,"latelyFollower":27271,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.73"},{"_id":"5675512c1ef9d9cc31c3496e","title":"秦时明月之大反派系统","author":"七星肥熊","shortIntro":"穿越到了秦时明月的世界，随身还附带了一个坑爹的系统。赢子弋不得不抹了把汗，还有二十多年，秦朝就得玩完。玩完就玩完吧，自己还和秦朝绑定在了一起，谁让他姓赢呢？ 最...","cover":"/agent/http://image.cmfu.com/books/3671477/3671477.jpg","cat":"同人","site":"qidian","banned":0,"latelyFollower":24404,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"52.49"},{"_id":"53eea67525ff25f16ffff03e","title":"医统江山","author":"石章鱼","shortIntro":"前世过劳而死的医生转世大康第一奸臣之家，附身在聋哑十六年的白痴少年身上，究竟是他的幸运还是不幸，上辈子太累，这辈子只想娇妻美眷，儿孙绕膝，舒舒服服地做一个蒙混度...","cover":"/agent/http://image.cmfu.com/books/3218385/3218385.jpg","site":"zhuishuvip","cat":"历史","banned":0,"latelyFollower":21695,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"59.95"},{"_id":"50dbeda1ab38ee5d3d000049","author":"陨落星辰","cover":"/agent/http://gocache.3g.cn/bookimage/bookpic/70/212570/212570_210_280.jpg?v=20140124","shortIntro":"谭笑笑说：我是极品校花！ 彭莹诗说：我是极品杀手！ 尹宝儿说：我是极品宝贝！ 上官无道说：我还是极品公子呢\u2026\u2026叶潇震撼登场：吵什么吵，少爷才是主角，少爷的口号是...","title":"绝品邪少","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":34183,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"42.38"},{"_id":"51e49f261e7439a171000001","author":"三戒大师","cover":"/agent/http://image.cmfu.com/books/3347271/3347271.jpg","shortIntro":"永乐九年，盛世天下，国大民骄，四海来朝！ 值此时，问一声，谁不想当大官人！","title":"大官人","site":"zhuishuvip","cat":"历史","banned":0,"latelyFollower":25208,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56.39"},{"_id":"528f46cac022377564009c49","author":"我本疯狂","cover":"/agent/http://image.cmfu.com/books/3034238/3034238.jpg","shortIntro":"身世如迷的少年，来到繁华都市，本想偷得浮生半日闲，却发现各方势力像飞蛾扑火一般涌现在他的身边\u2026\u2026 家事国事天下事，入世出世到救世，他像一头进山的猛虎，一路所向披...","site":"zhuishuvip","title":"极品狂少","cat":"都市","banned":0,"latelyFollower":28246,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"48.2"},{"_id":"508a2c7ef5cf27d11500000b","author":"心星逍遥","cover":"/agent/http://img.17k.com/images/bookcover/2012/417/2/83528.jpg","shortIntro":"剑尘，江湖中公认的第一高手，一手快剑法出神入化，无人能破，当他与消失百年的绝世高手独孤求败一战之后，身死而亡。 死后，剑尘的灵魂转世来到了一个陌生的世界，并且飞...","title":"混沌剑神","site":"zhuishuvip","cat":"奇幻","banned":0,"latelyFollower":25439,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"50.49"},{"_id":"53b5800ea624a39c19b64b19","title":"重生之神级学霸","author":"志鸟村","shortIntro":"生物系杨锐，一个跟头栽到了1982年，成了一名高考复读生，带着装满脑子的书籍资料，成为了校园的风云人物。","cover":"/agent/http://wap.cmread.com/r/cover_file/9335/401109335/20141109090516/cover180240.jpg","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":19506,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"64.67"},{"_id":"522287d76c578e312c00042e","author":"梦入洪荒","cover":"/agent/http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg","shortIntro":"军人出身的柳擎宇，毅然转业进入官场，成为乡镇镇长，然而上任当天却被完全架空，甚至被晾在办公室无人理睬！ 且看脾气火爆，办事雷厉风行的他，如何凭借着机智头脑和层出...","site":"zhuishuvip","title":"权力巅峰","cat":"都市","banned":0,"latelyFollower":20263,"latelyFollowerBase":2600,"minRetentionRatio":0,"retentionRatio":"64.95"},{"_id":"574e3a72c9e9044b74f9f78b","title":"总裁校花赖上我","author":"鱼人二代","shortIntro":"\u201c招聘帅哥\u2026\u2026\u201d \u201c我是来\u2026\u2026\u201d \u201c你来找工作啊，正好我家总裁缺一个男朋友！\u201d \u201c不是，我是来找校花退婚的\u2026\u2026\u201d 超级大高手楚楠不远万里跑到明城退婚，结果一下火...","cover":"/agent/http://image.cmfu.com/books/1003270018/1003270018.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":26995,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"49.96"},{"_id":"50a04e36ea1ebb6f5b00014c","author":"何常在","cover":"/agent/http://image.cmfu.com/books/1517362/1517362.jpg","shortIntro":"为官者达到举重若轻、点石成金、出神入化的境界，是为官神。 慧眼看红尘，做官如有神。 多了12年前瞻性优势的夏想重新站在大学毕业的路口，回味错过的人生，珍惜眼前的...","title":"官神","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":23752,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"64.02"},{"_id":"576247ec8b76fe640dd1ce19","title":"绝世剑神","author":"无用一书生","shortIntro":"百年前，苍穹大陆第一剑神叶云在\u201c开天\u201d的关键时刻，却遭挚爱红颜无双仙儿背叛，被至高之剑穿心，陨落万界山。　　  百年后，叶云重生到南域，落英帝国，破落战王府一个远近闻名废物少爷的身上。　    重活一世，叶云注定强势崛起，轰轰烈烈，再踏巅峰！　　  \u2026\u2026\u2026\u2026\u2026\u2026　　  六合八荒吾主沉浮，九天十地谁与争锋？　    这一世，我有一剑，可破苍穹！","cover":"/cover/146711129004969","site":"zhuishuvip","banned":0,"latelyFollower":27567,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"47.39"},{"_id":"50908b8fc3aaa79704000006","author":"黑夜de白羊","cover":"/agent/http://img.17k.com/images/bookcover/2012/446/2/89359.jpg","shortIntro":"奇遇、夺宝、杀戮、为了生存，一个小保安周旋在这些三界强者之间，将众美女一一揽入怀中！","title":"我的美女老师","site":"zhuishuvip","cat":"仙侠","banned":0,"latelyFollower":25961,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"44.28"},{"_id":"560127df3b2459ae27b5d5b4","title":"剑主苍穹","author":"乘风御剑","shortIntro":"\u201c我这一生，只出过两剑\u2026\u2026\u201d \u201c第一剑，以十年养剑、十年淬剑、十年藏剑，一剑惊天。\u201d \u201c第二剑，明心、见神、得真、悟道，而后持剑，一往无前。\u201d \u201c今日于你，我将...","cover":"/agent/http://img1.chuangshi.qq.com/upload/cover/20150929/cb_560a0662a00fb.jpg","cat":"玄幻","site":"zhuishuvip","banned":0,"latelyFollower":28436,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"46.79"},{"_id":"50a0520aea1ebb6f5b00018e","author":"愤怒的香蕉","cover":"/agent/http://image.cmfu.com/books/1979049/1979049.jpg","shortIntro":"一个受够了勾心斗角、生死打拼的金融界巨头回到了古代，进入一商贾之家最没地位的赘婿身体后的休闲故事。家国天下事，本已不欲去碰的他，却又如何能避得过了。 \u201c有人曾站...","title":"赘婿","site":"zhuishuvip","cat":"历史","banned":0,"latelyFollower":36014,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"34.45"},{"_id":"57283c257dad441c4bf77d9d","title":"网游之神级机械猎人","author":"逆袭的马里奥","shortIntro":"你还在为稀有宠物和极品装备发愁吗？ 看我带着飞机头，蜘蛛坦克，死神4000，速刷副本，虐杀BOSS！ 什么风骚的走位，什么神级意识，在我的机械炮台带来的金属风暴...","cover":"/agent/http://image.cmfu.com/books/1003422010/1003422010.jpg","cat":"网游","site":"qidian","banned":0,"latelyFollower":20947,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"60.87"},{"_id":"56a76c2ffb6f62427e387047","title":"武侠世界自由行","author":"大江入海","shortIntro":"身上有了一个可以穿越已知武侠世界的门户，于是杨易鲜衣怒马，仗剑走天下！ 让我们重温那些年一起追过的小说\u2026\u2026 扣群：531959678","cover":"/agent/http://image.cmfu.com/books/3682719/3682719.jpg","cat":"武侠","site":"qidian","banned":0,"latelyFollower":21983,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58.77"},{"_id":"50bef0732033d09b2f00007f","author":"唐家三少","cover":"/agent/http://image.cmfu.com/books/1115277/1115277.jpg","shortIntro":"唐门外门弟子唐三，因偷学内门绝学为唐门所不容，跳崖明志时却发现没有死，反而以另外一个身份来到了另一个世界，一个属于武魂的世界，名叫斗罗大陆。这里没有魔法，没有斗...","title":"斗罗大陆","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":30416,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"43.32"},{"_id":"5090718cc3aaa79704000001","author":"风凌天下","cover":"/agent/http://wfqqreader.3g.qq.com/cover/524/462524/t7_462524.jpg","shortIntro":"一笑风雷震，一怒沧海寒；一手破苍穹，一剑舞长天！　　一人一剑，傲世九重天！","title":"傲世九重天","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":25614,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.1"},{"_id":"53e8adbab371a2e830790d67","title":"都市逍遥修神","author":"血色红泪","shortIntro":"退役兵王曾逸，英雄救美身负重伤，未料被一位神人救活，得到一部逆天绝世功法，一颗神秘强大的珠子，从此校园、都市任我纵横，强大的家族，古老的门派尽数踩在脚下，一段段...","cover":"/agent/http://img4.readnovel.com/incoming/book/0/2958/202958_fm.jpg","site":"readnovel","cat":"都市","banned":0,"latelyFollower":23119,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"51.98"},{"_id":"5684b3bbed87bb472ed630e7","title":"重生之我为书狂","author":"天下第一白","shortIntro":"重生回到与地球相似的平行世界。 他以小说\u201c坏蛋是怎样练成的\u201d，开创了小白文热潮。 他写的射雕三部曲，直接将旧新武侠推向了新派武侠高峰。 他写的白蛇传，梁山伯与祝...","cover":"/agent/http://image.cmfu.com/books/3679201/3679201.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":18164,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"65.34"},{"_id":"523a89f3d87e739d3a005f30","author":"唐家三少","cover":"/agent/http://image.cmfu.com/books/2517792/2517792.jpg","shortIntro":"大陆传奇，一战成名；凤凰圣女，风火流星神界刀法；双升融合，金阳蓝月，雷霆之怒，这里没有魔法，没有斗气，没有武术，却有武魂。唐门创立万年之后的斗罗大陆上，唐门式微...","site":"zhuishuvip","title":"斗罗大陆II绝世唐门","cat":"玄幻","banned":0,"latelyFollower":25033,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"52.25"},{"_id":"572c02708ced279757fa0794","title":"无敌英雄系统","author":"忘川三途","shortIntro":"穿越成了一个被冤死的废材倒霉蛋，宁渊感到亚历山大，不过好在他有一个无敌英雄系统在手，能召唤英雄之魂附体。 剑神传人？不好意思，天剑无名，独孤求败，西门吹雪，我这...","cover":"/agent/http://image.cmfu.com/books/1003420092/1003420092.jpg","cat":"玄幻","site":"qidian","banned":0,"latelyFollower":21304,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.84"},{"_id":"5209f9bad94c53721b0043b2","author":"盘古","cover":"/agent/http://gocache.3g.cn/bookimage/bookpic/93/239193/239193_210_280.jpg?v=20140124","shortIntro":"臆想了一下女神，居然穿越了，大学生陈九意外来到了乾坤大陆，看着这武技满天、魔法纵横的世界，他竖了竖中指，看到了自己意外买来的九龙戒\u2026\u2026 修炼等级：淬体九重，开天...","site":"zhuishuvip","title":"九龙至尊","cat":"玄幻","banned":0,"latelyFollower":28860,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"39.85"},{"_id":"540040403f98947316a7f06c","title":"巫师之旅","author":"一行白鹭上青天","shortIntro":"给我无尽的知识，我便以自身为支点，撬动无尽世界。 故事讲述的是一个名为格林的巫师，依靠自己的智慧和运气，学习自己独特的巫师知识，游历异域世界，参与不同文明之间战...","cover":"/agent/http://image.cmfu.com/books/3268922/3268922.jpg","site":"zhuishuvip","cat":"奇幻","banned":0,"latelyFollower":20607,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.79"},{"_id":"55e6b44f0ba6b0f81c2d71da","title":"超时空垃圾站","author":"小城古道","shortIntro":"苏璟大学毕业后，处处碰壁，心灰意冷，回老家调整心情。却发现自家后院，成了超时空垃圾站，每天都有大量垃圾汇入其中，有的来自神墓、斗破苍穹、盘龙等小说时空；有的来自...","cover":"/agent/http://image.cmfu.com/books/3594144/3594144.jpg","cat":"都市","site":"zhuishuvip","banned":0,"latelyFollower":21135,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"53.23"},{"_id":"51d12c844a6165603e00002e","author":"逆苍天","cover":"/agent/http://image.cmfu.com/books/2817301/2817301.jpg","shortIntro":"三万年前，自称为\u201c神\u201d的搏天族入侵灵域，百族奋起反抗，最终惨败，人族率先投诚，百族随后相继臣服。 之后万年，所有种族皆被搏天族奴役，被严酷对待，生活在恐怖阴影中...","title":"灵域","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":28517,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"41.56"},{"_id":"56a83d56ff1952b30b4e1fe3","title":"文娱教父","author":"我最白","shortIntro":"这是一个拥有超越百亿人口的平行大世界。 他的第一部玄幻网文就问鼎至高，轰动网文界。 他是龙国科幻界最后的希望，也是龙国漫画界最漫不经心的王者！ 他是言情小王子，...","cover":"/agent/http://image.cmfu.com/books/3680829/3680829.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":20707,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58.24"},{"_id":"520ec9b8301c60951b0036bf","author":"神见","cover":"/agent/http://image.cmfu.com/books/2866988/2866988.jpg","shortIntro":"强者，永远都是寂寞的，只有战胜寂寞，才能无敌天下！ 地球少林世家亲传弟子黄小龙携带华夏无上武学秘典《易筋经》莫名穿越到了武魂世界。 武魂世界，体内拥有武魂才能修...","site":"zhuishuvip","title":"无敌天下","cat":"玄幻","banned":0,"latelyFollower":20989,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56.26"},{"_id":"56fe001bd2a6eb1e7ecac12a","title":"逍遥小镇长","author":"全金属弹壳","shortIntro":"要工作，要娱乐，但更要逍遥安逸的生活。 高山飞雪，碧水扬帆，放马草原还能探秘深林，我的地盘风光秀美。 教堂，学校，警察局；医院，社区，俱乐部，还有一个小政府要运...","cover":"/agent/http://image.cmfu.com/books/1003290088/1003290088.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":19264,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"60.39"},{"_id":"545378240cab2f010ccaa8d2","title":"无限之配角的逆袭","author":"落花独立","shortIntro":"每个故事都有一个主角，他们无不是天地间的宠儿，好东西是他们的，好妹子也是他们的\u2026\u2026每个世界都有N个配角，他们无不是为了衬托主角而存在的，面对主角的强势，他们只能...","cover":"/agent/http://image.cmfu.com/books/3315254/3315254.jpg","site":"zhuishuvip","cat":"网游","banned":0,"latelyFollower":23380,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"47.82"},{"_id":"56c03ad42bb7914844388cc9","title":"非人类基因统合体","author":"魔性沧月","shortIntro":"阴冷无情的巫妖，兴风作浪的巨蛟，能量无限的尾兽，无限进化的虫族，脑域开发的外星种族。 只要不是人类，蓝牧表示什么怪物没变过？不管是神话传说，影视游戏，小说动漫，...","cover":"/agent/http://image.cmfu.com/books/3685867/3685867.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":25465,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"45.45"},{"_id":"55c3f78ef4d2352875db2e65","title":"武侠仙侠世界里的道人","author":"天帝大人","shortIntro":"武侠仙侠世界里的道人，在缓缓地穿越途中领略道的真意。射雕世界里，他用排云掌与洪七公对决；大唐世界中，他指着宁道奇说：\u201c这道门第一高手的位置你得让让了\u201d。。。。。...","cover":"/agent/http://image.cmfu.com/books/3570896/3570896.jpg","cat":"武侠","site":"zhuishuvip","banned":0,"latelyFollower":18760,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"64.32"},{"_id":"50ca7bf76221b0e226000001","author":"失落叶","cover":"/agent/http://img.17k.com/images/bookcover/2012/252/1/50551.jpg","shortIntro":"我本是一个不起眼的玩家，然而真正的高手与菜鸟到底距离有多远？ 当有一天，两个漂亮女孩一起问我：你愿意当一个高手，纵横天下吗？ 我说：我愿意！ 于是乎，我悟了，便...","title":"网游之纵横天下","site":"zhuishuvip","cat":"网游","banned":0,"latelyFollower":22332,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"55.27"},{"_id":"508de54055e53b9a1a000027","author":"梦入洪荒","cover":"/agent/http://img.17k.com/images/bookcover/2012/320/1/64141.jpg","shortIntro":"刘飞大学毕业生步入官场，因为撞破上司和女秘书的好事被发配到下面的机关，接着便是一连串的打击压制，然而，逆境之中的刘飞却没有想到，一直追求他的美女徐娇娇的老爸竟然...","title":"官途","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":17613,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"66.27"},{"_id":"50a0559cea1ebb6f5b0001af","author":"莫默","cover":"/agent/http://image.cmfu.com/books/1844713/1844713.jpg","shortIntro":"宁遇阎罗王，莫惹唐门郎！ 行走在黑暗之中，卑鄙却不失本性，无耻却不丢风度的勾魂使者！ 一个唐门弟子重生到一个全是女子组成的门派之后的故事。 老天顺我老天昌，老天...","title":"唐门高手在异世","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":25053,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"53.99"},{"_id":"57858177386d1da60d754c7b","title":"起点直播之玄幻世界大冒险","author":"我丑到灵魂深处","shortIntro":"一个扑街写手穿越到东方玄幻世界的老套故事，不过他可以直播\u2026\u2026","cover":"/agent/http://image.cmfu.com/books/1003588827/1003588827.jpg","cat":"玄幻","site":"qidian","banned":0,"latelyFollower":22617,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"55.09"},{"_id":"56e52392b5be07d0077465ed","title":"黑暗主宰","author":"零下九十度","shortIntro":"在遥远的宇宙最深处，有着一个神秘而又古老的职业，它均衡万物星辰，制约光明和黑暗，维护着宇宙的法则，它被称之为：制裁者！然而，当光明失去力量，当法则黯淡无光，黑暗...","cover":"/agent/http://image.cmfu.com/books/1002014560/1002014560.jpg","cat":"玄幻","site":"qidian","banned":0,"latelyFollower":20230,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58.97"},{"_id":"53e7076097baba6b4e443a05","title":"九仙图","author":"秋晨","shortIntro":"被天道所不容，降下无上封印的少年偶得一幅画。 画里住着九位自称为仙人的灵魂。 于是，一个逆天强者的逆天传说开始了。","cover":"/agent/http://img1.chuangshi.qq.com/upload/cover/20140806/cb_53e1808cc565b.jpg","site":"zhuishuvip","cat":"仙侠","banned":0,"latelyFollower":24680,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"47.66"},{"_id":"5725e3f6f32f1be80cce2dca","title":"文娱缔造者","author":"别人家的小猫咪","shortIntro":"要么孤独，要么庸俗。 ps：我是来矫正文娱写法的，以上。","cover":"/agent/http://image.cmfu.com/books/1003453695/1003453695.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":17041,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"61.01"},{"_id":"55a9d0d0a0eff3e8518e87c8","title":"从仙侠世界归来","author":"发狂的妖魔","shortIntro":"五年前，萧凡突然神秘失踪，生不见人，死不见尸，没人知道他其实是去了一个广袤无边，仙神林立，妖魔横行的仙侠世界。 五年后，萧凡已经在那个仙侠世界度过了漫长的五千年...","cover":"/agent/http://image.cmfu.com/books/3544421/3544421.jpg","cat":"都市","site":"zhuishuvip","banned":0,"latelyFollower":23801,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"43.97"}]
     * created : 2015-02-06T02:57:22.000Z
     * id : 54d42d92321052167dfb75e3
     */

    private RankingBean ranking;
    /**
     * ranking : {"_id":"54d42d92321052167dfb75e3","updated":"2016-08-14T21:20:21.090Z",
     * "title":"追书最热榜 Top100","tag":"zhuishuLatelyFollowerMale",
     * "cover":"/ranking-cover/142319144267827","__v":509,"monthRank":"564d820bc319238a644fb408",
     * "totalRank":"564d8494fe996c25652644d2","isSub":false,"collapse":false,"new":true,
     * "gender":"male","priority":250,"books":[{"_id":"53855a750ac0b3a41e00c7e6","title":"择天记",
     * "author":"猫腻","shortIntro":"太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。
     * 数千年后，十四岁的少年孤儿陈长生，为治病...","cover":"/agent/http://image.cmfu.com/books/3347595/3347595.jpg",
     * "site":"qidian","cat":"玄幻","banned":0,"latelyFollower":182257,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.48"},{"_id":"5091fbcf8d834c0f190000cd",
     * "author":"鱼人二代","cover":"/agent/http://image.cmfu.com/books/1931432/1931432.jpg",
     * "shortIntro":"一个大山里走出来的绝世高手，一块能预知未来的神秘玉佩\u2026\u2026
     * 林逸是一名普通学生，不过，他还身负另外一个重任，那就是追校花！而且还是奉校花老爸之命！ 虽然林逸很不想...","title":"校花的贴身高手","site":"qidian",
     * "cat":"都市","banned":0,"latelyFollower":153917,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.29"},{"_id":"5373898f1032be0155019e73",
     * "title":"儒道至圣","author":"永恒之火","shortIntro":"这是一个读书人掌握天地之力的世界。 才气在身，诗可杀敌，词能灭军，文章安天下。
     * 秀才提笔，纸上谈兵；举人杀敌，出口成章；进士一怒，唇枪舌剑。 圣人驾临，口诛笔伐...","cover":"/agent/http://image.cmfu
     * .com/books/3173393/3173393.jpg","site":"zhuishuvip","cat":"玄幻","banned":0,
     * "latelyFollower":118695,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"62
     * .7"},{"_id":"526e8e3e7cfc087140004df7","author":"太一生水","cover":"/agent/http://image.cmfu
     * .com/books/3347382/3347382.jpg",
     * "shortIntro
     * ":"十大封号武帝之一，绝世古飞扬在天荡山脉陨落，于十五年后转世重生，化为天水国公子李云霄，开启了一场与当世无数天才相争锋的逆天之旅。武道九重，十方神境，从此整个世界...",
     * "site":"zhuishuvip","title":"万古至尊","cat":"玄幻","banned":0,"latelyFollower":166088,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"42.89"},
     * {"_id":"50874828abf1ced53c00003b","author":"萧鼎","cover":"/agent/http://wap.cmread
     * .com/r/cover_file/7182/390607182/20131226142254/cover180240.jpg",
     * "shortIntro":"普通少年张小凡为救红颜，手持烧火棍与整个世界为敌，何为正，何为邪，可笑万物如刍狗，谁为覆雨谁翻云！","title":"诛仙",
     * "site":"zhuishuvip","cat":"仙侠","banned":0,"latelyFollower":152544,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"42.17"},{"_id":"559b51abadcc20911c4a3b16",
     * "title":"万古神帝","author":"飞天鱼","shortIntro":"八百年前，明帝之子张若尘，被他的未婚妻池瑶公主杀死，一代天骄，就此陨落。
     * 八百年后，张若尘重新活了过来，却发现曾经杀死他的未婚妻， 已经统一昆仑界，开辟出第一中...","cover":"/agent/http://img1.chuangshi.qq
     * .com/upload/cover/20150710/cb_559fc855bd0b4.jpg","cat":"玄幻","site":"zhuishuvip",
     * "banned":0,"latelyFollower":99465,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"56.25"},{"_id":"54831bc7cc6633040d8a6256","title":"天域苍穹",
     * "author":"风凌天下","shortIntro":"笑尽天下英雄，宇内我为君主！
     * 天域君主叶笑，以一人之力战三大宗门，以一敌万，大杀四方，但终究寡不敌众。一朝醒来，发现重生世俗界，更得到极品灵宝天晶灵髓，无尽空间...",
     * "cover":"/agent/http://img1.chuangshi.qq.com/upload/cover/20150916/cb_55f918fb5a617.jpg",
     * "site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":79085,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"50.92"},{"_id":"56e174ef0dba234122588ef0",
     * "title":"英雄联盟之全民解说","author":"我的长枪依在",
     * "shortIntro":"职业选手王磊重生了，他反思过去，决定分享自己在联盟中得到的快乐，于是他成为了一名解说。
     * 平时打打直播，没事调戏调戏美女主播，和女房客搞搞暧昧，教育教育文化女青年...","cover":"/agent/http://image.cmfu
     * .com/books/1001776467/1001776467.jpg","cat":"网游","site":"qidian","banned":0,
     * "latelyFollower":67316,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58
     * .97"},{"_id":"542a5838a5ae10f815039a7f","title":"逆天邪神","author":"火星引力",
     * "shortIntro":"掌天毒之珠，承邪神之血，修逆天之力，一代邪神，君临天下！","cover":"/agent/http://static.zongheng
     * .com/upload/cover/2014/11/1416425191645.jpg","site":"zhuishuvip","cat":"玄幻","banned":0,
     * "latelyFollower":110678,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"36
     * .71"},{"_id":"5589c5d1cc6b9d3d794cbdb9","title":"女神的近身护卫","author":"肥茄子",
     * "shortIntro":"一次意外，萧正被大众眼中的冰山女BOSS倒追逼婚。并顺利成为号称女儿国的新奥公司一员，从此展开万花丛中过的精彩人生。
     * 冰山女神、暴龙霸王花、温柔女上司、俏皮学生...","cover":"/agent/http://img.17k
     * .com/images/bookcover/2015/6273/31/1254615-1436894038000.jpg","cat":"都市",
     * "site":"zhuishuvip","banned":0,"latelyFollower":58492,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"64.19"},{"_id":"50c541538380e4f81500001a",
     * "author":"风凌天下","cover":"/agent/http://image.cmfu.com/books/1524659/1524659.jpg",
     * "shortIntro":"世间毁誉，世人冷眼，与我何干？我自淡然一笑； 以吾本性，快意恩仇，以吾本心，遨游世间，我命由我不由天！
     * 一代牛人穿越异界，看其如何踏上异世巅峰，成为一代邪君！","title":"异世邪君","site":"qidian","cat":"玄幻","banned":0,
     * "latelyFollower":57151,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"62
     * .78"},{"_id":"56e181a1238b4ed376f3dd04","title":"超级仙学院","author":"尺长寸短",
     * "shortIntro":"都市中有这样一所学院，它不教人数理化，而是教人武功秘法，神功异能。学院的老师也不是一般的老师，而是来源于各个武侠小说以及影视作品里面的响当当人物。
     * 岳不群：\u201c今...","cover":"/agent/http://image.cmfu.com/books/1001667168/1001667168.jpg",
     * "cat":"都市","site":"qidian","banned":0,"latelyFollower":49994,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"63.72"},{"_id":"5790bc09e72826f52571f63c",
     * "title":"美食供应商","author":"会做菜的猫","shortIntro":"\u201c在遥远的东方，存在着一个数次拒绝了米其林三星评价的奇怪小店。
     * 那里价格昂贵，一碗配汤蛋炒饭288RMB，哦忘了还有一碟泡菜，但就算是这样也有很多人排队等候。 ...","cover":"/cover/147122961340677",
     * "cat":"都市","site":"qidian","banned":0,"latelyFollower":42141,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"61.98"},{"_id":"56eaf7fe4fb43b1203fab269",
     * "title":"超级怪兽工厂","author":"匣中藏剑","shortIntro":"我们这个工厂，不讲科技，它讲技能，讲工具。
     * 大锤（卓越品质）属性：物体硬度减少70%，5%几率发动万吨水压机冲击波。 吼吼～我是怪兽，我们是混蛋，我们有铜墙铁壁...",
     * "cover":"/agent/http://image.cmfu.com/books/1002959239/1002959239.jpg","cat":"都市",
     * "site":"zhuishuvip","banned":0,"latelyFollower":61649,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"55.14"},{"_id":"5121d75e1b7e32db54000011",
     * "author":"南希北庆","cover":"/agent/http://image.cmfu.com/books/2499752/2499752.jpg",
     * "shortIntro":"身为一家超五星级酒店首席大厨的李奇，因为喝了点小酒，竟然奇迹般的穿越到了北宋末年。
     * 来到这个无亲无故的陌生世界，无奈之下，李奇只好抄起了老本行，在汴京一家即将贱...","title":"北宋小厨师","site":"zhuishuvip","cat":"历史",
     * "banned":0,"latelyFollower":48965,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"61.69"},{"_id":"57416eeb95895b3b4ae06f8e","title":"史上最强店主",
     * "author":"南极烈日","shortIntro":"商通万界，让所有人给我打工。 周阳意外获得万界商铺系统，交易万千位面。
     * \u201c我曾举行一次小型拍卖会，来到的皇帝就有一百多位，听说还有两个叫李世民的皇帝。\u201d \u201c我曾...",
     * "cover":"/agent/http://image.cmfu.com/books/1003504656/1003504656.jpg","cat":"无限",
     * "site":"qidian","banned":0,"latelyFollower":44001,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"65.08"},{"_id":"518c93dc5f5a2f4e4300000a",
     * "author":"梁少","cover":"/agent/http://static.zongheng
     * .com/upload/cover/2014/05/1399893353629.jpg",
     * "shortIntro
     * ":"文武双科状元萧阳意外穿越到现代都市，以【伴读小书童】以及【女生寝室门卫】双重身份进入大学校园，开始香艳惑色的护花之旅。力撰反穿越第一精品，继《唐伯虎现代寻芳记》...",
     * "title":"护花状元在现代","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":45942,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"63.84"},
     * {"_id":"531169b3173bfacb4904ca67","author":"耳根","cover":"/agent/http://image.cmfu
     * .com/books/3106580/3106580.jpg","shortIntro":"我若要有 天不可无 我若要无 天不许有！ 这是一个起始于第八山与第九山之间的故事
     * 一个\u201c我命如妖欲封天\u201d的世界！ 这是耳根继《仙逆》《求魔》后，创作的第三部长篇...","title":"我欲封天","site":"zhuishuvip",
     * "cat":"仙侠","banned":0,"latelyFollower":54412,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"49.83"},{"_id":"56c44b82121f2cb228745ef2","title":"灭世魔帝",
     * "author":"沉默的糕点","shortIntro":"大三男生兰陵被流星砸中，穿越异世，被索氏家族女武士所救。
     * 仿佛是天意，他和天水城少主索伦长得很像，而这个纨绔已经死于非命。 为了报救命之恩，主角答应冒充少主索伦...","cover":"/agent/http://image.cmfu
     * .com/books/3686556/3686556.jpg","cat":"玄幻","site":"qidian","banned":0,
     * "latelyFollower":41542,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"62
     * .65"},{"_id":"52385133777d4d8455002666","author":"我是墨水","cover":"/agent/http://image.cmfu
     * .com/books/2939492/2939492.jpg","shortIntro":"我若执魔，天地无仙！我若执天，天地无魔！
     * 这是一个起始于雨之仙界的故事，一个\u2018我命如蝶斩轮回\u2019的世界！ 回首凡尘如烟，一笑淡了明月...只为她，横行雨界！ 天道...",
     * "site":"zhuishuvip","title":"执魔","cat":"仙侠","banned":0,"latelyFollower":62884,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"30.21"},
     * {"_id":"52eb685430174377060003d1","author":"颓废的烟121","cover":"/agent/http://images.zhulang
     * .com/book_cover/image/27/9/270971.jpg","shortIntro":"带着异世界的吞天武灵，废物少爷绝地逆袭，一跃成为震惊大陆的武学天才！
     * 强大的武技信手拈来，强横的敌人踩在脚下。 神秘的家族，未知的领域，一切的精彩，尽在武灵天下...","title":"武灵天下","site":"zhuishuvip",
     * "cat":"玄幻","banned":0,"latelyFollower":40669,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"62.87"},{"_id":"5717393d79eeca5e2854566a","title":"本座东方不败",
     * "author":"星辰雨","shortIntro":"武侠大世界中，秦、隋、宋、明四国共立，一个人穿越成东方不败的故事，且看东方不败败尽天下。 主角是个很正常的男人。",
     * "cover":"/agent/http://image.cmfu.com/books/1003389975/1003389975.jpg","cat":"武侠",
     * "site":"qidian","banned":0,"latelyFollower":33966,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"65.39"},{"_id":"5108725a7a2138ed06000001",
     * "author":"蛊真人","cover":"/agent/http://image.cmfu.com/books/2527417/2527417.jpg",
     * "shortIntro":"人是万物之灵，蛊是天地真精。 三观不正，魔头重生。 昔日旧梦，同名新作。 一个穿越者不断重生的故事。 一个养蛊、炼蛊、用蛊的奇特世界。
     * 春秋蝉、月光蛊、酒虫、一...","title":"蛊真人","site":"zhuishuvip","cat":"仙侠","banned":0,
     * "latelyFollower":39623,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56
     * .06"},{"_id":"5762847a36a7b3d03511b3b8","title":"不朽凡人","author":"鹅是老五",
     * "shortIntro":"我，只有凡根，一介凡人！ 我，叫莫无忌！ 我，要不朽！","cover":"/agent/http://image.cmfu
     * .com/books/1003307568/1003307568.jpg","cat":"仙侠","site":"qidian","banned":0,
     * "latelyFollower":37478,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58
     * .99"},{"_id":"5662ca66b8cb23ce21ba25f2","title":"大魏宫廷","author":"贱宗首席弟子",
     * "shortIntro":"生作大魏皇子， 愿当盛世闲王。 志在偎红倚翠犬马声色， 胸怀家国百姓社稷安危。 若兄贤，若弟明， 尔为人王吾偷闲。 若尔不能使国强， 吾来登基做帝王！
     * \u2014\u2014\u2014\u2014...","cover":"/agent/http://image.cmfu.com/books/3662715/3662715
     * .jpg","cat":"历史","site":"qidian","banned":0,"latelyFollower":34816,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"54.5"},{"_id":"56e6a5278181622047dc5a11",
     * "title":"英雄联盟之灾变时代","author":"会说话的肘子","shortIntro":"吕尘带着来自地球的一身LOL技巧穿了！
     * 同样是打英雄联盟，只不过这次，他要变超人！","cover":"/agent/http://image.cmfu.com/books/1002399247/1002399247
     * .jpg","cat":"网游","site":"qidian","banned":0,"latelyFollower":42315,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"51.69"},{"_id":"57297937661cbd303df9a39b",
     * "title":"龙皇武神","author":"步征","shortIntro":"佛道魔同修！体内蕴真龙！ 拥有太古龙神血脉，获得上古三皇传承！ 斩妖邪，除恶魔，一路逆天修行！
     * 我从修真世界而来，我将乘龙飞仙而去！","cover":"/agent/http://image.cmfu.com/books/2510964/2510964.jpg",
     * "cat":"都市","site":"qidian","banned":0,"latelyFollower":39780,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.67"},{"_id":"5091fcda8d834c0f190000dc",
     * "author":"鱼人二代","cover":"/agent/http://image.cmfu.com/books/1042235/1042235.jpg",
     * "shortIntro":"杨明是一名普通的学生，某一天，他收到一份礼物，一只神奇的眼镜，从此生活变得丰富多彩。","title":"很纯很暧昧",
     * "site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":38033,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"54.27"},{"_id":"508a2b97f5cf27d115000006",
     * "author":"只是小虾米","cover":"/agent/http://img.17k.com/images/bookcover/2012/962/4/192453
     * .jpg","shortIntro":"沉眠三载，不知岁月流江。 废材？不是，是天才！ 帝脉天赐，指天斥神张扬。 废体？不是，是神体！ 天下为敌，为伊孤战八方。
     * 男人的尊严，需自己找回！ 武逆修神，古...","title":"武逆","site":"w17k","cat":"奇幻","banned":0,
     * "latelyFollower":35908,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56
     * .96"},{"_id":"52159104c8f1be8010000015","author":"寂寞的舞者","cover":"/agent/http://img.17k
     * .com/images/bookcover/2014/2086/10/417239-1404360621000.jpg",
     * "shortIntro":"\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014 兵王之王，方为兵皇，兵皇入世，无限嚣张！
     * 一代兵皇，为身世回归都市，化身玩美房东，成为美女房客的贴身兵皇，狂啃窝边草！ 萧风扬言：谁敢吃我的...","site":"zhuishuvip","title":"贴身兵皇",
     * "cat":"都市","banned":0,"latelyFollower":35809,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"57.45"},{"_id":"571087e3430ce3417cdd079d","title":"帝临武侠",
     * "author":"隔壁老王01","shortIntro":"一颗紫色圆珠，一段不朽传奇，一个穿梭诸天位面的人。
     * 冯睿第一次穿越异位面，是一个修真位面，费尽千辛万苦，终于拜入七大门派之一清虚门，但因为在藏经阁偷盗功法，差点...","cover":"/agent/http://image
     * .cmfu.com/books/1003374935/1003374935.jpg","cat":"无限","site":"qidian","banned":0,
     * "latelyFollower":24521,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"69
     * .96"},{"_id":"55c48abcb905e5930c84ab61","title":"我在末世有套房","author":"晨星LL",
     * "shortIntro":"核战之后的末世，到处都是一片狼藉。
     * 如果你一不小心活了下来，那么接下来你将不得不面对饥饿与疾病的恐惧，一到夜晚就会发狂的丧尸，还有那些因辐射而变得奇形怪状的异种...",
     * "cover":"/agent/http://image.cmfu.com/books/3569244/3569244.jpg","cat":"末世",
     * "site":"zhuishuvip","banned":0,"latelyFollower":38066,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"51.13"},{"_id":"566e4d347e62feae50d41c52",
     * "title":"仙界网络直播间","author":"38大虾","shortIntro":"作为一个网络男主播，张小东突然发现自己直播间突然冒出了一批土神仙！
     * 一段郭德纲十年前的相声，\u201c好！！再来一段！\u201d财神赵公明哥哥这个冤大头扔下了一片元宝打赏。 \u201c...",
     * "cover":"/agent/http://image.cmfu.com/books/3662021/3662021.jpg","cat":"都市",
     * "site":"qidian","banned":0,"latelyFollower":34738,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"49.42"},{"_id":"56cd10bb32dc12e56764cf25",
     * "title":"天骄战纪","author":"萧瑾瑜",
     * "shortIntro
     * ":"苍图大陆，茫茫无垠。有古老宗门盘踞十方古域，有世外仙道屹立青冥之上，有太古妖神统领黑暗大渊，缔造了不知多少的辉煌篇章。就在这大千世界中，一个名叫林寻的少年，独自...",
     * "cover":"/agent/http://static.zongheng.com/upload/cover/2016/02/1456282051781.png",
     * "cat":"玄幻","site":"zongheng","banned":0,"latelyFollower":26314,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"64.01"},{"_id":"5761f7c1e8b588f50974f84b",
     * "title":"懒散初唐","author":"北冥老鱼",
     * "shortIntro
     * ":"武德五年，大唐初立，李渊呆在美女如云的后宫之中，忙着享受自己得来不易的胜利果实，李建成忙着稳固自己的太子之位，李世民忙着觊觎大哥的位子，武将们忙着打仗，文臣们忙...",
     * "cover":"/agent/http://image.cmfu.com/books/1003565025/1003565025.jpg","cat":"历史",
     * "site":"qidian","banned":0,"latelyFollower":29830,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"59.31"},{"_id":"508662b8d7a545903b000027",
     * "author":"忘语","cover":"/agent/http://image.cmfu.com/books/107580/107580.jpg",
     * "shortIntro":"一个普通山村小子，偶然下进入到当地江湖小门派，成了一名记名弟子。他以这样身份，如何在门派中立足,
     * 如何以平庸的资质进入到修仙者的行列，从而笑傲三界之中！","title":"凡人修仙传","site":"zhuishuvip","cat":"仙侠","banned":0,
     * "latelyFollower":33430,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"55
     * .44"},{"_id":"50c537838380e4f815000005","author":"王小蛮","cover":"/agent/http://wfqqreader
     * .3g.qq.com/cover/89/120089/t7_120089.jpg",
     * "shortIntro
     * ":"穿越异界附身世家落魄少爷，脑海带着本时灵时不灵的残破符法，心眼里藏着点小心思。且看他在残酷修真界中左右逢源，誓做纨绔的修仙生活。《盖世仙尊》已发！书友群群号58...",
     * "title":"修仙狂徒","site":"chuangshi","cat":"仙侠","banned":0,"latelyFollower":31135,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"60.7"},
     * {"_id":"53116126173bfacb49049b41","author":"犁天","cover":"/agent/http://image.cmfu
     * .com/books/3366482/3366482.jpg","shortIntro":"天帝之子江尘，转生在一个被人欺凌的诸侯少年身上，从此踏上一段轰杀各种天才的逆袭之路。
     * 在江尘面前，谁也没资格自称天才，因为，没有哪一个天才，能比天帝之子更懂天。...","title":"三界独尊","site":"zhuishuvip","cat":"玄幻",
     * "banned":0,"latelyFollower":33924,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"56.61"},{"_id":"50c54e158380e4f81500002e","author":"安山狐狸",
     * "cover":"/agent/http://img.17k.com/images/bookcover/2012/448/2/89718.jpg",
     * "shortIntro":"受尽别人白眼的季枫，在人生的低谷之际，得到了未来的科技产品，拥有了神奇的能力，从此他的人生变得不再平凡！
     * 透视功能，赌石无往不利。未来科技，让季枫成就商业帝国，...","title":"校园全能高手","site":"zhuishuvip","cat":"都市",
     * "banned":0,"latelyFollower":31674,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"57.19"},{"_id":"578b13f28ae3b47c3ce95bc9","title":"神级幸运星",
     * "author":"辰机唐红豆","shortIntro":"无意中获得了一枚运气骰子，还穿越到娱乐业匮乏的平行位面，王昊这下牛逼了。
     * 运气骰子的六个面分别是\u201c非常倒霉，倒霉，普通，好运，非常好运，神级好运\u201d，每天零点刷新...","cover":"/agent/http://qidian
     * .qpic.cn/qdbimg/349573/1003656831/180","cat":"都市","site":"qidian","banned":0,
     * "latelyFollower":38629,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"40
     * .66"},{"_id":"56d445d412e8175a3fefa45a","title":"穿越宁采臣","author":"西瓜有皮不好吃",
     * "shortIntro":"宁飞做了一个梦，梦见自己身处兰若寺中，有一个叫做聂小倩的女鬼，有一个叫做燕赤霞的大胡子、还有
     * 一棵千年树妖......然后，宁飞醒了过来，却发现自己成了宁采臣，...","cover":"/agent/http://image.cmfu
     * .com/books/1001756434/1001756434.jpg","cat":"仙侠","site":"qidian","banned":0,
     * "latelyFollower":25441,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"63
     * .76"},{"_id":"561a1133533f63f91ba0dcca","title":"冠军之心","author":"林海听涛",
     * "shortIntro":"\u201c周先生，我们注意到您小时候曾经涉猎过篮球、乒乓球、羽毛球、围棋、跳棋、五子棋、中国象棋、国际象棋、书法绘画、吉他音乐\u2026\u2026
     * 等多方面的领域，那这是否意味着就算您当...","cover":"/agent/http://image.cmfu.com/books/3623405/3623405.jpg",
     * "cat":"体育","site":"zhuishuvip","banned":0,"latelyFollower":26860,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"63.55"},{"_id":"50ce6b1263d90df45a000001",
     * "author":"我吃西红柿","cover":"/agent/http://image.cmfu.com/books/2502372/2502372.jpg",
     * "shortIntro":"纪宁死后来到阴曹地府，经判官审前生判来世，投胎到了部族纪氏。 这里， 有夸父逐日\u2026\u2026 有后羿射金乌\u2026\u2026
     * 更有为了逍遥长生，历三灾九劫，纵死无悔的无数修仙者\u2026\u2026 ...","title":"莽荒纪","site":"zhuishuvip","cat":"仙侠",
     * "banned":0,"latelyFollower":33897,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"53.87"},{"_id":"512199de221b4d2f30000100","author":"风青阳",
     * "cover":"/agent/http://img.17k.com/images/bookcover/2015/1955/9/391013-1423808477000.jpg",
     * "shortIntro":"上古世纪，天地初开，龙祭大陆乃神龙一族之天下，时至今日神龙灭绝殆尽，神秘浩劫再度降临，天下苍生岌岌可危！
     * 少年龙辰，背负父辈荣耀使命，追逐神龙灭绝谜团。机缘巧合...","title":"龙血战神","site":"zhuishuvip","cat":"奇幻","banned":0,
     * "latelyFollower":31802,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54
     * .25"},{"_id":"5757a7bf04eef8d3352049fc","title":"神级位面教师","author":"烟火色",
     * "shortIntro":"作为一名地球出品的位面教师，苏牧风的学生遍布多元宇宙，有战国时代的诸侯，有三十三天外的真仙，有银河母舰的舰长，有魔导帝国的皇帝。
     * 苏牧风的日常工作主要有三项，第...","cover":"/agent/http://image.cmfu.com/books/1003540925/1003540925
     * .jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":27330,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"55.22"},{"_id":"508751bef98e8f7446000024",
     * "author":"辰东","cover":"/agent/http://image.cmfu.com/books/63856/63856.jpg",
     * "shortIntro":"一个死去万载岁月的平凡青年从远古神墓中复活而出\u2026\u2026","title":"神墓","site":"zhuishuvip",
     * "cat":"玄幻","banned":0,"latelyFollower":33492,"latelyFollowerBase":0,"minRetentionRatio":0,
     * "retentionRatio":"49.88"},{"_id":"56cc69ded6db3bbe47c23e99","title":"我的时空穿梭手机",
     * "author":"金色茉莉花",
     * "shortIntro":"当安阳发现自己买的手机被调包了之后，他一开始是拒绝，当他发现这部手机可以带他穿越无尽的时空，电影、电视剧、小说、游戏\u2026\u2026
     * 他再也无法拒绝了！ 人生自此开始转变。","cover":"/agent/http://image.cmfu.com/books/1001649143/1001649143
     * .jpg","cat":"无限","site":"qidian","banned":0,"latelyFollower":29526,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"48.72"},{"_id":"5517a0d6179d91e8774040e5",
     * "title":"修真四万年","author":"卧牛真人",
     * "shortIntro":"\u201c这是一艘\u2018天狼\u2019
     * 级晶石战舰，重三亿九千万吨，由一千五百二十三名炼气期修真者操纵，主控晶脑每秒运算九千万道神念，\u2018计算力\u2019
     * 堪比元婴老怪，可以瞬息镇压一个星球！\u201d...","cover":"/agent/http://image.cmfu.com/books/3439785/3439785
     * .jpg","cat":"科幻","site":"zhuishuvip","banned":0,"latelyFollower":33331,
     * "latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"49.81"},
     * {"_id":"57085b3e10e3125756685a75","title":"位面电梯","author":"千翠百恋",
     * "shortIntro":"一次偶然，东方玉发现老旧住宿楼的电梯，在特定的时间，能够随机传送到小说，电视剧，电影，乃至动漫的世界。 从此，人生变得精彩了\u2026\u2026
     * V信书友聊天群已经建立，添加V...","cover":"/agent/http://image.cmfu.com/books/1003353824/1003353824
     * .jpg","cat":"无限","site":"qidian","banned":0,"latelyFollower":23703,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"56.53"},{"_id":"56b1c5b918ee9a7b1b374c3b",
     * "title":"都市至尊系统","author":"杯中窥香.",
     * "shortIntro
     * ":"华夏顶尖豪门杨家，唯一男丁竟然是个默默无名的废物？还是个没女人缘的苦逼男？荒唐！至尊系统附体，完成华丽转身。横推高富帅，逆袭白富美，为自己正名！用杨宁的话说，积分能解决的问题，那都不是事！","cover":"/agent/http://118.192.170.16:29998/group1/M00/10/EE/wKgGlVY4UQOEYiIIAAAAAKv4h4Y740.jpg","site":"zhuishuvip","banned":0,"latelyFollower":24418,"latelyFollowerBase":0,"minRetentionRatio":57.82,"retentionRatio":"31"},{"_id":"56a8b79326e87a5359f2eb60","title":"诛仙青云志","author":"萧鼎","shortIntro":"这世间本是没有什么神仙的，但自太古以来，人类眼见周遭世界，诸般奇异之事，电闪雷鸣，狂风暴雨，又有天灾人祸，伤亡无数，哀鸿遍野，决非人力所能为，所能抵挡。遂以为九...","cover":"/agent/http://static.zongheng.com/upload/cover/2015/05/1432263230870.jpg","cat":"仙侠","site":"zongheng","banned":0,"latelyFollower":46110,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"34.35"},{"_id":"53a26e4760b091a831bdc1f3","title":"农家仙田","author":"南山隐士","shortIntro":"神秘空间，偏僻山村。 从繁华都市回归的小农民，开启了他的传奇人生。 荒野庙宇，武林宗派，让平静的山林渐变喧嚣。 李青云说，其实我只是个农二代\u2026\u2026 闲时进山打猎三...","cover":"/agent/http://image.cmfu.com/books/3198729/3198729.jpg","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":28341,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.36"},{"_id":"56f63350fda30e2e40091fff","title":"电影世界大盗","author":"七只跳蚤","shortIntro":"窃玉者诛，窃国者侯，盗窃一方世界又何如！ 穿梭电影世界，盗女主，盗宝物，盗一方气运，无所不盗。 主角们一个个哭喊着： \u201c我的大气运！\u201d \u201c我的大造化！\u201d \u201c我的...","cover":"/agent/http://image.cmfu.com/books/1003290643/1003290643.jpg","cat":"无限","site":"qidian","banned":0,"latelyFollower":25632,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.67"},{"_id":"57089f6faa85a26e604ebdf0","title":"最强反派系统","author":"封七月","shortIntro":"什么是反派？ 是李沉舟拳倾天下，还是上官金虹搅动风云？ 是叶孤城天外飞仙，还是元十三限小箭伤心？ 重生一世，大反派系统加身，苏信可以获得前世武侠世界当中所有的反...","cover":"/agent/http://image.cmfu.com/books/1003123274/1003123274.jpg","cat":"武侠","site":"qidian","banned":0,"latelyFollower":26092,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.63"},{"_id":"528347fedfd459163304adf2","author":"张扬的五月","cover":"/agent/http://image.cmfu.com/books/3025145/3025145.jpg","shortIntro":"因为不能购买游戏头盔，穷凶极恶的李怀林一发狠，自制了一个游戏仓进入了新网游《荣耀之心》，但是没有想到居然把自己的游戏人物卡成了BUG。 别人打怪加经验，他打怪扣...","site":"zhuishuvip","title":"网游之倒行逆施","cat":"网游","banned":0,"latelyFollower":32843,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"48.5"},{"_id":"55f6be45361775607f278575","title":"神门","author":"薪意","shortIntro":"这是一个诡异莫名的世界\u2026\u2026 山，水，石，花，草，树，木，太阳，月亮，星辰\u2026\u2026天地万物都是力量的来源。 这里有着前世所有的经典名著，但却有了一个新的名字《道典》！...","cover":"/agent/http://image.cmfu.com/books/3600493/3600493.jpg","cat":"玄幻","site":"zhuishuvip","banned":0,"latelyFollower":21187,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"50.84"},{"_id":"56244b8dc46bb5ba62e0378c","title":"重生之2006","author":"雨去欲续","shortIntro":"陆恒，一个来自2015年的汽车销售职场精英，重生回到2006年高考前夕。 上辈子，因为他的堕落，导致父亲去世，母亲一夜白发，爱情婚姻一事无成。 这一世陆恒发誓要...","cover":"/agent/http://image.cmfu.com/books/3623463/3623463.jpg","cat":"都市","site":"zhuishuvip","banned":0,"latelyFollower":28635,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"53.36"},{"_id":"571186f0bbb150845b4a22e2","title":"斗鱼之顶级主播","author":"观众老爷","shortIntro":"重生带系统，任务做主播\u2026\u2026奖励顶级能力和意识？带着上帝视角打游戏？送韩服王者账号？别拦我，扶我起来，朕要直播！Q.Q群号557314393","cover":"/agent/http://image.cmfu.com/books/1003371700/1003371700.jpg","cat":"网游","site":"qidian","banned":0,"latelyFollower":24105,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.35"},{"_id":"50b45582aab49e9d04000035","author":"虾米XL","cover":"/agent/http://img.17k.com/images/bookcover/2012/548/2/109666.jpg","shortIntro":"开新书了，开新书了，《诸天万界》，兄弟们投花的投花，收藏的收藏哇！传送门：http://www.17k.com/book/840179.html \u2026\u2026\u2026\u2026\u2026\u2026\u2026...","title":"吞噬苍穹","site":"w17k","cat":"奇幻","banned":0,"latelyFollower":27271,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.73"},{"_id":"5675512c1ef9d9cc31c3496e","title":"秦时明月之大反派系统","author":"七星肥熊","shortIntro":"穿越到了秦时明月的世界，随身还附带了一个坑爹的系统。赢子弋不得不抹了把汗，还有二十多年，秦朝就得玩完。玩完就玩完吧，自己还和秦朝绑定在了一起，谁让他姓赢呢？ 最...","cover":"/agent/http://image.cmfu.com/books/3671477/3671477.jpg","cat":"同人","site":"qidian","banned":0,"latelyFollower":24404,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"52.49"},{"_id":"53eea67525ff25f16ffff03e","title":"医统江山","author":"石章鱼","shortIntro":"前世过劳而死的医生转世大康第一奸臣之家，附身在聋哑十六年的白痴少年身上，究竟是他的幸运还是不幸，上辈子太累，这辈子只想娇妻美眷，儿孙绕膝，舒舒服服地做一个蒙混度...","cover":"/agent/http://image.cmfu.com/books/3218385/3218385.jpg","site":"zhuishuvip","cat":"历史","banned":0,"latelyFollower":21695,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"59.95"},{"_id":"50dbeda1ab38ee5d3d000049","author":"陨落星辰","cover":"/agent/http://gocache.3g.cn/bookimage/bookpic/70/212570/212570_210_280.jpg?v=20140124","shortIntro":"谭笑笑说：我是极品校花！ 彭莹诗说：我是极品杀手！ 尹宝儿说：我是极品宝贝！ 上官无道说：我还是极品公子呢\u2026\u2026叶潇震撼登场：吵什么吵，少爷才是主角，少爷的口号是...","title":"绝品邪少","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":34183,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"42.38"},{"_id":"51e49f261e7439a171000001","author":"三戒大师","cover":"/agent/http://image.cmfu.com/books/3347271/3347271.jpg","shortIntro":"永乐九年，盛世天下，国大民骄，四海来朝！ 值此时，问一声，谁不想当大官人！","title":"大官人","site":"zhuishuvip","cat":"历史","banned":0,"latelyFollower":25208,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56.39"},{"_id":"528f46cac022377564009c49","author":"我本疯狂","cover":"/agent/http://image.cmfu.com/books/3034238/3034238.jpg","shortIntro":"身世如迷的少年，来到繁华都市，本想偷得浮生半日闲，却发现各方势力像飞蛾扑火一般涌现在他的身边\u2026\u2026 家事国事天下事，入世出世到救世，他像一头进山的猛虎，一路所向披...","site":"zhuishuvip","title":"极品狂少","cat":"都市","banned":0,"latelyFollower":28246,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"48.2"},{"_id":"508a2c7ef5cf27d11500000b","author":"心星逍遥","cover":"/agent/http://img.17k.com/images/bookcover/2012/417/2/83528.jpg","shortIntro":"剑尘，江湖中公认的第一高手，一手快剑法出神入化，无人能破，当他与消失百年的绝世高手独孤求败一战之后，身死而亡。 死后，剑尘的灵魂转世来到了一个陌生的世界，并且飞...","title":"混沌剑神","site":"zhuishuvip","cat":"奇幻","banned":0,"latelyFollower":25439,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"50.49"},{"_id":"53b5800ea624a39c19b64b19","title":"重生之神级学霸","author":"志鸟村","shortIntro":"生物系杨锐，一个跟头栽到了1982年，成了一名高考复读生，带着装满脑子的书籍资料，成为了校园的风云人物。","cover":"/agent/http://wap.cmread.com/r/cover_file/9335/401109335/20141109090516/cover180240.jpg","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":19506,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"64.67"},{"_id":"522287d76c578e312c00042e","author":"梦入洪荒","cover":"/agent/http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg","shortIntro":"军人出身的柳擎宇，毅然转业进入官场，成为乡镇镇长，然而上任当天却被完全架空，甚至被晾在办公室无人理睬！ 且看脾气火爆，办事雷厉风行的他，如何凭借着机智头脑和层出...","site":"zhuishuvip","title":"权力巅峰","cat":"都市","banned":0,"latelyFollower":20263,"latelyFollowerBase":2600,"minRetentionRatio":0,"retentionRatio":"64.95"},{"_id":"574e3a72c9e9044b74f9f78b","title":"总裁校花赖上我","author":"鱼人二代","shortIntro":"\u201c招聘帅哥\u2026\u2026\u201d \u201c我是来\u2026\u2026\u201d \u201c你来找工作啊，正好我家总裁缺一个男朋友！\u201d \u201c不是，我是来找校花退婚的\u2026\u2026\u201d 超级大高手楚楠不远万里跑到明城退婚，结果一下火...","cover":"/agent/http://image.cmfu.com/books/1003270018/1003270018.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":26995,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"49.96"},{"_id":"50a04e36ea1ebb6f5b00014c","author":"何常在","cover":"/agent/http://image.cmfu.com/books/1517362/1517362.jpg","shortIntro":"为官者达到举重若轻、点石成金、出神入化的境界，是为官神。 慧眼看红尘，做官如有神。 多了12年前瞻性优势的夏想重新站在大学毕业的路口，回味错过的人生，珍惜眼前的...","title":"官神","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":23752,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"64.02"},{"_id":"576247ec8b76fe640dd1ce19","title":"绝世剑神","author":"无用一书生","shortIntro":"百年前，苍穹大陆第一剑神叶云在\u201c开天\u201d的关键时刻，却遭挚爱红颜无双仙儿背叛，被至高之剑穿心，陨落万界山。　　  百年后，叶云重生到南域，落英帝国，破落战王府一个远近闻名废物少爷的身上。　    重活一世，叶云注定强势崛起，轰轰烈烈，再踏巅峰！　　  \u2026\u2026\u2026\u2026\u2026\u2026　　  六合八荒吾主沉浮，九天十地谁与争锋？　    这一世，我有一剑，可破苍穹！","cover":"/cover/146711129004969","site":"zhuishuvip","banned":0,"latelyFollower":27567,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"47.39"},{"_id":"50908b8fc3aaa79704000006","author":"黑夜de白羊","cover":"/agent/http://img.17k.com/images/bookcover/2012/446/2/89359.jpg","shortIntro":"奇遇、夺宝、杀戮、为了生存，一个小保安周旋在这些三界强者之间，将众美女一一揽入怀中！","title":"我的美女老师","site":"zhuishuvip","cat":"仙侠","banned":0,"latelyFollower":25961,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"44.28"},{"_id":"560127df3b2459ae27b5d5b4","title":"剑主苍穹","author":"乘风御剑","shortIntro":"\u201c我这一生，只出过两剑\u2026\u2026\u201d \u201c第一剑，以十年养剑、十年淬剑、十年藏剑，一剑惊天。\u201d \u201c第二剑，明心、见神、得真、悟道，而后持剑，一往无前。\u201d \u201c今日于你，我将...","cover":"/agent/http://img1.chuangshi.qq.com/upload/cover/20150929/cb_560a0662a00fb.jpg","cat":"玄幻","site":"zhuishuvip","banned":0,"latelyFollower":28436,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"46.79"},{"_id":"50a0520aea1ebb6f5b00018e","author":"愤怒的香蕉","cover":"/agent/http://image.cmfu.com/books/1979049/1979049.jpg","shortIntro":"一个受够了勾心斗角、生死打拼的金融界巨头回到了古代，进入一商贾之家最没地位的赘婿身体后的休闲故事。家国天下事，本已不欲去碰的他，却又如何能避得过了。 \u201c有人曾站...","title":"赘婿","site":"zhuishuvip","cat":"历史","banned":0,"latelyFollower":36014,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"34.45"},{"_id":"57283c257dad441c4bf77d9d","title":"网游之神级机械猎人","author":"逆袭的马里奥","shortIntro":"你还在为稀有宠物和极品装备发愁吗？ 看我带着飞机头，蜘蛛坦克，死神4000，速刷副本，虐杀BOSS！ 什么风骚的走位，什么神级意识，在我的机械炮台带来的金属风暴...","cover":"/agent/http://image.cmfu.com/books/1003422010/1003422010.jpg","cat":"网游","site":"qidian","banned":0,"latelyFollower":20947,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"60.87"},{"_id":"56a76c2ffb6f62427e387047","title":"武侠世界自由行","author":"大江入海","shortIntro":"身上有了一个可以穿越已知武侠世界的门户，于是杨易鲜衣怒马，仗剑走天下！ 让我们重温那些年一起追过的小说\u2026\u2026 扣群：531959678","cover":"/agent/http://image.cmfu.com/books/3682719/3682719.jpg","cat":"武侠","site":"qidian","banned":0,"latelyFollower":21983,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58.77"},{"_id":"50bef0732033d09b2f00007f","author":"唐家三少","cover":"/agent/http://image.cmfu.com/books/1115277/1115277.jpg","shortIntro":"唐门外门弟子唐三，因偷学内门绝学为唐门所不容，跳崖明志时却发现没有死，反而以另外一个身份来到了另一个世界，一个属于武魂的世界，名叫斗罗大陆。这里没有魔法，没有斗...","title":"斗罗大陆","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":30416,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"43.32"},{"_id":"5090718cc3aaa79704000001","author":"风凌天下","cover":"/agent/http://wfqqreader.3g.qq.com/cover/524/462524/t7_462524.jpg","shortIntro":"一笑风雷震，一怒沧海寒；一手破苍穹，一剑舞长天！　　一人一剑，傲世九重天！","title":"傲世九重天","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":25614,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.1"},{"_id":"53e8adbab371a2e830790d67","title":"都市逍遥修神","author":"血色红泪","shortIntro":"退役兵王曾逸，英雄救美身负重伤，未料被一位神人救活，得到一部逆天绝世功法，一颗神秘强大的珠子，从此校园、都市任我纵横，强大的家族，古老的门派尽数踩在脚下，一段段...","cover":"/agent/http://img4.readnovel.com/incoming/book/0/2958/202958_fm.jpg","site":"readnovel","cat":"都市","banned":0,"latelyFollower":23119,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"51.98"},{"_id":"5684b3bbed87bb472ed630e7","title":"重生之我为书狂","author":"天下第一白","shortIntro":"重生回到与地球相似的平行世界。 他以小说\u201c坏蛋是怎样练成的\u201d，开创了小白文热潮。 他写的射雕三部曲，直接将旧新武侠推向了新派武侠高峰。 他写的白蛇传，梁山伯与祝...","cover":"/agent/http://image.cmfu.com/books/3679201/3679201.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":18164,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"65.34"},{"_id":"523a89f3d87e739d3a005f30","author":"唐家三少","cover":"/agent/http://image.cmfu.com/books/2517792/2517792.jpg","shortIntro":"大陆传奇，一战成名；凤凰圣女，风火流星神界刀法；双升融合，金阳蓝月，雷霆之怒，这里没有魔法，没有斗气，没有武术，却有武魂。唐门创立万年之后的斗罗大陆上，唐门式微...","site":"zhuishuvip","title":"斗罗大陆II绝世唐门","cat":"玄幻","banned":0,"latelyFollower":25033,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"52.25"},{"_id":"572c02708ced279757fa0794","title":"无敌英雄系统","author":"忘川三途","shortIntro":"穿越成了一个被冤死的废材倒霉蛋，宁渊感到亚历山大，不过好在他有一个无敌英雄系统在手，能召唤英雄之魂附体。 剑神传人？不好意思，天剑无名，独孤求败，西门吹雪，我这...","cover":"/agent/http://image.cmfu.com/books/1003420092/1003420092.jpg","cat":"玄幻","site":"qidian","banned":0,"latelyFollower":21304,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"57.84"},{"_id":"5209f9bad94c53721b0043b2","author":"盘古","cover":"/agent/http://gocache.3g.cn/bookimage/bookpic/93/239193/239193_210_280.jpg?v=20140124","shortIntro":"臆想了一下女神，居然穿越了，大学生陈九意外来到了乾坤大陆，看着这武技满天、魔法纵横的世界，他竖了竖中指，看到了自己意外买来的九龙戒\u2026\u2026 修炼等级：淬体九重，开天...","site":"zhuishuvip","title":"九龙至尊","cat":"玄幻","banned":0,"latelyFollower":28860,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"39.85"},{"_id":"540040403f98947316a7f06c","title":"巫师之旅","author":"一行白鹭上青天","shortIntro":"给我无尽的知识，我便以自身为支点，撬动无尽世界。 故事讲述的是一个名为格林的巫师，依靠自己的智慧和运气，学习自己独特的巫师知识，游历异域世界，参与不同文明之间战...","cover":"/agent/http://image.cmfu.com/books/3268922/3268922.jpg","site":"zhuishuvip","cat":"奇幻","banned":0,"latelyFollower":20607,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"54.79"},{"_id":"55e6b44f0ba6b0f81c2d71da","title":"超时空垃圾站","author":"小城古道","shortIntro":"苏璟大学毕业后，处处碰壁，心灰意冷，回老家调整心情。却发现自家后院，成了超时空垃圾站，每天都有大量垃圾汇入其中，有的来自神墓、斗破苍穹、盘龙等小说时空；有的来自...","cover":"/agent/http://image.cmfu.com/books/3594144/3594144.jpg","cat":"都市","site":"zhuishuvip","banned":0,"latelyFollower":21135,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"53.23"},{"_id":"51d12c844a6165603e00002e","author":"逆苍天","cover":"/agent/http://image.cmfu.com/books/2817301/2817301.jpg","shortIntro":"三万年前，自称为\u201c神\u201d的搏天族入侵灵域，百族奋起反抗，最终惨败，人族率先投诚，百族随后相继臣服。 之后万年，所有种族皆被搏天族奴役，被严酷对待，生活在恐怖阴影中...","title":"灵域","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":28517,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"41.56"},{"_id":"56a83d56ff1952b30b4e1fe3","title":"文娱教父","author":"我最白","shortIntro":"这是一个拥有超越百亿人口的平行大世界。 他的第一部玄幻网文就问鼎至高，轰动网文界。 他是龙国科幻界最后的希望，也是龙国漫画界最漫不经心的王者！ 他是言情小王子，...","cover":"/agent/http://image.cmfu.com/books/3680829/3680829.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":20707,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58.24"},{"_id":"520ec9b8301c60951b0036bf","author":"神见","cover":"/agent/http://image.cmfu.com/books/2866988/2866988.jpg","shortIntro":"强者，永远都是寂寞的，只有战胜寂寞，才能无敌天下！ 地球少林世家亲传弟子黄小龙携带华夏无上武学秘典《易筋经》莫名穿越到了武魂世界。 武魂世界，体内拥有武魂才能修...","site":"zhuishuvip","title":"无敌天下","cat":"玄幻","banned":0,"latelyFollower":20989,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"56.26"},{"_id":"56fe001bd2a6eb1e7ecac12a","title":"逍遥小镇长","author":"全金属弹壳","shortIntro":"要工作，要娱乐，但更要逍遥安逸的生活。 高山飞雪，碧水扬帆，放马草原还能探秘深林，我的地盘风光秀美。 教堂，学校，警察局；医院，社区，俱乐部，还有一个小政府要运...","cover":"/agent/http://image.cmfu.com/books/1003290088/1003290088.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":19264,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"60.39"},{"_id":"545378240cab2f010ccaa8d2","title":"无限之配角的逆袭","author":"落花独立","shortIntro":"每个故事都有一个主角，他们无不是天地间的宠儿，好东西是他们的，好妹子也是他们的\u2026\u2026每个世界都有N个配角，他们无不是为了衬托主角而存在的，面对主角的强势，他们只能...","cover":"/agent/http://image.cmfu.com/books/3315254/3315254.jpg","site":"zhuishuvip","cat":"网游","banned":0,"latelyFollower":23380,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"47.82"},{"_id":"56c03ad42bb7914844388cc9","title":"非人类基因统合体","author":"魔性沧月","shortIntro":"阴冷无情的巫妖，兴风作浪的巨蛟，能量无限的尾兽，无限进化的虫族，脑域开发的外星种族。 只要不是人类，蓝牧表示什么怪物没变过？不管是神话传说，影视游戏，小说动漫，...","cover":"/agent/http://image.cmfu.com/books/3685867/3685867.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":25465,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"45.45"},{"_id":"55c3f78ef4d2352875db2e65","title":"武侠仙侠世界里的道人","author":"天帝大人","shortIntro":"武侠仙侠世界里的道人，在缓缓地穿越途中领略道的真意。射雕世界里，他用排云掌与洪七公对决；大唐世界中，他指着宁道奇说：\u201c这道门第一高手的位置你得让让了\u201d。。。。。...","cover":"/agent/http://image.cmfu.com/books/3570896/3570896.jpg","cat":"武侠","site":"zhuishuvip","banned":0,"latelyFollower":18760,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"64.32"},{"_id":"50ca7bf76221b0e226000001","author":"失落叶","cover":"/agent/http://img.17k.com/images/bookcover/2012/252/1/50551.jpg","shortIntro":"我本是一个不起眼的玩家，然而真正的高手与菜鸟到底距离有多远？ 当有一天，两个漂亮女孩一起问我：你愿意当一个高手，纵横天下吗？ 我说：我愿意！ 于是乎，我悟了，便...","title":"网游之纵横天下","site":"zhuishuvip","cat":"网游","banned":0,"latelyFollower":22332,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"55.27"},{"_id":"508de54055e53b9a1a000027","author":"梦入洪荒","cover":"/agent/http://img.17k.com/images/bookcover/2012/320/1/64141.jpg","shortIntro":"刘飞大学毕业生步入官场，因为撞破上司和女秘书的好事被发配到下面的机关，接着便是一连串的打击压制，然而，逆境之中的刘飞却没有想到，一直追求他的美女徐娇娇的老爸竟然...","title":"官途","site":"zhuishuvip","cat":"都市","banned":0,"latelyFollower":17613,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"66.27"},{"_id":"50a0559cea1ebb6f5b0001af","author":"莫默","cover":"/agent/http://image.cmfu.com/books/1844713/1844713.jpg","shortIntro":"宁遇阎罗王，莫惹唐门郎！ 行走在黑暗之中，卑鄙却不失本性，无耻却不丢风度的勾魂使者！ 一个唐门弟子重生到一个全是女子组成的门派之后的故事。 老天顺我老天昌，老天...","title":"唐门高手在异世","site":"zhuishuvip","cat":"玄幻","banned":0,"latelyFollower":25053,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"53.99"},{"_id":"57858177386d1da60d754c7b","title":"起点直播之玄幻世界大冒险","author":"我丑到灵魂深处","shortIntro":"一个扑街写手穿越到东方玄幻世界的老套故事，不过他可以直播\u2026\u2026","cover":"/agent/http://image.cmfu.com/books/1003588827/1003588827.jpg","cat":"玄幻","site":"qidian","banned":0,"latelyFollower":22617,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"55.09"},{"_id":"56e52392b5be07d0077465ed","title":"黑暗主宰","author":"零下九十度","shortIntro":"在遥远的宇宙最深处，有着一个神秘而又古老的职业，它均衡万物星辰，制约光明和黑暗，维护着宇宙的法则，它被称之为：制裁者！然而，当光明失去力量，当法则黯淡无光，黑暗...","cover":"/agent/http://image.cmfu.com/books/1002014560/1002014560.jpg","cat":"玄幻","site":"qidian","banned":0,"latelyFollower":20230,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"58.97"},{"_id":"53e7076097baba6b4e443a05","title":"九仙图","author":"秋晨","shortIntro":"被天道所不容，降下无上封印的少年偶得一幅画。 画里住着九位自称为仙人的灵魂。 于是，一个逆天强者的逆天传说开始了。","cover":"/agent/http://img1.chuangshi.qq.com/upload/cover/20140806/cb_53e1808cc565b.jpg","site":"zhuishuvip","cat":"仙侠","banned":0,"latelyFollower":24680,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"47.66"},{"_id":"5725e3f6f32f1be80cce2dca","title":"文娱缔造者","author":"别人家的小猫咪","shortIntro":"要么孤独，要么庸俗。 ps：我是来矫正文娱写法的，以上。","cover":"/agent/http://image.cmfu.com/books/1003453695/1003453695.jpg","cat":"都市","site":"qidian","banned":0,"latelyFollower":17041,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"61.01"},{"_id":"55a9d0d0a0eff3e8518e87c8","title":"从仙侠世界归来","author":"发狂的妖魔","shortIntro":"五年前，萧凡突然神秘失踪，生不见人，死不见尸，没人知道他其实是去了一个广袤无边，仙神林立，妖魔横行的仙侠世界。 五年后，萧凡已经在那个仙侠世界度过了漫长的五千年...","cover":"/agent/http://image.cmfu.com/books/3544421/3544421.jpg","cat":"都市","site":"zhuishuvip","banned":0,"latelyFollower":23801,"latelyFollowerBase":0,"minRetentionRatio":0,"retentionRatio":"43.97"}],"created":"2015-02-06T02:57:22.000Z","id":"54d42d92321052167dfb75e3"}
     * ok : true
     */

    private boolean ok;

    public RankingBean getRanking() {
        return ranking;
    }

    public void setRanking(RankingBean ranking) {
        this.ranking = ranking;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static class RankingBean {
        private String _id;
        private String updated;
        private String title;
        private String tag;
        private String cover;
        private int __v;
        private String monthRank;
        private String totalRank;
        private boolean isSub;
        private boolean collapse;
        @SerializedName("new")
        private boolean newX;
        private String gender;
        private int priority;
        private String created;
        private String id;
        /**
         * _id : 53855a750ac0b3a41e00c7e6
         * title : 择天记
         * author : 猫腻
         * shortIntro : 太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。 数千年后，十四岁的少年孤儿陈长生，为治病...
         * cover : /agent/http://image.cmfu.com/books/3347595/3347595.jpg
         * site : qidian
         * cat : 玄幻
         * banned : 0
         * latelyFollower : 182257
         * latelyFollowerBase : 0
         * minRetentionRatio : 0
         * retentionRatio : 52.48
         */

        private List<BooksBean> books;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public void setMonthRank(String monthRank) {
            this.monthRank = monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(String totalRank) {
            this.totalRank = totalRank;
        }

        public boolean isIsSub() {
            return isSub;
        }

        public void setIsSub(boolean isSub) {
            this.isSub = isSub;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public void setCollapse(boolean collapse) {
            this.collapse = collapse;
        }

        public boolean isNewX() {
            return newX;
        }

        public void setNewX(boolean newX) {
            this.newX = newX;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class BooksBean {
            private String _id;
            private String title;
            private String author;
            private String shortIntro;
            private String cover;
            private String site;
            private String cat;
            private int banned;
            private int latelyFollower;
            private int latelyFollowerBase;
            private int minRetentionRatio;
            private String retentionRatio;

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

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getShortIntro() {
                return shortIntro;
            }

            public void setShortIntro(String shortIntro) {
                this.shortIntro = shortIntro;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getSite() {
                return site;
            }

            public void setSite(String site) {
                this.site = site;
            }

            public String getCat() {
                return cat;
            }

            public void setCat(String cat) {
                this.cat = cat;
            }

            public int getBanned() {
                return banned;
            }

            public void setBanned(int banned) {
                this.banned = banned;
            }

            public int getLatelyFollower() {
                return latelyFollower;
            }

            public void setLatelyFollower(int latelyFollower) {
                this.latelyFollower = latelyFollower;
            }

            public int getLatelyFollowerBase() {
                return latelyFollowerBase;
            }

            public void setLatelyFollowerBase(int latelyFollowerBase) {
                this.latelyFollowerBase = latelyFollowerBase;
            }

            public int getMinRetentionRatio() {
                return minRetentionRatio;
            }

            public void setMinRetentionRatio(int minRetentionRatio) {
                this.minRetentionRatio = minRetentionRatio;
            }

            public String getRetentionRatio() {
                return retentionRatio;
            }

            public void setRetentionRatio(String retentionRatio) {
                this.retentionRatio = retentionRatio;
            }
        }
    }
}
