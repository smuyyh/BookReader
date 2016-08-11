package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public interface BookReadContract {

    interface View {
        void showBookToc(List<BookToc.mixToc.Chapters> list);
        void showChapterRead(ChapterRead.Chapter data, int chapter);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getBookToc(String bookId, String view);
        void getChapterRead(String url, int chapter);
    }

}
