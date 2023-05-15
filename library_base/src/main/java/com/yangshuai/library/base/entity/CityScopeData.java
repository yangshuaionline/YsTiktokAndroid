package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 城市区间数据
 *
 * @author hcp
 * @created 2019-07-09
 */
public class CityScopeData extends LitePalSupport implements Serializable {

    /**
     * channelClassname : 二手房
     * channelCode : esf
     * cityCode : 450100
     * cityName : 南宁
     * createdTm : 1556707530737
     * dataStatus : 1
     * scopeConfig : [{"scope":[{"unit":"㎡","name":"价格区间-单价(元/㎡)","type":"averageprice","value":"200000"},{"unit":"万","name":"价格区间(万)","type":"price","value":"50,80,100,120,150,200,300"},{"unit":"㎡","name":"面积区间(㎡)","type":"area","value":"500"}],"name":"普通住宅","type":"purpose"},{"scope":[{"unit":"㎡","name":"价格区间-单价(元/㎡)","type":"averageprice","value":"200000"},{"unit":"万","name":"价格区间(万)","type":"price","value":"50,80,100,120,150,200,300"},{"unit":"㎡","name":"面积区间(㎡)","type":"area","value":"50,100,160,200,300"}],"name":"别墅","type":"villa"},{"scope":[{"unit":"㎡","name":"价格区间-单价(元/㎡)","type":"averageprice","value":"100000"},{"unit":"万","name":"价格区间(万)","type":"price","value":"50,80,100,120,150,200,300"},{"unit":"㎡","name":"面积区间(㎡)","type":"area","value":"50,80,100,120,150,200,300"}],"name":"商铺","type":"shop"},{"scope":[{"unit":"㎡","name":"价格区间-单价(元/㎡)","type":"averageprice","value":"50000"},{"unit":"万","name":"价格区间(万)","type":"price","value":"50,80,100,120,150,200,300"},{"unit":"㎡","name":"面积区间(㎡)","type":"area","value":"100000"}],"name":"写字楼","type":"office"},{"scope":[{"unit":"万","name":"价格区间(万)","type":"price","value":"5,10,15,20,30"}],"name":"车位","type":"carport"},{"scope":[{"unit":"","name":"环线","type":"loopline","value":"1,2,3,4,5"}],"name":"范围","type":"scope"}]
     * scopeId : 560314645335167104
     * updatedTm : 1562465831849
     */

    private String channelClassname; // 类别名称:新房、二手房、租房
    private String channelCode; // 频道编码：new代表新房 esf代表二手房 rent代表租房
    private String cityCode; // 城市编号
    private String cityName; // 城市名称
    private String dataStatus; // 数据状态,0无效 1 有效
    private String scopeId; // UUID
    private String createdTm; // 创建时间
    private String updatedTm; // 最近更新时间
    private List<ScopeConfigData> scopeConfig; // 区间配置列表

    public String getChannelClassname() {
        return channelClassname;
    }

    public void setChannelClassname(String channelClassname) {
        this.channelClassname = channelClassname;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCreatedTm() {
        return createdTm;
    }

    public void setCreatedTm(String createdTm) {
        this.createdTm = createdTm;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getUpdatedTm() {
        return updatedTm;
    }

    public void setUpdatedTm(String updatedTm) {
        this.updatedTm = updatedTm;
    }

    public List<ScopeConfigData> getScopeConfig() {
        return scopeConfig;
    }

    public void setScopeConfig(List<ScopeConfigData> scopeConfig) {
        this.scopeConfig = scopeConfig;
    }
}
