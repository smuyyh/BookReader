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

/**
 * Created by Administrator on 2016/8/12.
 */
public class BookSource implements Serializable {

    /**
     * _id : 55219c4ea9240fb868282e65
     * lastChapter : 学神被封了，书也应该不可能放出来了。
     * link : http://leduwo.com/book/56/56121/index.html
     * source : tianyibook
     * name : 乐读窝
     * isCharge : false
     * chaptersCount : 515
     * updated : 2016-08-09T18:42:34.880Z
     * starting : false
     * host : leduwo.com
     */

    public String _id;
    public String lastChapter;
    public String link;
    public String source;
    public String name;
    public boolean isCharge;
    public int chaptersCount;
    public String updated;
    public boolean starting;
    public String host;
}
