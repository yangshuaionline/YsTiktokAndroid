package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 城区数据表(关联CityInfoData)
 *
 * @author hcp
 * @created 2019-05-14
 */
public class CityAreaData extends LitePalSupport implements Serializable {

    private String cityCode; // 城市代码
    private String cityName; // 城市名称
    private String code; // 区县编号
    private String createdTm; // 创建时间
    private String dataStatus; // 数据状态 0无效 1有效
    private String districtCode; // 区县编号
    private String districtId; // UUID
    private String districtName; // 区县名称
    private String districtShort; // 区县拼音缩写
    private String districtType; // 区县属性：本地、异地、周边
    private String initialLetter; // 区县首字母
    private String isEsf; // 是否二手房调用
    private String isNewhouse; // 是否新房调用
    private String isRent; // 是否租房调用
    private String label; // 区县名称
    private String latitude; // 纬度
    private String longitude; // 经度
    private String orderNum; // 排序号
    private String remark; // 描述
    private String updatedTm; // 最新更新时间(13位时间戳)
    private List<CityTradingAreaData> estaTradingAreaList = new ArrayList<>(); // 城区内的商圈信息(对应旧版片区)
    private List<CityArerPosCoordData> posCoord = new ArrayList<>(); // 城区地图标点集合

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

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictShort() {
        return districtShort;
    }

    public void setDistrictShort(String districtShort) {
        this.districtShort = districtShort;
    }

    public String getDistrictType() {
        return districtType;
    }

    public void setDistrictType(String districtType) {
        this.districtType = districtType;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdatedTm() {
        return updatedTm;
    }

    public void setUpdatedTm(String updatedTm) {
        this.updatedTm = updatedTm;
    }

    public List<CityTradingAreaData> getEstaTradingAreaList() {
        return estaTradingAreaList;
    }

    public void setEstaTradingAreaList(List<CityTradingAreaData> estaTradingAreaList) {
        this.estaTradingAreaList = estaTradingAreaList;
    }

    public List<CityArerPosCoordData> getPosCoord() {
        return posCoord;
    }

    public void setPosCoord(List<CityArerPosCoordData> posCoord) {
        this.posCoord = posCoord;
    }
}
