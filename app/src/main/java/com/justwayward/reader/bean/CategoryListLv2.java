package com.justwayward.reader.bean;

import java.util.List;

/**
 * Created by lfh on 2016/8/15.
 */
public class CategoryListLv2 {


    /**
     * male : [{"major":"玄幻","mins":["东方玄幻","异界大陆","异界争霸","远古神话"]},{"major":"奇幻","mins":["西方奇幻",
     * "领主贵族","亡灵异族","魔法校园"]},{"major":"武侠","mins":["传统武侠","新派武侠","国术武侠"]},{"major":"仙侠",
     * "mins":["古典仙侠","幻想修仙","现代修仙","洪荒封神"]},{"major":"都市","mins":["都市生活","爱情婚姻","异术超能","恩怨情仇",
     * "青春校园","现实百态"]},{"major":"职场","mins":["娱乐明星","官场沉浮","商场职场"]},{"major":"历史","mins":["穿越历史",
     * "架空历史","历史传记"]},{"major":"军事","mins":["军事战争","战争幻想","谍战特工","军旅生涯","抗战烽火"]},{"major":"游戏",
     * "mins":["游戏生涯","电子竞技","虚拟网游","游戏异界"]},{"major":"竞技","mins":["体育竞技","篮球运动","足球运动","棋牌桌游"]},
     * {"major":"科幻","mins":["星际战争","时空穿梭","未来世界","古武机甲","超级科技","进化变异","末世危机"]},{"major":"灵异",
     * "mins":["推理侦探","恐怖惊悚","悬疑探险","灵异奇谈"]},{"major":"同人","mins":["武侠同人","影视同人","动漫同人","游戏同人",
     * "小说同人"]},{"major":"轻小说","mins":[]}]
     * female : [{"major":"古代言情","mins":["穿越时空","古代历史","古典架空","宫闱宅斗","经商种田"]},{"major":"现代言情",
     * "mins":["豪门总裁","都市生活","婚恋情感","商战职场","异术超能"]},{"major":"青春校园","mins":[]},{"major":"纯爱",
     * "mins":["古代纯爱","现代纯爱"]},{"major":"玄幻奇幻","mins":["玄幻异世","奇幻魔法"]},{"major":"武侠仙侠",
     * "mins":["武侠","仙侠"]},{"major":"科幻","mins":[]},{"major":"游戏竞技","mins":[]},{"major":"悬疑灵异",
     * "mins":["悬疑","灵异"]},{"major":"同人","mins":["小说同人","动漫同人","影视同人","游戏同人","耽美同人"]},
     * {"major":"女尊","mins":[]},{"major":"莉莉","mins":[]}]
     * ok : true
     */

    private boolean ok;
    /**
     * major : 玄幻
     * mins : ["东方玄幻","异界大陆","异界争霸","远古神话"]
     */

    private List<MaleBean> male;
    /**
     * major : 古代言情
     * mins : ["穿越时空","古代历史","古典架空","宫闱宅斗","经商种田"]
     */

    private List<FemaleBean> female;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<MaleBean> getMale() {
        return male;
    }

    public void setMale(List<MaleBean> male) {
        this.male = male;
    }

    public List<FemaleBean> getFemale() {
        return female;
    }

    public void setFemale(List<FemaleBean> female) {
        this.female = female;
    }

    public static class MaleBean {
        private String major;
        private List<String> mins;

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public List<String> getMins() {
            return mins;
        }

        public void setMins(List<String> mins) {
            this.mins = mins;
        }
    }

    public static class FemaleBean {
        private String major;
        private List<String> mins;

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public List<String> getMins() {
            return mins;
        }

        public void setMins(List<String> mins) {
            this.mins = mins;
        }
    }
}
