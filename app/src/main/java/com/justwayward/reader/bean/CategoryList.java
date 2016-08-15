package com.justwayward.reader.bean;

import java.util.List;

/**
 * Created by lfh on 2016/8/15.
 */
public class CategoryList {


    /**
     * male : [{"name":"玄幻","bookCount":188244},{"name":"奇幻","bookCount":24183},{"name":"武侠",
     * "bookCount":14704},{"name":"仙侠","bookCount":59200},{"name":"都市","bookCount":124354},
     * {"name":"职场","bookCount":6953},{"name":"历史","bookCount":29649},{"name":"军事",
     * "bookCount":6326},{"name":"游戏","bookCount":32850},{"name":"竞技","bookCount":2932},
     * {"name":"科幻","bookCount":41933},{"name":"灵异","bookCount":22194},{"name":"同人",
     * "bookCount":34233},{"name":"轻小说","bookCount":2955}]
     * female : [{"name":"古代言情","bookCount":125103},{"name":"现代言情","bookCount":127900},
     * {"name":"青春校园","bookCount":37908},{"name":"纯爱","bookCount":41474},{"name":"玄幻奇幻",
     * "bookCount":25085},{"name":"武侠仙侠","bookCount":14724},{"name":"科幻","bookCount":2032},
     * {"name":"游戏竞技","bookCount":1723},{"name":"悬疑灵异","bookCount":2275},{"name":"同人",
     * "bookCount":46644},{"name":"女尊","bookCount":6228},{"name":"莉莉","bookCount":7573}]
     * ok : true
     */

    private boolean ok;
    /**
     * name : 玄幻
     * bookCount : 188244
     */

    private List<MaleBean> male;
    /**
     * name : 古代言情
     * bookCount : 125103
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
        private String name;
        private int bookCount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }
    }

    public static class FemaleBean {
        private String name;
        private int bookCount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }
    }
}
