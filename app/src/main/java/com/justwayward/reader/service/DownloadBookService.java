package com.justwayward.reader.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.justwayward.reader.bean.ChapterRead;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/13.
 */
public class DownloadBookService extends Service {

    public static final String INTENT_CHAPTER_LIST = "chapter_list";
    public static final String INTENT_START_CHAPTER = "start_chapter";
    public static final String INTENT_END_CHAPTER = "end_chapter";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        List<ChapterRead.Chapter> list = (List<ChapterRead.Chapter>) intent.getSerializableExtra(INTENT_CHAPTER_LIST);
        int start = intent.getIntExtra(INTENT_START_CHAPTER, 0);
        int end = intent.getIntExtra(INTENT_END_CHAPTER, 0);

        // 开启下载任务
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
