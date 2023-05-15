package com.yangshuai.library.base.appinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.yangshuai.library.base.utils.AppVersionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author hcp
 * @Created 6/21/19
 * @Editor hcp
 * @Edited 6/21/19
 * @Type
 * @Layout
 * @Api
 */
public class AppInfoManager {

    // 服务器返回最新的app版本信息
    private static AppInfo appInfo;
    private static String userAgent;//YJYZERP/1.0.0 (HUAWEI_NXT-AL10; Android 7.0; zh_CN)

    public static final String STORE_NAME = "app_info";
    // 编译版本号
    public static final String KEY_VERSION_CODE = "KEY_VERSION_CODE";
    // 提示类型 1:一直提示 2：只提示一次 3：一天提示一次
    public static final int ALWAYS_NOTICE_TYPE = 1;
    public static final int ONECE_NOTICE_TYPE = 2;
    public static final int DAY_NOTICE_TYPE = 3;
    public static final int NONE_NOTICE_TYPE = 0;
    // 上一次提示日期 20190808
    public static final String KEY_LAST_NOTICE_DATE = "KEY_LAST_NOTICE_DATE";

    private AppInfoManager() {
    }

    /**
     * 检查是否需要提示
     *
     * @param context
     * @return
     */
    public static boolean checkNeedNotice(Context context, int versionCode, int type) {

        if (type == ALWAYS_NOTICE_TYPE) return true;

        SharedPreferences sp = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int v = sp.getInt(KEY_VERSION_CODE, 0);
        int lastDate = sp.getInt(KEY_LAST_NOTICE_DATE, 0);
        int now = getNowDate();
        // 此版本更新未提示过
        if (versionCode != v) {
            if (ONECE_NOTICE_TYPE == type || DAY_NOTICE_TYPE == type) {
                editor.putInt(KEY_VERSION_CODE, versionCode);
                editor.putInt(KEY_LAST_NOTICE_DATE, now);
                editor.apply();
                return true;
            } else {
                return false;
            }
        }

        // 记录这一次提示的时间
        if (DAY_NOTICE_TYPE == type && lastDate != now) {
            editor.putInt(KEY_LAST_NOTICE_DATE, now);
            editor.apply();
            return true;
        }

        return false;
    }

    public static int getNowDate() {

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = simpleDateFormat.format(new Date());
            int dateInt = Integer.parseInt(dateStr);
            return dateInt;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static AppInfo getLastestAppInfo() {
        return appInfo;
    }

    public static void setLastestAppInfo(AppInfo appInfo) {
        if (appInfo != null)
            AppInfoManager.appInfo = appInfo;
    }

    /**
     * 获取请求使用的useragent
     *
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {
        if (userAgent == null) {
            StringBuilder stringBuilder = new StringBuilder("YJYZERP");
            String appVersion = AppVersionUtil.getVersion(context);
            String systemVersion = getSystemVersion();
            String systemModel = getSystemModel();
            String languageCountry = getSystemLanguageCountry();
            stringBuilder.append("/")
                    .append(appVersion)
                    .append(" (")
                    .append(systemModel).append("; ")
                    .append("Android ").append(systemVersion).append("; ")
                    .append(languageCountry)
                    .append(")");
            userAgent = stringBuilder.toString();
        }

        return userAgent;
    }

    /**
     * 获取系统版本
     *
     * @return 7.0
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return HUAWEI_NXT-AL10
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取语言Country
     *
     * @return zh_CN
     */
    public static String getSystemLanguageCountry() {
        return "" + Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();//zh_CN
    }
}
