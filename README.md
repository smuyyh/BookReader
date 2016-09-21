# BookReader
#### 任阅小说阅读器。实现追书推荐、标签检索、3D仿真翻页效果、文章阅读、缓存章节、日夜间模式、文本朗读等功能。

## 项目
本项目基于RxJava + Retrofit2 + Dagger2，结合MVP模式开发，项目结构清晰。本项目中的API均来自追书神器，纯属共享学习之用，不得用于商业用途。
有任何疑问或建议可提[issue](https://github.com/JustWayward/BookReader/issues/new)。

## TODO
* [ ] 新版阅读页[（预览图）](#新版阅读页)，[贝塞尔曲线](#新版阅读页)模拟翻书效果，提高分页速度以及分页准确性

* [ ] 登录模块（由于openid的限制，可能无法实现，考虑用sp代替）

* [ ] 细节优化

## 截图

- **首页**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/home_zhuishu.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/home_communication.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/home_discover.png?raw=true" width="280"/>

</br>

- **阅读**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/read_page_3.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/read_page.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/read_page_2.png?raw=true" width="280"/>

</br>

- **书籍**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/search.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/book_detail.png?raw=true" width="280"/>

</br>

- **社区**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/discuss.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/discuss_detail.png?raw=true" width="280"/>

</br>

- **排行榜**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/rank_type.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/rank_list.png?raw=true" width="280"/>

</br>

- **主题书单**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/subject_tag.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/subject_detail.png?raw=true" width="280"/>

</br>

- **书籍分类**

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/category.png?raw=true" width="280"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/category_list.png?raw=true" width="280"/>

##新版阅读页

友情提示：新版阅读界面目前尚未完全写完，阅读暂时还是先跳转到旧版阅读页。新版阅读页目前已实现的效果图如下：

<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/bezier_read_page_2.png?raw=true" width="320"/>
<img src="https://github.com/JustWayward/BookReader/blob/master/screenshot/bezier_read_page_3.png?raw=true" width="320"/>

正在拼命完善中~~

## LICENSE

```
   Copyright 2016 JustWayward Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
