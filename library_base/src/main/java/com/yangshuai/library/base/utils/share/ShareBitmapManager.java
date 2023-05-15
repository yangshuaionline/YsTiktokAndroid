package com.yangshuai.library.base.utils.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.yangshuai.library.base.luban.Luban;
import com.yangshuai.library.base.utils.BitmapUtils;
import com.yangshuai.library.base.utils.FileUtil;
import com.yangshuai.library.base.widget.ShareDialog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import io.reactivex.Observable;

/**
 * 分享图片管理
 *
 * @author hcp
 * @date 2020/8/5
 */
public class ShareBitmapManager {
    /**
     * 获取分享的图片
     * 可扩展此方法实现分享图片
     */
    public Observable<ShareBuilder> getShareBitmap(ShareBuilder shareBuilder) {
        return Observable.just(shareBuilder)
                .map(shareBuilder1 -> {
                    ShareContent shareContent = shareBuilder1.getShareContent();
                    //如果是分享短信，复制链接不需要图片
                    if (shareContent.getSharePlatform() == ShareDialog.SHARE_COPY
                            || shareContent.getSharePlatform() == ShareDialog.SHARE_SMS) {
                        return shareBuilder1;
                    }
                    if (shareContent.getShareContentType() == ShareContent.SHARE_TYPE.TEXT) {
                        //仅文本，清空图片
                        shareContent.setShareBitmap(null);
                        return shareBuilder1;
                    }
                    //加载分享图片
                    //1，如果bitmap不为空，直接用bitmap
                    //2，如果bitmap为空，图片地址不为空，加载图片地址
                    //3，如果bitmap为空，图片地址为空，加载本地图片
                    Bitmap bitmap = shareContent.getShareBitmap();
                    if (bitmap != null && shareContent.getShareContentType() == ShareContent.SHARE_TYPE.IMAGE) {
                        //保存图片，先保存一个缓存文件
                        File cacheFile = FileUtil.saveBitmapToCacheFile(shareBuilder1.getContext(), bitmap);
                        if (cacheFile != null) {
                           shareContent.setImagePath(cacheFile.getAbsolutePath());
                        }
                    }
                    if (bitmap == null && !TextUtils.isEmpty(shareContent.getImageUrl())) {
                        //将需要分享的图片链接下载转换成Bitmap
                        //微信分享只接受Bitmap类型的图片,缩略图大小不超过32KB,图片不超过10M
                        bitmap = getLocalOrNetBitmap(shareContent.getImageUrl());
                    }
                    if (bitmap == null && !TextUtils.isEmpty(shareContent.getImagePath())) {
                        //图片文件
                        bitmap = BitmapFactory.decodeFile(shareContent.getImagePath());
                    }

                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(shareBuilder1.getContext().getResources(),
                                shareContent.getDefaultShareResId());
                    }
                    //分享微信小程序缩略图需要大一点
                    boolean needBigThumpBitmap = shareContent.getSharePlatform() == ShareDialog.SHARE_WECHAT && shareContent.isShareWxMin();
                    int thumpSize = needBigThumpBitmap ? 500 : 150;
                    if (shareContent.getShareContentType() == ShareContent.SHARE_TYPE.IMAGE) {
                        //图片分享目前仅支持文件路径分享
                        shareContent.setImagePath(compressFile(shareBuilder1.getContext(), shareContent.getImagePath()));
                    }
                    //缩略图
                    shareContent.setThumpBitmap(BitmapUtils.centerSquareScaleBitmap(bitmap, thumpSize));
                    return shareBuilder1;
                });
    }

    /**
     * 压缩图片
     *
     * @param imagePath 源文件路径
     * @return 压缩后的图片
     */
    private String compressFile(Context context, String imagePath) {
        //微信分享图片最大10M
        int limitSize = 10240;
        //判断文件是否需要压缩
        while (needCompress(limitSize, imagePath)) {
            File file;
            try {
                file = Luban.with(context).get(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            imagePath = file.getAbsolutePath();
        }
        return imagePath;
    }


    boolean needCompress(int leastCompressSize, String path) {
        if (leastCompressSize > 0) {
            File source = new File(path);
            return source.exists() && source.length() > (leastCompressSize << 10);
        } else {
            return false;
        }
    }

    /**
     * 把网络资源图片转化成bitmap(注意，不能再UI线程调用)
     *
     * @param url 网络资源图片
     * @return Bitmap
     */
    private Bitmap getLocalOrNetBitmap(String url) {
        Bitmap bitmap;
        InputStream in;
        BufferedOutputStream out;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
}
