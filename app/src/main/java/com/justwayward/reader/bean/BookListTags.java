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

import java.util.List;

/**
 * Created by lfh on 2016/8/15.
 */
public class BookListTags extends Base{


    /**
     * data : [{"name":"时空","tags":["都市","古代","科幻","架空","重生","未来","穿越","历史","快穿","末世","异界位面"]},
     * {"name":"情感","tags":["纯爱","热血","言情","现言","古言","情有独钟","搞笑","青春","欢喜冤家","爽文","虐文"]},
     * {"name":"流派","tags":["变身","悬疑","系统","网游","推理","玄幻","武侠","仙侠","恐怖","奇幻","洪荒","犯罪","百合",
     * "种田","惊悚","轻小说","技术流","耽美","竞技","无限"]},{"name":"人设","tags":["同人","娱乐明星","女强","帝王","职场",
     * "女配","网配","火影","金庸","豪门","扮猪吃虎","谋士","特种兵","教师"]}]
     * ok : true
     */

    public List<DataBean> data;

    public static class DataBean {
        public String name;
        public List<String> tags;
    }
}
