package com.yangshuai.library.base.utils;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yangshuai.library.base.router.RouterPath;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看工具类
 * @author hcp
 */
public   class PhotoUtils {
    private static final String KEY_POSITION = "position";
    private static final String KEY_IMAGES = "images";

    /**
     * 跳转到图片展示界面
     * @param url
     * @param imageList
     * @param mContext
     */
    public static  void showImageView(String url, List<String> imageList, Context mContext) {
        int position = 0;
        for (int i = 0;i<imageList.size();i++){
            if(url != null && url.equals(imageList.get(i))){
                position = i;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_IMAGES, (ArrayList<String>) imageList);
        bundle.putInt(KEY_POSITION, position);
        ARouter.getInstance()
                .build(RouterPath.Features.ROUTE_PREVIEW_IMAGES)
                .with(bundle)
                .navigation(mContext);
    }


}
