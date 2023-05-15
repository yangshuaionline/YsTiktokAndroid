package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 搜索历史数据模型
 *
 * @author hcp
 */
public class SearchData extends LitePalSupport implements Serializable {

    String searchStr; // 搜索关键字或楼盘名称
    String time;
    String varr1;
    String varr2;
    int id;
    String estateId; // 楼盘ID


    public SearchData(String searchStr, String time, int id) {
        this.searchStr = searchStr;
        this.time = time;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVarr1() {
        return varr1;
    }

    public void setVarr1(String varr1) {
        this.varr1 = varr1;
    }

    public String getVarr2() {
        return varr2;
    }

    public void setVarr2(String varr2) {
        this.varr2 = varr2;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }
}
