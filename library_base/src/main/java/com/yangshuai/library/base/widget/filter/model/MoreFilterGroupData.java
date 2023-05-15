package com.yangshuai.library.base.widget.filter.model;

import java.io.Serializable;
import java.util.List;

/**
 * 更多筛选的组数据模型(例如房屋用途、房屋标记)
 *
 * @author hcp
 * @created 2019/4/2
 */
public class MoreFilterGroupData implements Serializable {

    private String groupTitle; // 组名
    private int column; // 列数
    private boolean isMultiple; // 该组是否支持多选
    private List<MoreFilterChildData> childs; // 每组对应的子项列表

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public List<MoreFilterChildData> getChilds() {
        return childs;
    }

    public void setChilds(List<MoreFilterChildData> childs) {
        this.childs = childs;
    }
}
