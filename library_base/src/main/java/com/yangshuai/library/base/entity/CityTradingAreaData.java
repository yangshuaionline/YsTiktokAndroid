package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 商圈信息表(关联CityAreaData)
 *
 * @author hcp
 * @created 2019-05-14
 */
public class CityTradingAreaData extends LitePalSupport implements Serializable {

    private String cityCode; // 城市代码
    private String cityName; // 城市名称
    private String dataStatus; // 数据状态 0无效 1有效
    private String districtCode; // 区县编号
    private String districtName; // 区县名称
    private String initialLetter; // 商圈首字母
    private String isEsf; // 是否二手房调用
    private String isNewhouse; // 是否新房调用
    private String isRent; // 是否租房调用 0未调用 1调用
    private String latitude; // 纬度
    private String longitude; // 经度
    private String orderNum; // 排序号
    private String tradingAreaId; // 商圈ID UUID
    private String tradingAreaName; // 商圈片区名称
    private String createdTm; // 创建时间(13位)
    private String updatedTm; // 更新时间
    private List<CityTradingAreaPosCoordData> posCoord; // 商圈地图标点边界值


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

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getInitialLetter() {
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    public String getIsEsf() {
        return isEsf;
    }

    public void setIsEsf(String isEsf) {
        this.isEsf = isEsf;
    }

    public String getIsNewhouse() {
        return isNewhouse;
    }

    public void setIsNewhouse(String isNewhouse) {
        this.isNewhouse = isNewhouse;
    }

    public String getIsRent() {
        return isRent;
    }

    public void setIsRent(String isRent) {
        this.isRent = isRent;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTradingAreaId() {
        return tradingAreaId;
    }

    public void setTradingAreaId(String tradingAreaId) {
        this.tradingAreaId = tradingAreaId;
    }

    public String getTradingAreaName() {
        return tradingAreaName;
    }

    public void setTradingAreaName(String tradingAreaName) {
        this.tradingAreaName = tradingAreaName;
    }

    public String getCreatedTm() {
        return createdTm;
    }

    public void setCreatedTm(String createdTm) {
        this.createdTm = createdTm;
    }

    public String getUpdatedTm() {
        return updatedTm;
    }

    public void setUpdatedTm(String updatedTm) {
        this.updatedTm = updatedTm;
    }

    public List<CityTradingAreaPosCoordData> getPosCoord() {
        return posCoord;
    }

    public void setPosCoord(List<CityTradingAreaPosCoordData> posCoord) {
        this.posCoord = posCoord;
    }
}
