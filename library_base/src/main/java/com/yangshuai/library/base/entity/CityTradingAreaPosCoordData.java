package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 商圈地图标点边界值(关联CityTradingAreaData)
 *
 * @author hcp
 * @created 2019-05-14
 */
public class CityTradingAreaPosCoordData extends LitePalSupport implements Serializable {

    private String longitude; // 经度
    private String latitude; // 纬度

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
