package com.justwayward.reader.manager;

import android.text.TextUtils;

import com.justwayward.reader.ReaderApplication;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.utils.ACache;
import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.utils.FormatUtils;
import com.justwayward.reader.utils.LogUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        List<Recommend.RecommendBooks> list = (ArrayList<Recommend.RecommendBooks>) ACache.get(
                ReaderApplication.getsInstance()).getAsObject("collection");
        return list == null ? null : list;
    }

    /**
     * 获取收藏列表（按最近阅读时间排序）
     *
     * @return
     */
    public List<Recommend.RecommendBooks> getCollectionListByRecentReadingTime() {
        List<Recommend.RecommendBooks> list = (ArrayList<Recommend.RecommendBooks>) ACache.get(
                ReaderApplication.getsInstance()).getAsObject("collection");
        if (list == null) {
            return null;
        } else {
            Collections.sort(list, new RecentReadingTimeComparator());
            Collections.reverse(list);
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
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookId)) {
                    list.remove(bean);
                    ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
                    break;
                }
            }
        }
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
        ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
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
        ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
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
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookId)) {
                    bean.isTop = isTop;
                    list.remove(bean);
                    list.add(0, bean);
                    ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
                    break;
                }
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
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookId)) {
                    bean.recentReadingTime = FormatUtils.formatDate(new Date().toString());
                    list.remove(bean);
                    list.add(bean);
                    ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
                    break;
                }
            }
        }
    }


    /**
     * 自定义比较器：按最近阅读时间来排序
     */
    static class RecentReadingTimeComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            Recommend.RecommendBooks p1 = (Recommend.RecommendBooks) object1; // 强制转换
            Recommend.RecommendBooks p2 = (Recommend.RecommendBooks) object2;
            return p1.recentReadingTime.compareTo(p2.recentReadingTime);
        }
    }

}
