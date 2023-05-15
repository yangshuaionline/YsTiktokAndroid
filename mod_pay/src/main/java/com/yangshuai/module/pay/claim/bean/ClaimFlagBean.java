package com.yangshuai.module.pay.claim.bean;

import android.text.TextUtils;

import com.yangshuai.library.base.BaseConst;

/**
 * @Author yangshuai
 * @Date : 2023-02-08 14:36
 * @Version 1.0
 * Description:  认领
 */
public class ClaimFlagBean {
    //状态
    // 0.正常
    // 1.代表有未验收的业务
    // 2.代表没有进行机构认证
    // 3.代表需要补充机构认证信息
    // 4.代表机构已认证处于待认证
    // 5.代表机构已认证处于认证中
    // 6.未实名未人脸
    // 7.已实名未人脸
    // 8.代表未签署著作权协议
    // 9.代表未同意著作权协议
    private int status;
    //状态描述 状态为7时：为人脸识别地址
    private String statusDesc;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return TextUtils.isEmpty(statusDesc)? BaseConst.NULL:statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
