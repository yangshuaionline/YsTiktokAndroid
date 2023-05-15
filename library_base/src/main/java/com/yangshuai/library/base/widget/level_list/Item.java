package com.yangshuai.library.base.widget.level_list;

import java.util.ArrayList;
import java.util.List;

/**
 * 分级列表项数据： 用于控制item的状态
 *
 * @Author hcp
 * @Created 3/22/19
 * @Editor hcp
 * @Edited 3/22/19
 * @Type
 * @Layout
 * @Api
 */
public class Item {

    private Object data;

    // 显示的文本
    private String name;
    /**
     * 该item的等级
     * 具体类型见{@link com.yangshuai.library.base.widget.level_list.Level}
     */
    private int level;
    // item的唯一标识
    private String id;
    // 依赖的上级的id
    private String parentId;
    // 拥有的下级item
    private List<Item> children = new ArrayList<>();
    // 控制是否展开下级，默认不展开
    private boolean expanded = false;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Item> getChildren() {
        return children;
    }

    public void setChildren(List<Item> children) {
        this.children = children;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
