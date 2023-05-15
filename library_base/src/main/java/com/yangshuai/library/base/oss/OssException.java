package com.yangshuai.library.base.oss;

/**
 * 图片上传异常
 *
 * @Author hcp
 * @Created 4/28/19
 * @Editor hcp
 * @Edited 4/28/19
 * @Type
 * @Layout
 * @Api
 */
public class OssException extends Exception {

    // 文件地址
    private String filePath;

    public OssException(String filePath, String msg) {
        super(msg);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
