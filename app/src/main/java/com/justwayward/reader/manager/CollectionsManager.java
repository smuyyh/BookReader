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
package com.justwayward.reader.manager;

import android.text.TextUtils;

import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.ui.presenter.MainActivityPresenter;
import com.justwayward.reader.utils.ACache;
import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.utils.FormatUtils;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 收藏列表管理
 * Created by lfh on 2016/9/22.
 */
public class CollectionsManager {

    private volatile static CollectionsManager singleton;

    private CollectionsManager() {

    }

    public static CollectionsManager getInstance() {
        if (singleton == null) {
            synchronized (CollectionsManager.class) {
                if (singleton == null) {
                    singleton = new CollectionsManager();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取收藏列表
     *
     * @return
     */
    public List<Recommend.RecommendBooks> getCollectionList() {
        List<Recommend.RecommendBooks> list = (ArrayList<Recommend.RecommendBooks>) ACache.get(new File(Constant.PATH_COLLECT)).getAsObject("collection");
        return list == null ? null : list;
    }

    public void putCollectionList(List<Recommend.RecommendBooks> list) {
        ACache.get(new File(Constant.PATH_COLLECT)).put("collection", (Serializable) list);
    }

    /**
     * 按排序方式获取收藏列表
     *
     * @return
     */
    public List<Recommend.RecommendBooks> getCollectionListBySort() {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return null;
        } else {
            if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISBYUPDATESORT, true)) {
                Collections.sort(list, new LatelyUpdateTimeComparator());
            } else {
                Collections.sort(list, new RecentReadingTimeComparator());
            }
            return list;
        }
    }

    /**
     * 移除单个收藏
     *
     * @param bookId
     */
    public void remove(String bookId) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (TextUtils.equals(bean._id, bookId)) {
                list.remove(bean);
                putCollectionList(list);
                break;
            }
        }
        EventManager.refreshCollectionList();
    }

    /**
     * 是否已收藏
     *
     * @param bookId
     * @return
     */
    public boolean isCollected(String bookId) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (bean._id.equals(bookId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否已置顶
     *
     * @param bookId
     * @return
     */
    public boolean isTop(String bookId) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (bean._id.equals(bookId)) {
                if (bean.isTop)
                    return true;
            }
        }
        return false;
    }

    /**
     * 移除多个收藏
     *
     * @param removeList
     */
    public void removeSome(List<Recommend.RecommendBooks> removeList, boolean removeCache) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return;
        }
        if (removeCache) {
            for (Recommend.RecommendBooks book : removeList) {
                try {
                    // 移除章节文件
                    FileUtils.deleteFileOrDirectory(FileUtils.getBookDir(book._id));
                    // 移除目录缓存
                    CacheManager.getInstance().removeTocList(AppUtils.getAppContext(), book._id);
                    // 移除阅读进度
                    SettingManager.getInstance().removeReadProgress(book._id);
                } catch (IOException e) {
                    LogUtils.e(e.toString());
                }
            }
        }
        list.removeAll(removeList);
        putCollectionList(list);
    }

    /**
     * 加入收藏
     *
     * @param bean
     */
    public boolean add(Recommend.RecommendBooks bean) {
        if (isCollected(bean._id)) {
            return false;
        }
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(bean);
        putCollectionList(list);
        EventManager.refreshCollectionList();
        return true;
    }

    /**
     * 置顶收藏、取消置顶
     *
     * @param bookId
     */
    public void top(String bookId, boolean isTop) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (TextUtils.equals(bean._id, bookId)) {
                bean.isTop = isTop;
                list.remove(bean);
                list.add(0, bean);
                putCollectionList(list);
                break;
            }
        }
        EventManager.refreshCollectionList();
    }

    /**
     * 设置最新章节和更新时间
     *
     * @param bookId
     */
    public synchronized void setLastChapterAndLatelyUpdate(String bookId, String lastChapter, String latelyUpdate) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (TextUtils.equals(bean._id, bookId)) {
                if (!bean.lastChapter.equals(lastChapter)) {
                    MainActivityPresenter.isLastSyncUpdateed = true;
                }
                bean.lastChapter = lastChapter;
                bean.updated = latelyUpdate;
                list.remove(bean);
                list.add(bean);
                putCollectionList(list);
                break;
            }
        }
    }

    /**
     * 设置最近阅读时间
     *
     * @param bookId
     */
    public void setRecentReadingTime(String bookId) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (TextUtils.equals(bean._id, bookId)) {
                bean.recentReadingTime = FormatUtils.getCurrentTimeString(FormatUtils.FORMAT_DATE_TIME);
                list.remove(bean);
                list.add(bean);
                putCollectionList(list);
                break;
            }
        }
    }

    public void clear() {
        try {
            FileUtils.deleteFileOrDirectory(new File(Constant.PATH_COLLECT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 自定义比较器：按最近阅读时间来排序，置顶优先，降序
     */
    static class RecentReadingTimeComparator implements Comparator {
        public int compare(Object object1, Object object2) {
            Recommend.RecommendBooks p1 = (Recommend.RecommendBooks) object1;
            Recommend.RecommendBooks p2 = (Recommend.RecommendBooks) object2;
            if (p1.isTop && p2.isTop || !p1.isTop && !p2.isTop) {
                return p2.recentReadingTime.compareTo(p1.recentReadingTime);
            } else {
                return p1.isTop ? -1 : 1;
            }
        }
    }

    /**
     * 自定义比较器：按更新时间来排序，置顶优先，降序
     */
    static class LatelyUpdateTimeComparator implements Comparator {
        public int compare(Object object1, Object object2) {
            Recommend.RecommendBooks p1 = (Recommend.RecommendBooks) object1;
            Recommend.RecommendBooks p2 = (Recommend.RecommendBooks) object2;
            if (p1.isTop && p2.isTop || !p1.isTop && !p2.isTop) {
                return p2.updated.compareTo(p1.updated);
            } else {
                return p1.isTop ? -1 : 1;
            }
        }
    }

}