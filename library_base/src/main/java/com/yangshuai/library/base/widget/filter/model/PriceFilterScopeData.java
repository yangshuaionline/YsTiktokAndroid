package com.yangshuai.library.base.widget.filter.model;

import java.io.Serializable;

/**
 * @author hcp
 * @created 2019-07-08
 */
public class PriceFilterScopeData implements Serializable {

    private String name; // 显示在UI上的名称
    private String minValue; // 最小值
    private String maxValue; // 最大值
    private boolean selected; // 是否选中
    private boolean isMultiple; // 是否支持多选

    public PriceFilterScopeData() {
    }

    public PriceFilterScopeData(String name, String minValue, String maxValue, boolean isMultiple) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.isMultiple = isMultiple;
    }

    public PriceFilterScopeData(String name,boolean isSelect, String minValue, String maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.selected = isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }
}
