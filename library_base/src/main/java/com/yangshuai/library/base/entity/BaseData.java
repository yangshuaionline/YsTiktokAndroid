package com.yangshuai.library.base.entity;

import java.util.List;

/**
 * @Author hcp
 * @Created 2019-05-10
 * @Editor hcp
 * @Edited 2019-05-10
 * @Type
 * @Layout
 * @Api
 */
public class BaseData<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
