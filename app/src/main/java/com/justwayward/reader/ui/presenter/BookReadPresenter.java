package com.justwayward.reader.ui.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.justwayward.reader.api.BookApi;
import com.justwayward.reader.bean.BookSource;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.ui.contract.BookReadContract;
import com.justwayward.reader.utils.BookPageFactory;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BookReadPresenter implements BookReadContract.Presenter<BookReadContract.View> {
    private Context context;
    private BookApi bookApi;

    private BookReadContract.View view;
    private AsyncTask<Integer, Integer, Integer> downloadTask;

    private static final String TAG = "BookReadPresenter";

    public boolean interrupted = false;

    @Inject
    public BookReadPresenter(Context context, BookApi bookApi) {
        this.context = context;
        this.bookApi = bookApi;
    }

    @Override
    public void attachView(BookReadContract.View view) {
        this.view = view;
    }

    @Override
    public void getBookToc(String bookId, String viewChapters) {
        bookApi.getBookToc(bookId, viewChapters).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookToc>() {
                    @Override
                    public void onNext(BookToc data) {
                        List<BookToc.mixToc.Chapters> list = data.mixToc.chapters;
                        if (list != null && !list.isEmpty() && view != null) {
                            view.showBookToc(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                });
    }

    @Override
    public void getChapterRead(String url, final int chapter) {
        bookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onNext(ChapterRead data) {
                        if (data.chapter != null && view != null) {
                            view.showChapterRead(data.chapter, chapter);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                });
    }

    @Override
    public void getBookSource(String viewSummary, String book) {
        bookApi.getBookSource(viewSummary, book).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookSource>>() {
                    @Override
                    public void onNext(List<BookSource> data) {
                        if (data != null && view != null) {
                            view.showBookSource(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                });
    }

    public synchronized void downloadBook(final String bookId, final List<BookToc.mixToc.Chapters> list, final int start, final int end) {
        interrupted = false;
        downloadTask = new AsyncTask<Integer, Integer, Integer>() {

            int failureCount = 0;
            BookPageFactory factory = new BookPageFactory(bookId, 0);

            @Override
            protected Integer doInBackground(Integer... params) {
                for (int i = start; i < end && i <= list.size(); i++) {
                    if(!interrupted) {
                        if (factory.getBookFile(i).length() < 50) { // 认为章节文件不存在,则下载
                            BookToc.mixToc.Chapters chapters = list.get(i - 1);
                            String url = chapters.link;
                            int ret = download(url, i);
                            if (ret != 1) {
                                failureCount++;
                            }
                        } else {
                            view.showDownloadProgress(null, i);
                        }
                    }
                }
                return null;
            }


            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                ToastUtils.showSingleToast("缓存完成！");
                LogUtils.i("缓存完成，失败" + failureCount + "章");
            }
        };
        downloadTask.execute();
    }

    private int download(String url, final int chapter) {

        final int[] result = {-1};

        bookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onNext(ChapterRead data) {
                        if (data.chapter != null && view != null) {
                            view.showDownloadProgress(data.chapter, chapter);
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
                        Log.e(TAG, "onError: " + e);
                    }
                });

        while (result[0] == -1) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result[0];
    }

    public void cancelDownload(){
        interrupted = true;
    }

}