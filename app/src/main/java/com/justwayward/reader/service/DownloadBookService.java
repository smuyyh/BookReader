package com.justwayward.reader.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.api.support.HeaderInterceptor;
import com.justwayward.reader.api.support.LoggingInterceptor;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.bean.support.DownloadComplete;
import com.justwayward.reader.bean.support.DownloadProgress;
import com.justwayward.reader.bean.support.DownloadQueue;
import com.justwayward.reader.module.BookApiModule;
import com.justwayward.reader.utils.BookPageFactory;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/8/13.
 */
public class DownloadBookService extends Service {

    public static List<DownloadQueue> downloadQueues = new ArrayList<>();

    public BookApi bookApi;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        LoggingInterceptor logging = new LoggingInterceptor(new BookApiModule.MyLog());
        logging.setLevel(LoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging);
        bookApi = BookApi.getInstance(builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static void post(DownloadQueue downloadQueue) {
        EventBus.getDefault().post(downloadQueue);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addToDownloadQueue(DownloadQueue downloadQueue) {
        LogUtils.e("addToDownloadQueue:" + downloadQueue.bookId);
        boolean exists = false;
        // 判断当前书籍缓存任务是否存在
        for (int i = 0; i < downloadQueues.size(); i++) {
            if (downloadQueues.get(i).bookId.equals(downloadQueue.bookId)) {
                LogUtils.e("addToDownloadQueue:exists");
                exists = true;
                break;
            }
        }
        if (exists) {
            ToastUtils.showSingleToast("当前缓存任务已存在");
            return;
        }

        // 添加到下载队列
        downloadQueues.add(downloadQueue);
        // 从队列顺序取出，下载（正常情况下来说只有一条）
        for (DownloadQueue queue : downloadQueues) {
            downloadBook(queue);
        }
        ToastUtils.showSingleToast("成功加入缓存队列");
    }

    public synchronized void downloadBook(final DownloadQueue downloadQueue) {
        AsyncTask<Integer, Integer, Integer> downloadTask = new AsyncTask<Integer, Integer, Integer>() {

            int failureCount = 0;
            List<BookToc.mixToc.Chapters> list = downloadQueue.list;
            String bookId = downloadQueue.bookId;
            int start = downloadQueue.start; // 起始章节
            int end = downloadQueue.end; // 结束章节
            BookPageFactory factory = new BookPageFactory(bookId, 0);

            @Override
            protected Integer doInBackground(Integer... params) {
                for (int i = start; i < end && i <= list.size(); i++) {
                    if (!downloadQueue.isFinish && !downloadQueue.isCancel) {
                        if (factory.getBookFile(i).length() < 50) { // 认为章节文件不存在,则下载
                            BookToc.mixToc.Chapters chapters = list.get(i - 1);
                            String url = chapters.link;
                            int ret = download(factory, url, bookId, i);
                            if (ret != 1) {
                                failureCount++;
                            }
                        } else {
                            EventBus.getDefault().post(new DownloadProgress(bookId, i));
                        }
                    }
                }
                return null;
            }


            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                downloadQueue.isFinish = true;
                // 下载完成，从队列里移除
                downloadQueues.remove(downloadQueue);
                LogUtils.i(bookId + "缓存完成，失败" + failureCount + "章");
                // 通知
                EventBus.getDefault().post(new DownloadComplete());
            }
        };
        downloadTask.execute();
    }

    private int download(final BookPageFactory factory, String url, final String bookId, final int chapter) {

        final int[] result = {-1};

        bookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onNext(ChapterRead data) {
                        if (data.chapter != null) {
                            factory.append(data.chapter, chapter);
                            EventBus.getDefault().post(new DownloadProgress(bookId, chapter));
                            result[0] = 1;
                        } else {
                            result[0] = 0;
                        }
                    }

                    @Override
                    public void onCompleted() {
                        result[0] = 1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        result[0] = 0;
                    }
                });

        while (result[0] == -1) {
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result[0];
    }
}
