package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 地铁站点信息(关联SubwayLineData)
 *
 * @author hcp
 * @created 2019-12-09
 */
public class SubwayStationData extends LitePalSupport implements Serializable {

    private String cityCode; // 城市代码
    private String cityName; // 城市名称
    private String code; // 站点代码
    private String isOpen; // 站点是否开通 0 未开通 1 开通
    private String label; // 站点名称
    private String latitude; // 纬度
    private String longitude; // 经度
    private String stationId; // UUID
    private String stationName; // 站点名称
    private String subwayId; // 地铁线路ID
    private String subwayName; // 地铁线路名称

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
