package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 搜素数据
 * @author hcp
 */
public class NewHouseSearchData extends LitePalSupport implements Serializable {

    String searchStr;
    String time;
    String varr1;
    String varr2;
    int id;

    public NewHouseSearchData(String searchStr, String varr1,String time) {
        this.searchStr = searchStr;
        this.time = time;
        this.varr1 =varr1;
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
}