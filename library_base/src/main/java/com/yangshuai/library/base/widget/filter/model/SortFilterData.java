package com.yangshuai.library.base.widget.filter.model;

import java.io.Serializable;

/**
 * 排序筛选数据模型
 *
 * @author hcp
 * @created 2019/4/1
 */
public class SortFilterData implements Serializable {

    private String name; // 显示的名称
    private String value;// 对应的传参值
    private boolean select; // 是否选中

    public SortFilterData(){};

    public SortFilterData(String name, String value, boolean select) {
        this.name = name;
        this.value = value;
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
