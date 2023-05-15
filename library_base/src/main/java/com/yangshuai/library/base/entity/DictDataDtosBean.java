package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

/**
 * @Author hcp
 * @Created 2019-05-10
 * @Editor hcp
 * @Edited 2019-05-10
 * @Type
 * @Layout
 * @Api
 */
public class DictDataDtosBean extends LitePalSupport {
    /**
     * dictDataName : 58同城
     * dictTypeId : 574593205578465280
     * id : 574593205649768448
     * lastUpdateTime : 1557064095651
     * sort : 1
     * status : 0
     * val : 1
     * */


    private String dictDataName;
    private String dictTypeId;
    private long id;
    private String lastUpdateTime;
    private int sort;
    private int status;
    private String val;
    private String remarks;

    public String getDictDataName() {
        return dictDataName;
    }

    public void setDictDataName(String dictDataName) {
        this.dictDataName = dictDataName;
    }

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
