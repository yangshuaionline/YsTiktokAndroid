package com.yangshuai.library.base.entity;

import java.io.Serializable;

/**
 * @author hcp
 * Create on 2020-02-06 13:48
 */
public class AnnounceBean implements Serializable {

    /**
     * content :
     * count : 0
     * title :
     */

    private String content;
    private int count;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
