package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 地铁线路信息(关联SubwayInfoData)
 *
 * @author hcp
 * @created 2019-12-09
 */
public class SubwayLineData extends LitePalSupport implements Serializable {

    private String cityCode; // 城市代码
    private String cityName; // 城市名称
    private String code; // 地铁线路ID
    private String isOpen; // 地铁线路是否已开通
    private String label; // 地铁线名称
    private String orderNum; // 排序
    private String subwayId; // UUID
    private String subwayName; // 地铁线名称
    private List<SubwayStationData> children; // 地铁线路对应的站点列表

    public List<SubwayStationData> getChildren() {
        return children;
    }

    public void setChildren(List<SubwayStationData> children) {
        this.children = children;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSubwayId() {
        return subwayId;
    }

    public void setSubwayId(String subwayId) {
        this.subwayId = subwayId;
    }

    public String getSubwayName() {
        return subwayName;
    }

    public void setSubwayName(String subwayName) {
        this.subwayName = subwayName;
    }
}
