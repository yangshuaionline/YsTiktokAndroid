package com.yangshuai.library.base.entity;

/**
 * @Author hcp
 * @Created 7/1/19
 * @Editor hcp
 * @Edited 7/1/19
 * @Type
 * @Layout
 * @Api
 */
public class OssCallbackResponse {

    private String fullUrl;
    private String id;
    private String key;
    private String status;
    private String userId;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
