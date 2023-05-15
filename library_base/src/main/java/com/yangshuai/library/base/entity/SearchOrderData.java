package com.yangshuai.library.base.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @author hcp
 * @date 2019/8/16
*/
public class SearchOrderData extends LitePalSupport implements Serializable {
    /**
     * 搜索
     */
    String searchText;
    /**
     * 时间
     */
    String time;
    /**
     * 订单搜索类型
     *
     *  1 订单管理
     *  2 新房订单
     */
    String orderType;

    public SearchOrderData(String searchText, String time) {
        this.searchText = searchText;
        this.time = time;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
