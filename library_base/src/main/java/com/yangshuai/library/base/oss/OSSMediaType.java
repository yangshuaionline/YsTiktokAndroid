package com.yangshuai.library.base.oss;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Author hcp
 * @Created 5/8/19
 * @Editor hcp
 * @Edited 5/8/19
 * @Type
 * @Layout
 * @Api
 */
public class OSSMediaType {
    public static final String IMAGE_TYPE = "image/*";
    public static final String VIDEO_TYPE = "video/*";
    public static final String AUDIO_TYPE = "audio/*";

    private static final List<String> SUPPORT_TYPES = Arrays.asList(IMAGE_TYPE, VIDEO_TYPE, AUDIO_TYPE);
    /**
     * 图片文件后缀名
     */
    private static final List<String> IMAGE_SUFFIXS = Arrays.asList(
            "bmp",
            "gif",
            "jpeg",
            "jpg",
            "svg",
            "webp",
            "png",
            "heic"
    );
    /**
     * 视频文件后缀名
     */
    private static final List<String> VIDEO_SUFFIXS = Arrays.asList(
            "avi",
            "wmv",
            "mp4",
            "mov",
            "mkv",
            "flv",
            "f4v",
            "m4v",
            "rmvb"
    );
    /**
     * 音频文件后缀
     */
    private static final List<String> AUDIO_SUFFIXS = Arrays.asList(
            "mp3",
            "mid",
            "m3u",
            "ram",
            "rpm",
            "wav"
    );


    /**
     * 根据文件拓展名获取类型
     *
     * @param path
     * @return
     */
    public static String getTypeByFile(String path) {
        File file = new File(path);

        return getTypeBySuffix(getFileSuffix(file));
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        String suffix = dotIndex != -1 ? fileName.substring(dotIndex + 1) : "unknown";

        return suffix;
    }

    /**
     * 根据文件拓展名获取类型
     *
     * @param suffix
     * @return
     */
    public static String getTypeBySuffix(String suffix) {
        suffix = suffix.toLowerCase();
        String type = suffix;

        if (IMAGE_SUFFIXS.contains(suffix)) {
            type = IMAGE_TYPE;
        } else if (VIDEO_SUFFIXS.contains(suffix)) {
            type = VIDEO_TYPE;
        } else if (AUDIO_SUFFIXS.contains(suffix)) {
            type = AUDIO_TYPE;
        }

        return type;
    }

    public static boolean isSupportType(String type) {
        return SUPPORT_TYPES.contains(type);
    }

}
