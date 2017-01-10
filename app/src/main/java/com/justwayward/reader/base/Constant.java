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
package com.justwayward.reader.base;

import android.graphics.Color;
import android.support.annotation.StringDef;

import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.FileUtils;

import org.apache.commons.collections4.map.HashedMap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/8/5.
 */
public class Constant {

    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";

    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_COLLECT = FileUtils.createRootPath(AppUtils.getAppContext()) + "/collect";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";

    public static String BASE_PATH = AppUtils.getAppContext().getCacheDir().getPath();

    public static final String ISNIGHT = "isNight";

    public static final String ISBYUPDATESORT = "isByUpdateSort";
    public static final String FLIP_STYLE = "flipStyle";

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";

    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    @StringDef({
            Gender.MALE,
            Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }

    @StringDef({
            CateType.HOT,
            CateType.NEW,
            CateType.REPUTATION,
            CateType.OVER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CateType {
        String HOT = "hot";

        String NEW = "new";

        String REPUTATION = "reputation";

        String OVER = "over";
    }

    @StringDef({
            Distillate.ALL,
            Distillate.DISTILLATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Distillate {
        String ALL = "";

        String DISTILLATE = "true";
    }

    @StringDef({
            SortType.DEFAULT,
            SortType.COMMENT_COUNT,
            SortType.CREATED,
            SortType.HELPFUL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
        String DEFAULT = "updated";

        String CREATED = "created";

        String HELPFUL = "helpful";

        String COMMENT_COUNT = "comment-count";
    }

    public static List<String> sortTypeList = new ArrayList<String>() {{
        add(SortType.DEFAULT);
        add(SortType.CREATED);
        add(SortType.COMMENT_COUNT);
        add(SortType.HELPFUL);
    }};

    @StringDef({
            BookType.ALL,
            BookType.XHQH,
            BookType.WXXX,
            BookType.DSYN,
            BookType.LSJS,
            BookType.YXJJ,
            BookType.KHLY,
            BookType.CYJK,
            BookType.HMZC,
            BookType.XDYQ,
            BookType.GDYQ,
            BookType.HXYQ,
            BookType.DMTR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BookType {
        String ALL = "all";

        String XHQH = "xhqh";

        String WXXX = "wxxx";

        String DSYN = "dsyn";

        String LSJS = "lsjs";

        String YXJJ = "yxjj";

        String KHLY = "khly";

        String CYJK = "cyjk";

        String HMZC = "hmzc";

        String XDYQ = "xdyq";

        String GDYQ = "gdyq";

        String HXYQ = "hxyq";

        String DMTR = "dmtr";
    }

    public static List<String> bookTypeList = new ArrayList<String>() {{
        add(BookType.ALL);
        add(BookType.XHQH);
        add(BookType.WXXX);
        add(BookType.DSYN);
        add(BookType.LSJS);
        add(BookType.YXJJ);
        add(BookType.KHLY);
        add(BookType.CYJK);
        add(BookType.HMZC);
        add(BookType.XDYQ);
        add(BookType.GDYQ);
        add(BookType.HXYQ);
        add(BookType.DMTR);
    }};

    public static Map<String, String> bookType = new HashedMap<String, String>() {{
        put("qt", "其他");
        put(BookType.XHQH, "玄幻奇幻");
        put(BookType.WXXX, "武侠仙侠");
        put(BookType.DSYN, "都市异能");
        put(BookType.LSJS, "历史军事");
        put(BookType.YXJJ, "游戏竞技");
        put(BookType.KHLY, "科幻灵异");
        put(BookType.CYJK, "穿越架空");
        put(BookType.HMZC, "豪门总裁");
        put(BookType.XDYQ, "现代言情");
        put(BookType.GDYQ, "古代言情");
        put(BookType.HXYQ, "幻想言情");
        put(BookType.DMTR, "耽美同人");
    }};
}
