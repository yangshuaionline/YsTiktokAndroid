package com.yangshuai.library.base.entity;

import java.io.Serializable;

public class HouseSourceBean implements Serializable {
    String name;
    String id;
    boolean ischeck;

    public HouseSourceBean(String name, String id, boolean ischeck) {
        this.name = name;
        this.id = id;
        this.ischeck = ischeck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
