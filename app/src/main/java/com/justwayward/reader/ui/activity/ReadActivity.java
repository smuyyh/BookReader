package com.justwayward.reader.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
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
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.bean.support.DownloadComplete;
import com.justwayward.reader.bean.support.DownloadError;
import com.justwayward.reader.bean.support.DownloadProgress;
import com.justwayward.reader.bean.support.DownloadQueue;
import com.justwayward.reader.bean.support.ReadTheme;
import com.justwayward.reader.bean.support.RefreshCollectionIconEvent;
import com.justwayward.reader.bean.support.RefreshCollectionListEvent;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerBookComponent;
import com.justwayward.reader.manager.CacheManager;
import com.justwayward.reader.manager.CollectionsManager;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.manager.ThemeManager;
import com.justwayward.reader.service.DownloadBookService;
import com.justwayward.reader.ui.adapter.TocListAdapter;
import com.justwayward.reader.ui.contract.BookReadContract;
import com.justwayward.reader.ui.easyadapter.ReadThemeAdapter;
import com.justwayward.reader.ui.presenter.BookReadPresenter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Bind(R.id.tvBookReadIntroduce)
    TextView mTvBookReadChangeSource;

    @Bind(R.id.flReadWidget)
    FrameLayout flReadWidget;

    @Bind(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
    @Bind(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    @Bind(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
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
    LinearLayout rlReadAaSet;
    @Bind(R.id.ivBrightnessMinus)
    ImageView ivBrightnessMinus;
    @Bind(R.id.seekbarLightness)
    SeekBar seekbarLightness;
    @Bind(R.id.ivBrightnessPlus)
    ImageView ivBrightnessPlus;

    @Bind(R.id.tvFontsizeMinus)
    TextView tvFontsizeMinus;
    @Bind(R.id.seekbarFontSize)
    SeekBar seekbarFontSize;
    @Bind(R.id.tvFontsizePlus)
    TextView tvFontsizePlus;

    @Bind(R.id.cbVolume)
    CheckBox cbVolume;
    @Bind(R.id.cbAutoBrightness)
    CheckBox cbAutoBrightness;
    @Bind(R.id.gvTheme)
    GridView gvTheme;

    @Inject
    BookReadPresenter mPresenter;

    private List<BookToc.mixToc.Chapters> mChapterList = new ArrayList<>();
    private ListPopupWindow mTocListPopupWindow;
    private TocListAdapter mTocListAdapter;

    private int currentChapter = 1;

    /**
     * 是否开始阅读章节
     **/
    private boolean startRead = false;

    /**
     * 朗读 播放器
     */
    private TTSPlayer mTtsPlayer;
    private TtsConfig ttsConfig;

    private PageWidget mPageWidget;
    private int curTheme = -1;
    private List<ReadTheme> themes;
    private ReadThemeAdapter gvAdapter;
    private Receiver receiver = new Receiver();
    private IntentFilter intentFilter = new IntentFilter();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public static final String INTENT_BEAN = "recommendBooksBean";

    private Recommend.RecommendBooks recommendBooks;

    private boolean isAutoLightness = false; // 记录其他页面是否自动调整亮度

    //添加收藏需要，所以跳转的时候传递整个实体类
    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(INTENT_BEAN, recommendBooks));
    }

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
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        showDialog();

        recommendBooks = (Recommend.RecommendBooks) getIntent().getSerializableExtra(INTENT_BEAN);

        mTvBookReadTocTitle.setText(recommendBooks.title);

        mTtsPlayer = TTSPlayerUtils.getTTSPlayer();
        ttsConfig = TTSPlayerUtils.getTtsConfig();

        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
    }

    @Override
    public void configViews() {
        initTocList();

        initAASet();

        initPagerWidget();

        mPresenter.attachView(this);
        mPresenter.getBookToc(recommendBooks._id, "chapters");
    }


    private void initTocList() {
        mTocListAdapter = new TocListAdapter(this, mChapterList, currentChapter);
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
                mTocListAdapter.setCurrentChapter(currentChapter);
                startRead = false;
                hideReadBar();
                showDialog();
                readCurrentChapter();
            }
        });
        mTocListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gone(mTvBookReadTocTitle);
                visible(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadChangeSource);
            }
        });
    }

    private void initAASet() {
        curTheme = SettingManager.getInstance().getReadTheme();
        ThemeManager.setReaderTheme(curTheme, mRlBookReadRoot);

        seekbarFontSize.setMax(40);
        seekbarFontSize.setProgress(ScreenUtils.pxToDpInt(SettingManager.getInstance().getReadFontSize(recommendBooks._id)));
        seekbarFontSize.setOnSeekBarChangeListener(new SeekBarChangeListener());

        seekbarLightness.setMax(100);
        seekbarLightness.setOnSeekBarChangeListener(new SeekBarChangeListener());
        seekbarLightness.setProgress(SettingManager.getInstance().getReadBrightness());
        isAutoLightness = ScreenUtils.isAutoBrightness(this);
        if (SettingManager.getInstance().isAutoBrightness()) {
            startAutoLightness();
        } else {
            stopAutoLightness();
        }

        cbVolume.setChecked(SettingManager.getInstance().isVolumeFlipEnable());
        cbVolume.setOnCheckedChangeListener(new ChechBoxChangeListener());

        cbAutoBrightness.setChecked(SettingManager.getInstance().isAutoBrightness());
        cbAutoBrightness.setOnCheckedChangeListener(new ChechBoxChangeListener());

        gvAdapter = new ReadThemeAdapter(this, (themes = ThemeManager.getReaderThemeData(curTheme)), curTheme);
        gvTheme.setAdapter(gvAdapter);
        gvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < themes.size() - 1) {
                    changedMode(false, position);
                } else {
                    changedMode(true, position);
                }
            }
        });
    }

    private void initPagerWidget() {
        mPageWidget = new PageWidget(this, recommendBooks._id, mChapterList, new ReadListener());// 页面
        registerReceiver(receiver, intentFilter);
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
        }
        flReadWidget.removeAllViews();
        flReadWidget.addView(mPageWidget);
    }

    /**
     * 读取currentChapter章节。章节文件存在则直接阅读，不存在则请求
     */
    public void readCurrentChapter() {
        if (CacheManager.getInstance().getChapterFile(recommendBooks._id, currentChapter) != null) {
            showChapterRead(null, currentChapter);
        } else {
            mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
        }
    }

    @Override
    public void showBookToc(List<BookToc.mixToc.Chapters> list) { // 加载章节列表
        mChapterList.clear();
        mChapterList.addAll(list);

        readCurrentChapter();
    }

    @Override
    public synchronized void showChapterRead(ChapterRead.Chapter data, int chapter) { // 加载章节内容
        if (data != null) {
            CacheManager.getInstance().saveChapterFile(recommendBooks._id, chapter, data);
        }

        if (!startRead) {
            startRead = true;
            if (!mPageWidget.isPrepared) {
                mPageWidget.init(curTheme);
            } else {
                mPageWidget.jumpToChapter(currentChapter);
            }
            hideDialog();
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
        gone(rlReadAaSet);
        ToastUtils.showToast("正在拼命开发中...");
    }

    @OnClick(R.id.tvBookReadCommunity)
    public void onClickCommunity() {
        gone(rlReadAaSet);
        BookDetailCommunityActivity.startActivity(this, recommendBooks._id, mTvBookReadTocTitle.getText().toString(), 0);
    }

    @OnClick(R.id.tvBookReadIntroduce)
    public void onClickIntroduce() {
        gone(rlReadAaSet);
        BookDetailActivity.startActivity(mContext, recommendBooks._id);
    }

    @OnClick(R.id.tvBookReadMode)
    public void onClickChangeMode() { // 日/夜间模式切换
        gone(rlReadAaSet);

        boolean isNight = !SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false);
        changedMode(isNight, -1);
    }

    private void changedMode(boolean isNight, int position) {
        SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, isNight);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);

        if (position >= 0) {
            curTheme = position;
        } else {
            curTheme = SettingManager.getInstance().getReadTheme();
        }
        gvAdapter.select(curTheme);

        mPageWidget.setTheme(isNight ? ThemeManager.NIGHT : curTheme);
        mPageWidget.setTextColor(ContextCompat.getColor(mContext, isNight ? R.color.chapter_content_night : R.color.chapter_content_day),
                ContextCompat.getColor(mContext, isNight ? R.color.chapter_title_night : R.color.chapter_title_day));

        mTvBookReadMode.setText(getString(isNight ? R.string.book_read_mode_day_manual_setting
                : R.string.book_read_mode_night_manual_setting));
        Drawable drawable = ContextCompat.getDrawable(this, isNight ? R.drawable.ic_menu_mode_day_manual
                : R.drawable.ic_menu_mode_night_manual);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvBookReadMode.setCompoundDrawables(null, drawable, null, null);
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

    @OnClick(R.id.tvBookReadDownload)
    public void downloadBook() {
        gone(rlReadAaSet);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("缓存多少章？")
                .setItems(new String[]{"后面五十章", "后面全部", "全部"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                DownloadBookService.post(new DownloadQueue(recommendBooks._id, mChapterList, currentChapter + 1, currentChapter + 50));
                                break;
                            case 1:
                                DownloadBookService.post(new DownloadQueue(recommendBooks._id, mChapterList, currentChapter + 1, mChapterList.size()));
                                break;
                            case 2:
                                DownloadBookService.post(new DownloadQueue(recommendBooks._id, mChapterList, 1, mChapterList.size()));
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }

    @OnClick(R.id.tvBookReadToc)
    public void onClickToc() {
        gone(rlReadAaSet);
        if (!mTocListPopupWindow.isShowing()) {
            visible(mTvBookReadTocTitle);
            gone(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadChangeSource);
            mTocListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mTocListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mTocListPopupWindow.show();
            mTocListPopupWindow.setSelection(currentChapter - 1);
            mTocListPopupWindow.getListView().setFastScrollEnabled(true);
        }
    }

    @OnClick(R.id.ivBrightnessMinus)
    public void brightnessMinus() {
        int curBrightness = SettingManager.getInstance().getReadBrightness();
        if (curBrightness > 2 && !SettingManager.getInstance().isAutoBrightness()) {
            seekbarLightness.setProgress((curBrightness = curBrightness - 2));
            ScreenUtils.setScreenBrightness(curBrightness, ReadActivity.this);
            SettingManager.getInstance().saveReadBrightness(curBrightness);
        }
    }

    @OnClick(R.id.ivBrightnessPlus)
    public void brightnessPlus() {
        int curBrightness = SettingManager.getInstance().getReadBrightness();
        if (curBrightness < 99 && !SettingManager.getInstance().isAutoBrightness()) {
            seekbarLightness.setProgress((curBrightness = curBrightness + 2));
            ScreenUtils.setScreenBrightness(curBrightness, ReadActivity.this);
            SettingManager.getInstance().saveReadBrightness(curBrightness);
        }
    }

    @OnClick(R.id.tvFontsizeMinus)
    public void fontsizeMinus() {
        int curFontSize = SettingManager.getInstance().getReadFontSize(recommendBooks._id);
        int curFontSizeDp = ScreenUtils.pxToDpInt(curFontSize);
        if (curFontSizeDp > 5) {
            seekbarFontSize.setProgress(--curFontSizeDp);
            mPageWidget.setFontSize(ScreenUtils.dpToPxInt(curFontSizeDp));
        }
    }

    @OnClick(R.id.tvFontsizePlus)
    public void fontsizePlus() {
        int curFontSize = SettingManager.getInstance().getReadFontSize(recommendBooks._id);
        int curFontSizeDp = ScreenUtils.pxToDpInt(curFontSize);
        if (curFontSizeDp < 40) {
            seekbarFontSize.setProgress(++curFontSizeDp);
            mPageWidget.setFontSize(ScreenUtils.dpToPxInt(curFontSizeDp));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDownProgress(DownloadProgress progress) {
        if (recommendBooks._id.equals(progress.bookId)) {
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
        if (recommendBooks._id.equals(complete.bookId)) {
            mTvDownloadProgress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvDownloadProgress.setText(getString(R.string.book_read_download_complete));
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
        if (recommendBooks._id.equals(error.bookId)) {
            mTvDownloadProgress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvDownloadProgress.setText(getString(R.string.book_read_download_error));
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
        ToastUtils.showSingleToast("加载失败");
        hideDialog();
    }

    @Override
    public void complete() {
        hideDialog();
    }

    /**
     * 显示加入书架对话框
     *
     * @param bean
     */
    private void showJoinBookShelfDialog(final Recommend.RecommendBooks bean) {
        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.book_read_add_book))
                .setMessage(getString(R.string.book_read_would_you_like_to_add_this_to_the_book_shelf))
                .setPositiveButton(getString(R.string.book_read_join_the_book_shelf),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                CollectionsManager.getInstance().add(bean);
                                ToastUtils.showToast(String.format(getString(
                                        R.string.book_detail_has_joined_the_book_shelf), bean.title));
                                EventBus.getDefault().post(new RefreshCollectionIconEvent());
                                EventBus.getDefault().post(new RefreshCollectionListEvent());
                                finish();
                            }
                        })
                .setNegativeButton(getString(R.string.book_read_not),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                .create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mTocListPopupWindow.isShowing()) {
                mTocListPopupWindow.dismiss();
                gone(mTvBookReadTocTitle);
                visible(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadChangeSource);
                return true;
            } else if (isVisible(rlReadAaSet)) {
                gone(rlReadAaSet);
                return true;
            } else if (isVisible(mLlBookReadBottom)) {
                hideReadBar();
                return true;
            } else if (!CollectionsManager.getInstance().isCollected(recommendBooks._id)) {
                showJoinBookShelfDialog(recommendBooks);
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            toggleReadBar();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // 防止翻页有声音
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                mPageWidget.nextPage();
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                mPageWidget.prePage();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PLAYING)
            mTtsPlayer.stop();
        EventBus.getDefault().unregister(this);
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            LogUtils.e("Receiver not registered");
        }
        if (isAutoLightness) {
            ScreenUtils.startAutoBrightness(ReadActivity.this);
        } else {
            ScreenUtils.stopAutoBrightness(ReadActivity.this);
        }
    }

    class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            LogUtils.i("onChapterChanged:" + chapter);
            currentChapter = chapter;
            mTocListAdapter.setCurrentChapter(currentChapter);
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter
                        && CacheManager.getInstance().getChapterFile(recommendBooks._id, i) == null) {
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
            startRead = false;
            if (CacheManager.getInstance().getChapterFile(recommendBooks._id, chapter) == null)
                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onCenterClick() {
            LogUtils.i("onCenterClick");
            toggleReadBar();
        }

        @Override
        public void onFlip() {
            hideReadBar();
        }
    }

    class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar.getId() == seekbarFontSize.getId() && fromUser) {
                mPageWidget.setFontSize(ScreenUtils.dpToPxInt(progress));
            } else if (seekBar.getId() == seekbarLightness.getId() && fromUser
                    && !SettingManager.getInstance().isAutoBrightness()) { // 非自动调节模式下 才可调整屏幕亮度
                ScreenUtils.setScreenBrightness(progress, ReadActivity.this);
                SettingManager.getInstance().saveReadBrightness(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class ChechBoxChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == cbVolume.getId()) {
                SettingManager.getInstance().saveVolumeFlipEnable(isChecked);
            } else if (buttonView.getId() == cbAutoBrightness.getId()) {
                if (isChecked) {
                    startAutoLightness();
                } else {
                    stopAutoLightness();
                }
            }
        }
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mPageWidget != null) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra("level", 0);
                    mPageWidget.setBattery(100 - level);
                } else if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                    mPageWidget.setTime(sdf.format(new Date()));
                }
            }
        }
    }

    private void startAutoLightness() {
        SettingManager.getInstance().saveAutoBrightness(true);
        ScreenUtils.startAutoBrightness(ReadActivity.this);
        seekbarLightness.setEnabled(false);
    }

    private void stopAutoLightness() {
        SettingManager.getInstance().saveAutoBrightness(false);
        ScreenUtils.stopAutoBrightness(ReadActivity.this);
        int value = SettingManager.getInstance().getReadBrightness();
        seekbarLightness.setProgress(value);
        ScreenUtils.setScreenBrightness(value, ReadActivity.this);
        seekbarLightness.setEnabled(true);
    }

}
