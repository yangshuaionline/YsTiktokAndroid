package com.yangshuai.library.base.widget.filter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 区域筛选的数据模型
 *
 * @author hcp
 * @created 2019/4/3
 */
public class RegionFilterData implements Serializable {

    private RegionFilterData parent; // 父级数据
    private List<RegionFilterData> childs = new ArrayList<>(); // 子集数据
    private String name; // 菜单显示名称
    private String value; // 区域id,用于传给服务器 districtCode
    private String type; // 菜单类型:用于区分区域、地铁、附近
    private boolean isSelect; // 是否选中
    private boolean isShowCheckBox; // 是否显示CheckBox

    public RegionFilterData() {
    }

    public RegionFilterData(String name) {
        this.name = name;
    }

    public RegionFilterData(String type, boolean isSelect) {
        this.type = type;
        this.isSelect = isSelect;
    }

    public RegionFilterData(String name, String type, boolean isSelect) {
        this.name = name;
        this.type = type;
        this.isSelect = isSelect;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RegionFilterData getParent() {
        return parent;
    }

    public void setParent(RegionFilterData parent) {
        this.parent = parent;
    }

    public List<RegionFilterData> getChilds() {
        return childs;
    }

    public void setChilds(List<RegionFilterData> childs) {
        this.childs = childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
    }
}
