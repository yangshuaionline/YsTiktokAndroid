package com.yangshuai.library.base.utils;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yangshuai.library.base.config.AppBaseConfig;
import com.yangshuai.library.base.oss.OSSService;

/**
 * Time:$[DATE]
 * Author:hcp
 */
public class YJGlideUtil {
    public static Object getGlideUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            KLog.w("GlideUrl empty!" + url);
            return url;
        }

        /**
         * 判断是否是网络图片
         * */
        if (url.startsWith("http") || url.startsWith("https")) {
            if (url.contains(AppBaseConfig.get().getConfig().getBaseUrl())
                    || url.contains("images-beta.xxxxxx.com")
                    || url.contains("images.xxxxxx.com")
                    || url.contains("static-beta.xxxxxx.com")
                    || url.contains("static-stag.xxxxxx.com")
                    || url.contains("static.xxxxxx.com")) {
                GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                        .addHeader("Referer", OSSService.REFERER_PHOTO)
                        .build());
                return glideUrl;
            } else {
                return url;
            }

        } else {
            return url;
        }

    }

    /**
     * 判断是否是YJYZ网络图片
     * @param url
     * @return
     */
    public static boolean isYJImageUrl(String url) {
        /**
         * 判断是否是YJYZ网络图片
         * */
        if (url.startsWith("http") || url.startsWith("https")) {
            if (url.contains(AppBaseConfig.get().getConfig().getBaseUrl())
                    || url.contains("images-beta.xxxxxx.com")
                    || url.contains("images.xxxxxx.com")
                    || url.contains("static-beta.xxxxxx.com")
                    || url.contains("static-stag.xxxxxx.com")
                    || url.contains("static.xxxxxx.com")) {

                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}
