package com.justwayward.reader.bean.user;

import com.justwayward.reader.bean.base.Base;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/9/4.
 */
public class Following extends Base {


    /**
     * _id : 52f840b982cfcc3a74031693
     * avatar : /avatar/56/a9/56a96462a50ca99f9cf83440899e46f3
     * nickname : 追书首席打杂
     * followers : 4662
     * followings : 107
     * tweets : 949
     */

    public List<FollowingsBean> followings;

    public static class FollowingsBean {
        public String _id;
        public String avatar;
        public String nickname;
        public int followers;
        public int followings;
        public int tweets;
    }
}
