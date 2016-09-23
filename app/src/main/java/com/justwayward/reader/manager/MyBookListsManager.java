package com.justwayward.reader.manager;

import android.text.TextUtils;

import com.justwayward.reader.ReaderApplication;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.utils.ACache;
import com.justwayward.reader.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的书单管理
 * Created by lfh on 2016/9/23.
 */
public class MyBookListsManager {

    private volatile static MyBookListsManager singleton;

    private MyBookListsManager() {

    }

    public static MyBookListsManager getInstance() {
        if (singleton == null) {
            synchronized (MyBookListsManager.class) {
                if (singleton == null) {
                    singleton = new MyBookListsManager();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取我收藏的书单列表
     *
     * @return
     */
    public List<BookLists.BookListsBean> getCollectionList() {
        List<BookLists.BookListsBean> list = (ArrayList<BookLists.BookListsBean>) ACache.get(
                ReaderApplication.getsInstance()).getAsObject("my_book_lists");
        return list == null ? null : list;
    }

    /**
     * 移除收藏
     *
     * @param bookListId
     */
    public void remove(String bookListId) {
        List<BookLists.BookListsBean> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (BookLists.BookListsBean bean : list) {
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookListId)) {
                    list.remove(bean);
                    ACache.get(ReaderApplication.getsInstance()).put("my_book_lists", (Serializable) list);
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
    public void add(BookLists.BookListsBean bean) {
        List<BookLists.BookListsBean> list = getCollectionList();
        if (list == null) {
            list = new ArrayList<>();
        }
        for (BookLists.BookListsBean data : list) {
            if (data != null) {
                if (TextUtils.equals(data._id, bean._id)) {
                    ToastUtils.showToast("已收藏");
                    return ;
                }
            }
        }
        list.add(bean);
        ACache.get(ReaderApplication.getsInstance()).put("my_book_lists", (Serializable) list);
        ToastUtils.showToast("收藏成功");
    }

}
