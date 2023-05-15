package com.yangshuai.library.base.entity;


import android.text.TextUtils;

/**
 * @Author hcp
 * @Created 3/29/19
 * @Editor hcp
 * @Edited 3/29/19
 * @Type
 * @Layout
 * @Api
 */
public class BaseResponse<T> {

    private String errorCode;
    private String errorMsg;
    private T data;
    private boolean succeed;
    private String requestId;//碰到请求失败的，在 提示信息里把这个信息打印一下吧 方便后端定位问题



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorCode() {
        if (TextUtils.isEmpty(errorCode)) {
            return "";
        } else {
            return errorCode;
        }
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        if (TextUtils.isEmpty(errorMsg)) {
            return "";
        } else {
            return errorMsg;
        }
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
