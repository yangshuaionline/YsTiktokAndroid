package com.yangshuai.library.base.appinfo;

/**
 * @Author zrh
 * @Created 11/24/19
 * @Editor hcp
 * @Edited 11/24/19
 * @Type
 * @Layout
 * @Api
 */
public class ApkDownloadEvent {
    private AppInfo appInfo;

    public ApkDownloadEvent(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
