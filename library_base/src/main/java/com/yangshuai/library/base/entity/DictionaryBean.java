package com.yangshuai.library.base.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 字典数据模型
 *
 * @Author hcp
 * @Created 2019-05-09
 * @Editor hcp
 * @Edited 2019-05-09
 * @Type
 * @Layout
 * @Api
 */
public class DictionaryBean extends LitePalSupport implements Serializable {

    /**
     * code : property_source
     * dictDataDtos : [{"dictDataName":"58同城","dictTypeId":"574593205578465280","id":"574593205649768448","lastUpdateTime":"1557064095651","sort":1,"status":0,"val":"1"},{"dictDataName":"搜房网","dictTypeId":"574593205578465280","id":"574593205750431744","lastUpdateTime":"1557064095675","sort":2,"status":0,"val":"2"},{"dictDataName":"赶集网","dictTypeId":"574593205578465280","id":"574593205867872256","lastUpdateTime":"1557064095704","sort":3,"status":0,"val":"3"},{"dictDataName":"百姓网","dictTypeId":"574593205578465280","id":"574593205960146944","lastUpdateTime":"1557064095726","sort":4,"status":0,"val":"4"},{"dictDataName":"新浪乐居","dictTypeId":"574593205578465280","id":"574593206052421632","lastUpdateTime":"1557064095748","sort":5,"status":0,"val":"5"},{"dictDataName":"安居客","dictTypeId":"574593205578465280","id":"574593206144696320","lastUpdateTime":"1557064095770","sort":6,"status":0,"val":"6"},{"dictDataName":"来电","dictTypeId":"574593205578465280","id":"574593206236971008","lastUpdateTime":"1557064095791","sort":7,"status":0,"val":"7"},{"dictDataName":"来访","dictTypeId":"574593205578465280","id":"574593206325051392","lastUpdateTime":"1557064095812","sort":8,"status":0,"val":"8"},{"dictDataName":"朋友介绍","dictTypeId":"574593205578465280","id":"574593206413131776","lastUpdateTime":"1557064095834","sort":9,"status":0,"val":"9"},{"dictDataName":"扫街","dictTypeId":"574593205578465280","id":"574593206501212160","lastUpdateTime":"1557064095855","sort":10,"status":0,"val":"10"},{"dictDataName":"举牌","dictTypeId":"574593205578465280","id":"574593206593486848","lastUpdateTime":"1557064095877","sort":11,"status":0,"val":"11"},{"dictDataName":"蹲点","dictTypeId":"574593205578465280","id":"574593206685761536","lastUpdateTime":"1557064095898","sort":12,"status":0,"val":"12"},{"dictDataName":"优居","dictTypeId":"574593205578465280","id":"574593206773841920","lastUpdateTime":"1557064095920","sort":13,"status":0,"val":"13"},{"dictDataName":"转介绍","dictTypeId":"574593205578465280","id":"574593206866116608","lastUpdateTime":"1557064095941","sort":14,"status":0,"val":"14"}]
     * dictType : 1
     * dictTypeName : 房源来源
     * id : 574593205578465280
     * lastUpdTime : 1557217061408
     * lastUpdUser : 574851825620713472
     * status : 0
     */

    @Column(unique = true)
    private String code;

    private int dictType;
    private String dictTypeName;
    private long id;
    private String lastUpdTime;
    private String lastUpdUser;
    private int status;
    private String remarks;
    private List<DictDataDtosBean> dictDataDtos;

    public List<DictDataDtosBean> getDictDataDtos() {
        return dictDataDtos;
    }

    public void setDictDataDtos(List<DictDataDtosBean> dictDataDtos) {
        this.dictDataDtos = dictDataDtos;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDictType() {
        return dictType;
    }

    public void setDictType(int dictType) {
        this.dictType = dictType;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(String lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public String getLastUpdUser() {
        return lastUpdUser;
    }

    public void setLastUpdUser(String lastUpdUser) {
        this.lastUpdUser = lastUpdUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
