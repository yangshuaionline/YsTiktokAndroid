package com.yangshuai.library.base.entity;

/**
 * Time:$[DATE]
 * Author:ThinkPad
 * <p>
 * 参数名称	说明	请求类型	必填	类型	schema
 *    appInfoDto	appInfoDto	body	TRUE	AppInfoDto	AppInfoDto
 *        updateType		body	FALSE	integer(int32)
 *        appType		body	FALSE	integer(int32)
 *        appVersion		body	FALSE	string
 *        appVersionCode		body	FALSE	integer(int32)
 *        channel		body	FALSE	string
 *        createBy		body	FALSE	integer(int64)
 *        createTime		body	FALSE	integer(int64)
 *        describe		body	FALSE	string
 *        downUrl		body	FALSE	string
 *        id		body	FALSE	integer(int64)
 *        appName		body	FALSE	string
 *        loginCode		body	FALSE	string
 *        packageName		body	FALSE	string
 *        pageNo		body	FALSE	integer(int32)
 *        pageSize		body	FALSE	integer(int32)
 *        status		body	FALSE	integer(int32)
 *        systemType		body	FALSE	integer(int32)
 *        systemVersion		body	FALSE	string
 *        updateBy		body	FALSE	integer(int64)
 *        updateTime		body	FALSE	integer(int64)
 *        iosAppId		body	FALSE	string
 */
public class AppInfoDtoRequest {
    public int updateType;
    public int appType;
    public String appVersion;
    public int appVersionCode;
    public String channel;
    public long createBy;
    public long createTime;
    public String describe;
    public String downUrl;
    public int id;
    public String appName;
    public String loginCode;
    public String packageName;
    public int pageNo;
    public int pageSize;
    public int status;
    public int systemType;
    public String systemVersion;
    public int updateBy;
    public long updateTime;
    public String iosAppId;

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getIosAppId() {
        return iosAppId;
    }

    public void setIosAppId(String iosAppId) {
        this.iosAppId = iosAppId;
    }
}
