package com.yangshuai.library.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Picture implements Serializable, Parcelable {
    private String bucket;
    @SerializedName(value = "_size")
    private int size;
    @SerializedName(value = "size")
    private String sizeStr;
    private String name;
    private String type;
    private String url;
    private boolean isCover;

    public Picture(String bucket, int size, String name, String type, String url, boolean isCover) {
        this.bucket = bucket;
        this.size = size;
        this.name = name;
        this.type = type;
        this.url = url;
        this.isCover = isCover;
    }

    public Picture() {

    }

    public String getSizeStr() {
        return sizeStr;
    }

    public void setSizeStr(String sizeStr) {
        this.sizeStr = sizeStr;
    }

    public boolean isCover() {
        return isCover;
    }

    public void setCover(boolean cover) {
        isCover = cover;
    }


    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bucket);
        dest.writeInt(this.size);
        dest.writeString(this.sizeStr);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.isCover ? (byte) 1 : (byte) 0);
    }

    protected Picture(Parcel in) {
        this.bucket = in.readString();
        this.size = in.readInt();
        this.sizeStr = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.isCover = in.readByte() != 0;
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
