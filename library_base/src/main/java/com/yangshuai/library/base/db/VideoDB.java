package com.yangshuai.library.base.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * @Author yangshuai
 * @Date 2023-04-18 周二 15:11
 * @Description 短视频表DB数据
 */
public class VideoDB extends LitePalSupport {
    //unique唯一性
    //defaultValue默认值
    //nullable可以为空
    //ignore是否忽略错误
    @Column(unique = true)
    private int key;//唯一键
    @Column
    private String url;//视频播放地址
    @Column
    private String img;//视频占位图地址
    @Column
    private boolean isAgree;//是否点赞
    @Column
    private int agree;//点赞数量
    @Column
    private boolean isCollect;//是否收藏
    @Column
    private int collect;//收藏数

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isAgree() {
        return isAgree;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }
}
