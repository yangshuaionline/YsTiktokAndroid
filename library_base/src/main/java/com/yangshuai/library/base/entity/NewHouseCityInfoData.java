package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 城市基本信息表 - 对应API的返回实体
 * 接口地址 /erp.settings.api/esta/cityinfo/infobycitycode
 *
 * @author hcp
 * @created 2019-05-14
 */
public class NewHouseCityInfoData extends LitePalSupport implements Serializable {

    /**
     * cityCode : 0
     * cityDomain :
     * cityId : 0
     * cityName :
     * cityShort :
     * createdTm : 0
     * dataStatus : 0
     * estaDistrictInfoList : [{"cityCode":0,"cityName":"","createdTm":0,"dataStatus":0,"districtCode":0,"districtId":0,"districtName":"","districtShort":"","districtType":"","estaTradingAreaList":[{"cityCode":450100,"cityName":"南宁","createdTm":83315,"dataStatus":1,"districtCode":450107,"districtName":"西乡塘区","initialLetter":"fk","isEsf":0,"isNewhouse":0,"isRent":0,"latitude":139,"longitude":149,"orderNum":1,"posCoord":[{"longitude":"","latitude":""},{"longitude":"","latitude":""}],"tradingAreaId":551980521759363200,"tradingAreaName":"安宁","updatedTm":73526}],"initialLetter":"","isEsf":0,"isNewhouse":0,"isRent":0,"latitude":0,"longitude":0,"orderNum":0,"posCoord":[],"remark":"","updatedTm":0}]
     * initialLetter :
     * isEsf : 0
     * isNewhouse : 0
     * isRent : 0
     * latitude : 0
     * longitude : 0
     * provinceCode :
     * provinceName :
     * updatedTm : 0
     */

    private String cityCode;                                // 城市代码
    private String cityDomain;                              // 城市主域名
    private String cityId;                                  // 城市ID
    private String cityName;                                // 城市名称
    private String cityShort;                               // 城市拼音缩写
    private String code;                                    // 城市代码
    private String createdTm;                               // 创建时间
    private String dataStatus;                              // 数据状态,0无效 1 有效
    private String initialLetter;                           // 城市首字母
    private String isEsf;                                   // 是否开通二手房
    private String isNewhouse;                              // 是否开通新房
    private String isRent;                                  // 是否开通租房 0未开通 1开通
    private String latitude;                                // 纬度
    private String longitude;                               // 经度
    private String provinceCode;                            // 省份代码
    private String provinceName;                            // 省份名称
    private String updatedTm;                               // 最近更新时间(13位时间戳)
    private List<NewHouseCityAreaData> estaDistrictInfoList;        // 区县城区信息列表(对应旧版城区)


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityDomain() {
        return cityDomain;
    }

    public void setCityDomain(String cityDomain) {
        this.cityDomain = cityDomain;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityShort() {
        return cityShort;
    }

    public void setCityShort(String cityShort) {
        this.cityShort = cityShort;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getUpdatedTm() {
        return updatedTm;
    }

    public void setUpdatedTm(String updatedTm) {
        this.updatedTm = updatedTm;
    }

    public List<NewHouseCityAreaData> getEstaDistrictInfoList() {
        return estaDistrictInfoList;
    }

    public void setEstaDistrictInfoList(List<NewHouseCityAreaData> estaDistrictInfoList) {
        this.estaDistrictInfoList = estaDistrictInfoList;
    }
}
