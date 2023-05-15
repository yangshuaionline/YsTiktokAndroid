package com.yangshuai.library.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.yangshuai.library.base.BaseConst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 文件操作工具类
 *
 * @author hcp
 * @date 2020/9/16
 */
public class FileUtil {

    /**
     * 保存字节数组文件到本地
     *
     * @param context context
     * @param bitmap  bitmap
     * @return 保存结果
     */
    public static File saveBitmapToCacheFile(Context context, Bitmap bitmap) {
        // 文件夹
        File file = getImageCacheFile(context);
        if (file == null||bitmap==null) {
            return null;
        }
        try {
            // 保存图片到本地
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public static File getImageCacheFile(Context context) {
        return getImageCacheFile(context, "");
    }

    /**
     * Returns a file with a cache image name in the private cache directory.
     * copy from luban
     *
     * @param context A context.
     * @param suffix  后缀
     * @return 缓存文件
     */
    public static File getImageCacheFile(Context context, String suffix) {
        File file = getCacheDir(context);
        if (file == null) {
            return null;
        }
        String cacheBuilder = file.getAbsolutePath() + "/" +
                System.currentTimeMillis() +
                new Random(1000).nextInt() +
                (TextUtils.isEmpty(suffix) ? ".jpg" : suffix);

        return new File(cacheBuilder);
    }

    /**
     * Returns a directory with the given name in the private cache directory of the application to
     * use to store retrieved media and thumbnails.
     * copy from luban
     *
     * @param context A context.
     */
    private static File getCacheDir(Context context) {
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, BaseConst.APP_FOLDER_CACHE);
            // File wasn't able to create a directory, or the result exists but not a directory
            boolean condition1 = !result.mkdirs();
            boolean condition2 = (!result.exists() || !result.isDirectory());
            if (condition1 && condition2) {
                return null;
            }
            return result;
        }
        return null;
    }
}
