package com.yangshuai.library.base.account;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * @Author hcp
 * @Created 5/9/19
 * @Editor hcp
 * @Edited 5/9/19
 * @Type
 * @Layout
 * @Api
 */
public class UserInfo {
    // 公司名称
    @SerializedName(value = "branchName")
    private String companyName;
    // 公司ID
    @SerializedName(value = "branchId")
    private String companyId;
    // 城市编码
    private String cityCode;
    // 城市名称
    private String cityName;
    // 性别
    private String sex;
    // 职位
    @SerializedName(value = "positionName")
    private String position;
    // 用户名
    private String userName;
    // 真实名
    private String realName;
    // 头像
    private String avatar;
    // 电话
    private String mobile;
    // 品牌公司
    private String brandName;
    private String brandId;
    // 用户ID
    private String employeesId;
    //实人认证状态 -1:未认证 0：认证失败  1：已认证  2:已过期
    private String realNameAuth;
    //员工是否在职 1在职 2 离职 3 待审 4 否决 5 待提交 6 已失效
    private int status;
    //身份验证框关闭开关 1 可以关闭 0 不可以关闭
    private int exitSwitch;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(String employeesId) {
        this.employeesId = employeesId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSex() {
        if (TextUtils.isEmpty(sex)){
            sex = "";
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealNameAuth() {
        return realNameAuth;
    }

    public void setRealNameAuth(String realNameAuth) {
        this.realNameAuth = realNameAuth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExitSwitch() {
        return exitSwitch;
    }

    public void setExitSwitch(int exitSwitch) {
        this.exitSwitch = exitSwitch;
    }
}
