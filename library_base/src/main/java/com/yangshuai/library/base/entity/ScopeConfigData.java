package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 区间配置列表数据模型(上一层级CityScopeData)
 * @author hcp
 * @created 2019-07-09
 */
public class ScopeConfigData extends LitePalSupport implements Serializable {

    /**
     * scope : [{"unit":"㎡","name":"价格区间-单价(元/㎡)","type":"averageprice","value":"200000"},{"unit":"万","name":"价格区间(万)","type":"price","value":"50,80,100,120,150,200,300"},{"unit":"㎡","name":"面积区间(㎡)","type":"area","value":"500"}]
     * name : 普通住宅
     * type : purpose
     */
    private String channelClassname; // 数据所属类别(由上一级传入，方便做查询)
    private String cityCode; // 数据所属城市(由上一级传入)
    private String name;
    private String type;
    private List<ScopeData> scope;

    public String getChannelClassname() {
        return channelClassname;
    }

    public void setChannelClassname(String channelClassname) {
        this.channelClassname = channelClassname;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ScopeData> getScope() {
        return scope;
    }

    public void setScope(List<ScopeData> scope) {
        this.scope = scope;
    }
}
