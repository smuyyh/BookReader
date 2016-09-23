package com.justwayward.reader.manager;

import android.text.TextUtils;

import com.justwayward.reader.ReaderApplication;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.utils.ACache;

import java.io.Serializable;
import java.util.ArrayList;
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
     * 移除收藏
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
     * 加入收藏
     *
     * @param bean
     */
    public void add(Recommend.RecommendBooks bean) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(bean);
        ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
    }

    /**
     * 置顶收藏
     *
     * @param bookId
     */
    public void top(String bookId) {
        List<Recommend.RecommendBooks> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (Recommend.RecommendBooks bean : list) {
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookId)) {
                    bean.isTop = true;
                    list.remove(bean);
                    list.add(0,bean);
                    ACache.get(ReaderApplication.getsInstance()).put("collection", (Serializable) list);
                    break;
                }
            }
        }
    }


}
