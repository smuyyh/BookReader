/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.justwayward.reader.base.Constant;
import com.justwayward.reader.base.CrashHandler;
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
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class ReaderApplication extends Application {

    private static ReaderApplication sInstance;
    private AppComponent appComponent;

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        ReaderApplication application = (ReaderApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        sInstance = this;
        initCompoent();
        AppUtils.init(this);
        CrashHandler.getInstance().init(this);
        initPrefs();
        initNightMode();
        //initHciCloud();
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
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    protected void initHciCloud() {
        InitParam initparam = new InitParam();
        String authDirPath = getFilesDir().getAbsolutePath();
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_AUTH_PATH, authDirPath);
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_AUTO_CLOUD_AUTH, "no");
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_CLOUD_URL, "test.api.hcicloud.com:8888");
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_DEVELOPER_KEY, "0a5e69f8fb1c019b2d87a17acf200889");
        initparam.addParam(InitParam.AuthParam.PARAM_KEY_APP_KEY, "0d5d5466");
        String logDirPath = FileUtils.createRootPath(this) + "/hcicloud";
        FileUtils.createDir(logDirPath);
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_FILE_PATH, logDirPath);
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_FILE_COUNT, "5");
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_FILE_SIZE, "1024");
        initparam.addParam(InitParam.LogParam.PARAM_KEY_LOG_LEVEL, "5");
        int errCode = HciCloudSys.hciInit(initparam.getStringConfig(), this);
        if (errCode != HciErrorCode.HCI_ERR_NONE) {
            LogUtils.e("HciCloud初始化失败" + errCode);
            return;
        }
        LogUtils.e("HciCloud初始化成功");
    }
}
