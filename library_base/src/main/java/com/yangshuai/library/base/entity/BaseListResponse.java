package com.yangshuai.library.base.entity;

/**
 * @Author hcp
 * @Created 2019-05-10
 * @Editor hcp
 * @Edited 2019-05-10
 * @Type
 * @Layout
 * @Api
 */
public class BaseListResponse<T> {
    private String code;
    private String msg;
    private BaseData<T> data;
    private boolean succeed;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseData<T> getData() {
        return data;
    }

    public void setData(BaseData<T> data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }
}
