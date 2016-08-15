package com.justwayward.reader.bean;

import java.util.List;

/**
 * Created by lfh on 2016/8/15.
 */
public class BookLists {


    /**
     * bookLists : [{"_id":"57a83783c9b799011623ecff","title":"【追书盘点】男频类型文（六）体育类竞技文",
     * "author":"追书白小生","desc":"在体育竞技的赛场上！\r\n运动员们，因为一个坚定的信念，而不断前行，努力，付出。\r\n他们的目标只有一个：升级！\r\n
     * 当冠军，收小弟，在体育的大道上，走向人生的巅峰！\r\n\r\n本次就让我们来盘点一下，那些正值火热的体育类竞技文吧。\r\n【排名不分先后】","gender":"male",
     * "collectorCount":2713,"cover":"/agent/http://image.cmfu.com/books/3623405/3623405.jpg",
     * "bookCount":20},{"_id":"57a81bb323eda7b201353972","title":"青春不老，我们不散","author":"鹿晓",
     * "desc":"那些曾经让我们潸然泪下的书","gender":"female","collectorCount":2478,
     * "cover":"/agent/http://wfqqreader.3g.qq.com/cover/534/444534/t7_444534.jpg",
     * "bookCount":47},{"_id":"57a7ed4e3ed5f1fe6d46d5e0","title":"古代医书","author":"安小东",
     * "desc":"我国古代的医书","gender":"male","collectorCount":2361,"cover":"/agent/http://wfqqreader
     * .3g.qq.com/cover/777/582777/t7_582777.jpg","bookCount":20},
     * {"_id":"57a8b3b756ae33c864eb984e","title":"English books.","author":"玉玉穆","desc":"Here,
     * there are classic English books.I believe that's a job you like it here.","gender":"none",
     * "collectorCount":2234,"cover":"/agent/http://rm2.kingreader
     * .com/book/690761%2Fm%2F%5B640%5D_201508181921313153.jpg","bookCount":27},
     * {"_id":"57a89e88f1a617d47c8f65a2","title":"快开学了！学渣动起来！！！","author":"伏-307",
     * "desc":"多读书，读好书，都让开，不要阻止我当学霸！！！😠😠😠😠","gender":"none","collectorCount":1761,
     * "cover":"/agent/http://wfqqreader.3g.qq.com/cover/16/447016/t7_447016.jpg",
     * "bookCount":21},{"_id":"57aa568f5fa4bac6014057cd","title":"洪荒，系统，无限，武侠，都市","author":"落尘",
     * "desc":"全是大作","gender":"male","collectorCount":1392,"cover":"/agent/http://image.cmfu
     * .com/books/2750457/2750457.jpg","bookCount":351},{"_id":"57b0875a9769012828614b36",
     * "title":"【追书盘点】男频类型文（七）娱乐圈生活文","author":"追书白小生",
     * "desc":"娱乐圈的存在大多让小老百姓们有了茶余饭后八卦的资源。\r\n比如：XXX婚内出轨！XXX喜当爹！XXX潜规则上位！\r\n
     * 可这圈子里又不止有这些亦真亦假的小道消息，更多的还有生活！\r\n今天，我们就来盘点娱乐圈那些和生活息息相关的文。\r\n【排名不分先后】","gender":"male",
     * "collectorCount":1297,"cover":"/agent/http://image.cmfu.com/books/3646562/3646562.jpg",
     * "bookCount":9},{"_id":"57a98046c0f84c4671053fca","title":"致青春","author":"渐行渐远...",
     * "desc":"这是我喜欢的一些书，有穿越文，总裁文，青梅竹马文，反正是各种文。希望大家喜欢，大家看看吧！☺☺（随时加书哦）","gender":"female",
     * "collectorCount":1278,"cover":"/agent/http://media.tadu
     * .com/2015/09/29/15/53/08497_e8b54e8dfe8b4bfb830104c9007ec997_250_200.jpg","bookCount":89},
     * {"_id":"57a8297b207ef1ed694e50ea","title":"女强，一女n男，还有一些乱入","author":"花之语",
     * "desc":"女主，额，强，\n男主（们），帅，强，\n额外的，校园文，宠文，青梅竹马，吸血鬼神马的，反正，我把书架上的书全加了，懒得分","gender":"female",
     * "collectorCount":1172,"cover":"/agent/http://images.xxsy.net/bimg/403749.jpg",
     * "bookCount":121},{"_id":"57aad0212e807c762695ee0a","title":"中华古籍","author":"HaZy RaIn",
     * "desc":"中华民族历史绝唱！","gender":"male","collectorCount":1092,"cover":"/agent/http://media.tadu
     * .com/","bookCount":136},{"_id":"57a84b9545e61de312cc7fa5","title":"已出版小说，赞赞赞","author":"喵呜
     * (๑´ω`๑)","desc":"都是比较好的文章，其中大部分都是魅丽文化出版的，都比较好！","gender":"female","collectorCount":1070,
     * "cover":"/agent/http://img.17k.com/images/bookcover/2012/1955/9/391132.jpg",
     * "bookCount":35},{"_id":"57a896da3da9e1710c50d5bc","title":"快穿女配类的小说没事可以看看",
     * "author":"*T_T*苦*:*丁*:*茶T_T*","desc":"这些都是我看过的小说质量绝对保证，字数也很多，可入坑！！！","gender":"female",
     * "collectorCount":1012,"cover":"/agent/http://img1.chuangshi.qq
     * .com/upload/cover/20150215/cb_54e0c21d6aa12.png","bookCount":137},
     * {"_id":"57a7639e5ea1476317d327e9","title":"师生暖文","author":"小丑","desc":"都是老师与学生之间产生的爱情",
     * "gender":"female","collectorCount":1007,"cover":"/agent/http://img3.douban
     * .com/lpic/s3914114.jpg","bookCount":20},{"_id":"57a99db131fb202c71e314fc",
     * "title":"系统流（优先）","author":"◆◆、今年丶忿散","desc":"持续更新中，收藏就知道😊","gender":"male",
     * "collectorCount":863,"cover":"/agent/http://image.cmfu.com/books/2271528/2271528.jpg",
     * "bookCount":138},{"_id":"57a8b545f304695a41438876","title":"都是你们爱看的暖文","author":"雪吧👑",
     * "desc":"有暖文，宠文.我愿意放弃这个世界来爱你
     * (本宝宝是一个超爱看暖文的一个人。所以这些小说都是本宝宝超爱的。这些每一本宝宝都看过绝对值得一看)","gender":"female","collectorCount":840,
     * "cover":"/agent/http://i57.tinypic.com/4qq0qp.jpg","bookCount":11},
     * {"_id":"57aca70674cce5a313cad795","title":"能看完这些书你离疯也不远了","author":"史料百科编辑员",
     * "desc":"有些书确实是能把人逼疯逼成神经病的，不信，自己看看","gender":"male","collectorCount":823,
     * "cover":"/agent/http://bj.bs.baidu
     * .com/wise-novel-authority-logo/911f8d7aa830181dea3902d378eb5ed9.jpg","bookCount":24},
     * {"_id":"57a85502cf9f8bd1238f70fb","title":"明星文","author":"迷路和麋鹿","desc":"都是明星文呦～(￣▽￣～)
     * (～￣▽￣)～","gender":"female","collectorCount":780,"cover":"/agent/http://wap.cmread
     * .com/r/cover_file/5031/406695031/20150511084002/cover180240.jpg","bookCount":79},
     * {"_id":"57a75cbbd620e1972fb397ac","title":"黑暗，不圣母，不小白。不定期更新！！！","author":"放肆的活着",
     * "desc":"心狠手辣。小白请勿入","gender":"male","collectorCount":753,"cover":"/agent/http://image.cmfu
     * .com/books/2527417/2527417.jpg","bookCount":111},{"_id":"57ab1260ac5417b76c01054c",
     * "title":"FBI","author":"♠忧星离落语☆♪","desc":"心理学  催眠","gender":"male","collectorCount":712,
     * "cover":"/agent/http://wfqqreader.3g.qq.com/cover/211/170211/t7_170211.jpg",
     * "bookCount":25},{"_id":"57a9f6be4270144936a79a51","title":"男变女，或伪娘，或萝莉",
     * "author":"蕾姆","desc":"如标题［变百系列］QwQ不求精选，","gender":"male","collectorCount":653,
     * "cover":"/agent/http://image.cmfu.com/books/2164297/2164297.jpg","bookCount":242}]
     * ok : true
     */

    private boolean ok;
    /**
     * _id : 57a83783c9b799011623ecff
     * title : 【追书盘点】男频类型文（六）体育类竞技文
     * author : 追书白小生
     * desc : 在体育竞技的赛场上！
     运动员们，因为一个坚定的信念，而不断前行，努力，付出。
     他们的目标只有一个：升级！
     当冠军，收小弟，在体育的大道上，走向人生的巅峰！

     本次就让我们来盘点一下，那些正值火热的体育类竞技文吧。
     【排名不分先后】
     * gender : male
     * collectorCount : 2713
     * cover : /agent/http://image.cmfu.com/books/3623405/3623405.jpg
     * bookCount : 20
     */

    private List<BookListsBean> bookLists;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<BookListsBean> getBookLists() {
        return bookLists;
    }

    public void setBookLists(List<BookListsBean> bookLists) {
        this.bookLists = bookLists;
    }

    public static class BookListsBean {
        private String _id;
        private String title;
        private String author;
        private String desc;
        private String gender;
        private int collectorCount;
        private String cover;
        private int bookCount;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }
    }
}
