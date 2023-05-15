package com.yangshuai.library.base.entity;

import com.yangshuai.library.base.utils.Utils;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.File;
import java.io.Serializable;

/**
 * @Author hcp
 * @Created 9/10/19
 * @Editor hcp
 * @Edited 9/10/19
 * @Type
 * @Layout
 * @Api
 */
public class DownloadTask extends LitePalSupport implements Serializable {

    // 下载的文件目录
    private String outputDir;
    // 下载的文件名，唯一标识
    @Column(unique = true)
    private String fileName;
    // 下载的地址
    private String url;
    // 下载的文件总大小
    private long totalSize;

    /**
     * 根据文件名查找下载任务
     *
     * @param fileName
     * @return
     */
    public static DownloadTask findByFileName(String fileName) {
        return LitePal.where("fileName = ?", fileName).findFirst(DownloadTask.class);
    }

    /**
     * 根据文件名保存任务
     *
     */
    public void saveWithFileName() {
        saveOrUpdate("fileName = ?", fileName);
    }

    /**
     * 判断是否在下载中
     *
     * @return
     */
    public boolean isDownloading() {

        long progress = getProgress();

        return progress > 0 && progress < totalSize;
    }

    /**
     * 判断是否下载完成
     *
     * @return
     */
    public boolean isCompleted() {
        long progress = getProgress();
        return totalSize > 0 && progress == totalSize;
    }

    public long getProgress() {
        File file = getOutPutFile();
        return file.length();
    }

    /**
     * 获取下载的文件
     *
     * @return
     */
    public File getOutPutFile() {
        File dir = new File(getPhat());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        return file;
    }

    /**
     * 适配android11 根据名字适配下载路径
     * @return
     */
    public String getPhat(){
        String phat="";
        //视频格式
        if(fileName.contains(".mp4")||fileName.contains(".avi")||fileName.contains(".flv")
                ||fileName.contains(".wmv")||fileName.contains(".mov")||fileName.contains(".mkv")
                ||fileName.contains(".f4v")||fileName.contains(".m4v")||fileName.contains(".rmvb")){
            phat="/Movies" + "/yjzf";
        }
        //图片格式
        else if(fileName.contains(".bmp")||fileName.contains(".gif")||fileName.contains(".jpeg")
                ||fileName.contains(".jpg")||fileName.contains(".svg")||fileName.contains(".webp")
                ||fileName.contains(".png")||fileName.contains(".heif")||fileName.contains(".heic")){
            phat="/Pictures" + "/hcp";
        }
        //其他格式 mp3  pdf apk
        else {
            phat="/Download" + "/hcp";
        }
        return Utils.sdPath() +phat;
    }


    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

}
