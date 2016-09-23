package com.justwayward.reader.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookSource;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.bean.support.DownloadComplete;
import com.justwayward.reader.bean.support.DownloadError;
import com.justwayward.reader.bean.support.DownloadProgress;
import com.justwayward.reader.bean.support.DownloadQueue;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.service.DownloadBookService;
import com.justwayward.reader.ui.adapter.TocListAdapter;
import com.justwayward.reader.ui.contract.BookReadContract;
import com.justwayward.reader.ui.presenter.BookReadPresenter;
import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.ScreenUtils;
import com.justwayward.reader.utils.SharedPreferencesUtil;
import com.justwayward.reader.utils.TTSPlayerUtils;
import com.justwayward.reader.utils.ToastUtils;
import com.justwayward.reader.view.ReadView.OnReadStateChangeListener;
import com.justwayward.reader.view.ReadView.PageWidget;
import com.sinovoice.hcicloudsdk.android.tts.player.TTSPlayer;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;
import com.sinovoice.hcicloudsdk.player.TTSCommonPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lfh on 2016/9/18.
 */
public class ReadActivity extends BaseActivity implements BookReadContract.View {

    @Bind(R.id.ivBack)
    ImageView mIvBack;
    @Bind(R.id.tvBookReadReading)
    TextView mTvBookReadReading;
    @Bind(R.id.tvBookReadCommunity)
    TextView mTvBookReadCommunity;
    @Bind(R.id.tvBookReadChangeSource)
    TextView mTvBookReadChangeSource;
    @Bind(R.id.ivBookReadMore)
    ImageView mIvBookReadMore;

    @Bind(R.id.flReadWidget)
    FrameLayout flReadWidget;

