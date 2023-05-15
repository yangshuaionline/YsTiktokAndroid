package com.yangshuai.library.base.widget.filter.model;

import java.io.Serializable;

/**
 * 更多筛选的每组对应子项的数据模型(例如：房屋用途中的 住宅、别墅、写字楼)
 *
 * @author hcp
 * @created 2019/4/2
 */
public class MoreFilterChildData implements Serializable {

    private String name; // 显示在UI上的名称
    private String value; // 传给服务器的值(当使用了服务器返回的筛选数据时，需要用这个将显示名称区分，如果用本地数据做筛选则直接传name)
    private boolean selected; // 是否选中
    private boolean isMultiple; // 是否支持多选
    private String groupName; // 所属组名

    public MoreFilterChildData() {
    }

    public MoreFilterChildData(String name) {
        this.name = name;
    }


    public MoreFilterChildData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public MoreFilterChildData(String name, String value, boolean multiple) {
        this.name = name;
        this.value = value;
        this.isMultiple = multiple;
    }

    public MoreFilterChildData(String name, String value, boolean multiple,String groupName) {
        this.name = name;
        this.value = value;
        this.isMultiple = multiple;
        this.groupName = groupName;
    }

    public MoreFilterChildData(String name, String value, boolean multiple,String groupName,boolean isCheck) {
        this.name = name;
        this.value = value;
        this.isMultiple = multiple;
        this.groupName = groupName;
        this.selected = isCheck;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
