package com.justwayward.reader.bean;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class PlayerList {

    public PlayerListResult result;

    public static class PlayerListResult {
        public Creator creator;

        public List<Track> tracks;
    }

    public static class Creator {
        public String signature;
        public String avatarUrl;
    }

    public static class Track {
        public int id;
        public String name;
    }

}
