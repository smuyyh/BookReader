package com.justwayward.reader.bean.support;

/**
 * @author yuyh.
 * @date 16/8/14.
 */
public class DownloadProgress {

    public String bookId;

    public int progress;

    public boolean isAlreadyDownload = false;

    public DownloadProgress(String bookId, int progress, boolean isAlreadyDownload) {
        this.bookId = bookId;
        this.progress = progress;
        this.isAlreadyDownload = isAlreadyDownload;
    }
}
