package com.justwayward.reader.bean.support;

/**
 * @author yuyh.
 * @date 16/8/14.
 */
public class DownloadMessage {

    public String bookId;

    public String message;

    public boolean isComplete = false;

    public DownloadMessage(String bookId, String message, boolean isComplete) {
        this.bookId = bookId;
        this.message = message;
        this.isComplete = isComplete;
    }
}
