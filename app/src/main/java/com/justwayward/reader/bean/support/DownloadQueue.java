package com.justwayward.reader.bean.support;

import com.justwayward.reader.bean.BookToc;

import java.io.Serializable;
import java.util.List;

/**
 * 下载队列实体
 *
 * @author yuyh.
 * @date 16/8/13.
 */
public class DownloadQueue implements Serializable {

    public String bookId;

    public List<BookToc.mixToc.Chapters> list;

    public int start;

    public int end;

    public boolean isStartDownload = false;

    public boolean isCancel = false;

    public boolean isFinish = false;

    public DownloadQueue(String bookId, List<BookToc.mixToc.Chapters> list, int start, int end) {
        this.bookId = bookId;
        this.list = list;
        this.start = start;
        this.end = end;
    }
}