    @Bind(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
    @Bind(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    @Bind(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    @Bind(R.id.tvBookReadFeedBack)
    TextView mTvBookReadFeedBack;
    @Bind(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;
    @Bind(R.id.tvBookReadDownload)
    TextView mTvBookReadDownload;
    @Bind(R.id.tvBookReadToc)
    TextView mTvBookReadToc;
    @Bind(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @Bind(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;
    @Bind(R.id.tvDownloadProgress)
    TextView mTvDownloadProgress;
    @Bind(R.id.rlReadAaSet)
    RelativeLayout rlReadAaSet;
    @Bind(R.id.seekbarLightness)
    SeekBar seekbarLightness;
    @Bind(R.id.seekbarFontSize)
    SeekBar seekbarFontSize;

    @Inject
    BookReadPresenter mPresenter;

    private List<BookToc.mixToc.Chapters> mChapterList = new ArrayList<>();
    private ListPopupWindow mTocListPopupWindow;
    private TocListAdapter mTocListAdapter;

    private String bookId;
    private int currentChapter = 1;

    /**
     * 是否开始阅读章节
     **/
    boolean startRead = false;

    /**
     * 朗读 播放器
     */
    private TTSPlayer mTtsPlayer;
    private TtsConfig ttsConfig;

    private PageWidget mPageWidget;

    @Override
    public int getLayoutId() {
        statusBarColor = -1;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_read;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        showDialog();
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        bookId = getIntent().getStringExtra("bookId");
        mTvBookReadTocTitle.setText(getIntent().getStringExtra("bookName"));

        mTtsPlayer = TTSPlayerUtils.getTTSPlayer();
        ttsConfig = TTSPlayerUtils.getTtsConfig();
    }

    @Override
    public void configViews() {

        mTocListAdapter = new TocListAdapter(this, mChapterList);
        mTocListPopupWindow = new ListPopupWindow(this);
        mTocListPopupWindow.setAdapter(mTocListAdapter);
        mTocListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTocListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTocListPopupWindow.setAnchorView(mLlBookReadTop);
        mTocListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTocListPopupWindow.dismiss();
                currentChapter = position + 1;
                startRead = false;
                readCurrentChapter();
            }
        });
        mTocListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideReadBar();
            }
        });

        seekbarFontSize.setMax(40);
        seekbarFontSize.setProgress(ScreenUtils.pxToDpInt(SettingManager.getInstance().getReadFontSize()));
        seekbarFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPageWidget.setFontSize(ScreenUtils.dpToPxInt(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mPresenter.attachView(this);
        mPresenter.getBookToc(bookId, "chapters");

    }

    /**
     * 读取currentChapter章节。章节文件存在则直接阅读，不存在就请求加载
     */
    public void readCurrentChapter() {
        if (getBookFile(currentChapter).length() > 50)
            showChapterRead(null, currentChapter);
        else
            mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
    }

    @Override
    public void showBookToc(List<BookToc.mixToc.Chapters> list) { // 加载章节列表
        mChapterList.clear();
        mChapterList.addAll(list);

        readCurrentChapter();
    }

    private String basePath = FileUtils.createRootPath(AppUtils.getAppContext()) + "/book/";

    public File getBookFile(int chapter) {
        File file = new File(basePath + bookId + "/" + chapter + ".txt");
        if (!file.exists())
            FileUtils.createFile(file);
        return file;
    }

    @Override
    public synchronized void showChapterRead(ChapterRead.Chapter data, int chapter) { // 加载章节内容
        if (data != null) {
            File file = getBookFile(chapter);
            FileUtils.writeFile(file.getAbsolutePath(), data.body, false);
            LogUtils.i("save chapter:" + chapter);
        }

        if (!startRead) {
            startRead = true;
            File dir = getBookFile(chapter);
            if (dir != null) {
                if (mPageWidget == null) {
                    mPageWidget = new PageWidget(this, bookId, chapter, mChapterList, new ReadListener());// 页面
                    flReadWidget.removeAllViews();
                    flReadWidget.addView(mPageWidget);
                } else {
                    mPageWidget.jumpToChapter(currentChapter);
                }
            }
        }
    }

    @Override
    public void showBookSource(List<BookSource> list) {

    }

    @Override
    public void netError() {
        hideDialog();//防止因为网络问题而出现dialog不消失
        ToastUtils.showToast(R.string.net_error);
    }

    @OnClick(R.id.ivBack)
    public void onClickBack() {
        if (mTocListPopupWindow.isShowing()) {
            mTocListPopupWindow.dismiss();
        } else {
            finish();
        }
    }

    @OnClick(R.id.tvBookReadReading)
    public void readBook() {
    }

    @OnClick(R.id.tvBookReadChangeSource)
    public void onClickChangeSource() {

    }

    @OnClick(R.id.tvBookReadMode)
    public void onClickChangeMode() {
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }

    @OnClick(R.id.tvBookReadSettings)
    public void setting() {
        if (isVisible(mLlBookReadBottom)) {
            if (isVisible(rlReadAaSet)) {
                gone(rlReadAaSet);
            } else {
                visible(rlReadAaSet);
            }
        }
    }

    @OnClick(R.id.tvBookReadToc)
    public void onClickToc() {
        if (!mTocListPopupWindow.isShowing()) {
            visible(mTvBookReadTocTitle);
            gone(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadChangeSource, mIvBookReadMore);
            mTocListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mTocListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mTocListPopupWindow.show();
        }
        mTocListAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tvBookReadDownload)
    public void downloadBook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("缓存多少章？")
                .setItems(new String[]{"后面五十章", "后面全部", "全部"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                DownloadBookService.post(new DownloadQueue(bookId, mChapterList, currentChapter + 1, currentChapter + 50));
                                break;
                            case 1:
                                DownloadBookService.post(new DownloadQueue(bookId, mChapterList, currentChapter + 1, mChapterList.size()));
                                break;
                            case 2:
                                DownloadBookService.post(new DownloadQueue(bookId, mChapterList, 1, mChapterList.size()));
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDownProgress(DownloadProgress progress) {
        if (bookId.equals(progress.bookId)) {
            LogUtils.e(progress.bookId + " " + progress.progress + "/" + mChapterList.size());
            if (isVisible(mLlBookReadBottom)) { // 如果工具栏显示，则进度条也显示
                visible(mTvDownloadProgress);
                mTvDownloadProgress.setText(String.format(getString(R.string.book_read_download_progress), mChapterList.get(progress.progress - 1).title, progress.progress, mChapterList.size()));
            } else {
                gone(mTvDownloadProgress);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadComplete(DownloadComplete complete) {
        if (bookId.equals(complete.bookId)) {
            mTvDownloadProgress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvDownloadProgress.setText("缓存完成");
                }
            }, 500);
            mTvDownloadProgress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gone(mTvDownloadProgress);
                }
            }, 2500);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadError(DownloadError error) {
        if (bookId.equals(error.bookId)) {
            mTvDownloadProgress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvDownloadProgress.setText("网络异常，已取消下载");
                }
            }, 500);
            mTvDownloadProgress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gone(mTvDownloadProgress);
                }
            }, 2500);
        }
    }

    private void hideReadBar() {
        gone(mTvDownloadProgress, mLlBookReadBottom, mLlBookReadTop, rlReadAaSet);
    }

    private void showReadBar() { // 显示工具栏
        visible(mLlBookReadBottom, mLlBookReadTop);
    }

    private void toggleReadBar() { // 切换工具栏 隐藏/显示 状态
        if (isVisible(mLlBookReadTop)) {
            hideReadBar();
        } else {
            showReadBar();
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mTocListPopupWindow.isShowing()) {
                mTocListPopupWindow.dismiss();
                mTvBookReadTocTitle.setVisibility(View.GONE);
                mTvBookReadReading.setVisibility(View.VISIBLE);
                mTvBookReadCommunity.setVisibility(View.VISIBLE);
                mTvBookReadChangeSource.setVisibility(View.VISIBLE);
                mIvBookReadMore.setVisibility(View.VISIBLE);
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PLAYING)
            mTtsPlayer.stop();
        EventBus.getDefault().unregister(this);
        mPresenter.cancelDownload();
    }

    class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            LogUtils.i("onChapterChanged:" + chapter);
            currentChapter = chapter;
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter && getBookFile(i).length() < 50) {
                    mPresenter.getChapterRead(mChapterList.get(i - 1).link, i);
                }
            }
        }

        @Override
        public void onPageChanged(int chapter, int page) {
            LogUtils.i("onPageChanged:" + chapter + "-" + page);
        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            LogUtils.i("onLoadChapterFailure:" + chapter);
            if (getBookFile(chapter).length() < 50)
                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onCenterClick() {
            LogUtils.i("onCenterClick");
            toggleReadBar();
        }
    }
}
