package com.yangshuai.library.base.config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.yangshuai.library.base.BuildConfig;
import com.yangshuai.library.base.utils.StringUtils;
/**
 * APP的基础配置(域名等)
 *
 * @author hcp
 * @created 2019-07-02
 */
public class AppBaseConfig {

    private static AppBaseConfig mBaseConfig;
    private Application mApplication;
    private Config mConfig;

    private static SharedPreferences sharedPreferences;

    public static AppBaseConfig get() {
        if (mBaseConfig == null) {
            mBaseConfig = new AppBaseConfig();
        }

        return mBaseConfig;
    }

    public void init(Application application, Config config) {
        this.mApplication = application;
        this.mConfig = config;
        sharedPreferences = application.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String loadString(String key) {
        return sharedPreferences.getString(key, null);
    }


    public static class Config {
        private int versionCode; // 版本号
        private String versionName; // 版本名
        private String applicationId; // 应用ID

        private boolean debug; // 是否为debug模式
        public String baseUrl; // 边缘服务API Host
        private String msgBaseUrl; // 消息服务API Host

        public Config() {

        }

        public Config(int versionCode, String versionName, String applicationId, boolean debug, String baseUrl, String msgBaseUrl) {
            this.versionCode = versionCode;
            this.versionName = versionName;
            this.applicationId = applicationId;
            this.debug = debug;
            this.baseUrl = baseUrl;
            this.msgBaseUrl = msgBaseUrl;
        }


        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getMsgBaseUrl() {
            try {// 方便测试环境 可切换 prod 消息
                if (BuildConfig.DEBUG && (StringUtils.isEmpty(baseUrl)
                        || "https://api-beta.yjyz.com".equals(AppBaseConfig.get().loadString("baseUrl")))) {
                    return "wss://api-beta.yjyz.com";
                } else {
                    return "wss://api.yjyz.com";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return msgBaseUrl;
            }
        }

        public void setMsgBaseUrl(String msgBaseUrl) {
            this.msgBaseUrl = msgBaseUrl;
        }
    }

    public Config getConfig() {
        if (mConfig==null) mConfig = new Config();
        return mConfig;
    }

    public void setConfig(Config mConfig) {
        this.mConfig = mConfig;
    }


    public Application getApplication() {
        return mApplication;
    }
}
