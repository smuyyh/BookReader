package com.justwayward.reader;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.justwayward.reader.base.Constant;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerAppComponent;
import com.justwayward.reader.module.AppModule;
import com.justwayward.reader.module.BookApiModule;
import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.SharedPreferencesUtil;
import com.sinovoice.hcicloudsdk.api.HciCloudSys;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.InitParam;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class ReaderApplication extends Application {

    private static ReaderApplication sInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;
        initCompoent();
        AppUtils.init(this);
        initPrefs();
        initNightMode();
        initHciCloud();
    }

    public static ReaderApplication getsInstance() {
        return sInstance;
    }

    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .bookApiModule(new BookApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    protected void initNightMode() {
        boolean isNight = SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false);
        LogUtils.d("isNight=" + isNight);
        if (isNight) {
            //使用夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            //不使用夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    protected void initHciCloud() {
        // 创建初始化参数辅助类
        InitParam initparam = new InitParam();
        // 授权文件所在路径，此项必填
        String authDirPath = getFilesDir().getAbsolutePath();
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_AUTH_PATH, authDirPath);
        // 是否自动访问云授权
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_AUTO_CLOUD_AUTH, "no");
        // 灵云云服务的接口地址，此项必填
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_CLOUD_URL, "test.api.hcicloud.com:8888");
        // 开发者密钥，此项必填，由捷通华声提供
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_DEVELOPER_KEY, "0a5e69f8fb1c019b2d87a17acf200889");
        // 应用程序序号，此项必填，由捷通华声提供
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_APP_KEY, "0d5d5466");
        // 日志的路径，可选，如果不传或者为空则不生成日志
        String logDirPath = FileUtils.createRootPath(this)+"/hcicloud";
        FileUtils.createDir(logDirPath);
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_FILE_PATH, logDirPath);
        // 日志数目，默认保留多少个日志文件，超过则覆盖最旧的日志
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_FILE_COUNT, "5");
        // 日志大小，默认一个日志文件写多大，单位为K
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_FILE_SIZE, "1024");
        // 日志等级，0=无，1=错误，2=警告，3=信息，4=细节，5=调试，SDK将输出小于等于logLevel的// 日志信息
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_LEVEL, "5");
        // 灵云系统初始化 第二个参数在Android平台下，必须为当前的Context，在Windows/Linux平台下，可以为null
        int errCode = HciCloudSys.hciInit(initparam.getStringConfig(), this);
        if (errCode != HciErrorCode.HCI_ERR_NONE) {
            LogUtils.e("HciCloud初始化失败"+errCode);
            return;
        }
        LogUtils.e("HciCloud初始化成功");
    }
}
