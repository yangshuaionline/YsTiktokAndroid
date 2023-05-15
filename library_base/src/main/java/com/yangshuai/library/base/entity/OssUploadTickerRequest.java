package com.yangshuai.library.base.entity;

/**
 * @Author hcp
 * @Created 2020/7/31
 * 获取上传文件时的票据
 *        userDir	需要获取存储目录, 目录结构统一规范，{项目名}/{模块名}/{业务线}/{房|客|合同}id/	body	TRUE	string
 *        applyId	oss bucket选择 0:普通 1:VR,不传默认为0	body	FALSE	integer(int64)
 *        uploadSize	需要上传的文件数量 默认1	body	FALSE	integer(int32)
 */
public class OssUploadTickerRequest {
    private String userDir;
    private String applyId;
    private int uploadSize;

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public int getUploadSize() {
        return uploadSize;
    }

    public void setUploadSize(int uploadSize) {
        this.uploadSize = uploadSize;
    }
}

