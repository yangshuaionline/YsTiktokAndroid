package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 每个房源类型对应的数据区间列表模型(上一层级ScopeConfigData)
 * @author hcp
 * @created 2019-07-09
 */
public class ScopeData extends LitePalSupport implements Serializable {

    /**
     * unit : ㎡
     * name : 价格区间-单价(元/㎡)
     * type : averageprice
     * value : 200000
     */
    private String unit;
    private String name;
    private String type;
    private String value;
    private String channelClassname; // 数据所属类别(由上一级传入，方便做查询)
    private String cityCode; // 数据所属城市(由上一级传入)

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getChannelClassname() {
        return channelClassname;
    }

    public void setChannelClassname(String channelClassname) {
        this.channelClassname = channelClassname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
