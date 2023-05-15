package com.yangshuai.module.main.entity;

import com.google.gson.annotations.SerializedName;

public class CompanyBean {
    @SerializedName(value = "branchId")
    private String companyId;
    private String cityName;
    private String cityCode;
    private String brandId;
    @SerializedName(value = "branchName")
    private String companyName;
    @SerializedName(value = "brandName")
    private String rootNodeName;
    private Integer indChannelInvite;

    public Integer getIndChannelInvite() {
        return indChannelInvite;
    }

    public void setIndChannelInvite(Integer indChannelInvite) {
        this.indChannelInvite = indChannelInvite;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBrandId() {
        return this.brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRootNodeName() {
        return this.rootNodeName;
    }

    public void setRootNodeName(String rootNodeName) {
        this.rootNodeName = rootNodeName;
    }
}
