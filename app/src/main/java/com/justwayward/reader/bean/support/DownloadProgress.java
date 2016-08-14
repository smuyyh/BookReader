package com.justwayward.reader.bean.support;

/**
 * @author yuyh.
 * @date 16/8/14.
 */
public class DownloadProgress {

    public String bookId;

    public int progress;

    public DownloadProgress(String bookId, int progress) {
        this.bookId = bookId;
        this.progress = progress;
    }
}
