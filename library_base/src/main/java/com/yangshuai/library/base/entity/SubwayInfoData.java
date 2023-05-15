package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 城市地铁信息
 * 接口地址 /erp.settings.api/esta/subway/citycodes
 * @author hcp
 * @created 2019-12-09
 */
public class SubwayInfoData extends LitePalSupport implements Serializable {

    /**
     * children : [{"children":[{"code":0,"isOpen":74970,"label":"","latitude":102,"longitude":156,"stationId":551980521759363200,"stationName":"","subwayId":73408,"subwayName":"1号线"}],"code":0,"isOpen":73189,"label":"","orderNum":53224,"subwayId":551980521759363200,"subwayName":"YDU"}]
     * cityCode : 0
     * cityId : 0
     * cityName :
     * code : 0
     * label :
     * provinceCode : 0
     * provinceName :
     */

    private String cityCode;
    private String cityId;
    private String cityName;
    private String code;
    private String label;
    private String provinceCode;
    private String provinceName;
    private List<SubwayLineData> children; // 城市对应的地铁线路列表

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public List<SubwayLineData> getChildren() {
        return children;
    }

    public void setChildren(List<SubwayLineData> children) {
        this.children = children;
    }
}
