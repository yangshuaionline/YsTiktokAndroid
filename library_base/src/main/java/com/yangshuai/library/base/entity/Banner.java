package com.yangshuai.library.base.entity;

import java.io.Serializable;

/**
 * @author hcp
 * Create on 2020-05-11 09:52
 */
public class Banner implements Serializable {
    /**
     * autoFields : {	"title":"标题，我是一个广告isAdvertising是1了","sum":"摘要金三胖跳广场舞","url":"https://www.baidu.com/","isAd":false,"filed1":"imgurl","filed2":"2000元/月"}
     * cityCode : 440100
     * createBy : 1
     * createName : feng
     * createTm : 1589006908131
     * endTm : 99999999999999999
     * id : 708571071795695616
     * isAdvertising : 1
     * operateId : 708192676356296704
     * sort : 0
     * startTm : 22222
     * status : 0
     * updateBy : 0
     * updateName :
     * updateTm : 1589006908131
     */

    private String autoFields;
    private String cityCode;
    private String createBy;
    private String createName;
    private String createTm;
    private String endTm;
    private String id;
    private int isAdvertising;
    private String operateId;
    private int sort;
    private String startTm;
    private int status;
    private String updateBy;
    private String updateName;
    private String updateTm;



    public String getAutoFields() {
        return autoFields;
    }

    public void setAutoFields(String autoFields) {
        this.autoFields = autoFields;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTm() {
        return createTm;
    }

    public void setCreateTm(String createTm) {
        this.createTm = createTm;
    }

    public String getEndTm() {
        return endTm;
    }

    public void setEndTm(String endTm) {
        this.endTm = endTm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsAdvertising() {
        return isAdvertising;
    }

    public void setIsAdvertising(int isAdvertising) {
        this.isAdvertising = isAdvertising;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStartTm() {
        return startTm;
    }

    public void setStartTm(String startTm) {
        this.startTm = startTm;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateTm() {
        return updateTm;
    }

    public void setUpdateTm(String updateTm) {
        this.updateTm = updateTm;
    }
}
