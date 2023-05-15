package com.yangshuai.library.base.entity;

import java.util.List;

/**
 * @Author hcp
 * @Created 7/1/19
 * @Editor hcp
 * @Edited 7/1/19
 * @Type
 * @Layout
 * @Api
 */
public class OssCallbackRequest {
    private String userData;
    private List<UploadInfo> upload;


    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public List<UploadInfo> getUpload() {
        return upload;
    }

    public void setUpload(List<UploadInfo> upload) {
        this.upload = upload;
    }

    /**
     * 上传的文件信息
     */
    public static class UploadInfo {
        private String key;
        private String md5;
        private String suffix;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }
}

