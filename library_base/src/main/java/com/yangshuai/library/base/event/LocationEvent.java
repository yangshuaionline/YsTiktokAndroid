package com.yangshuai.library.base.event;

/**
 * @author hcp
 * @created 2020-08-27
 */
public class LocationEvent {

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

    public LocationEvent() {
    }
}
