package com.yangshuai.library.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yangshuai.library.base.BaseConst;
import com.yangshuai.library.base.api.BaseApi;
import com.yangshuai.library.base.network.RetrofitManager;
import com.yangshuai.library.base.observer.ResponseObserver;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 分享工具类
 *
 * @author hcp
 * @created 2019-09-05
 */
public class ShareUtils {

    public static final int USED_HOUSE_SHARE = 1;
    public static final int RENT_HOUSE_SHARE = 2;
    public static final int NEW_HOUSE_SHARE = 3;
    public static final int STORE_SHARE = 4;
    public static final int BUSINESS_CARD_SHARE = 5;
    public static final int SPECIAL_SHARE = 6;
    public static final int NEW_HOUSE_TYPE_SHARE = 7;
    public static final int OTHER_SHARE = 0;

    /**
     * 分享网页类型至微信
     *
     * @param context   上下文
     * @param shareType 1分享至微信好友 2分享至朋友圈
     * @param webUrl    网页的url
     * @param title     网页标题
     * @param content   网页描述
     * @param bitmap    位图
     * @param category  来源 1.二手房 2.租房 3.新房 4.小程序店铺 5.经济名片 6.专题 7.新房户型
     */
    public static void shareWebToWeChat(Context context,
                                        int shareType,
                                        String webUrl,
                                        String title,
                                        String content,
                                        Bitmap bitmap,
                                        int category) {
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, BaseConst.WX_APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtil.Short("您还未安装微信客户端");
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = webUrl;

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = title;
        msg.description = content;
        // 如果没有位图，可以传null，会显示默认的图片
        msg.setThumbImage(bitmap);

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）对应该请求的事务ID，通常由Req发起，回复Resp时应填入对应事务ID
        req.transaction = "优居优住";
        // 上文的WXMediaMessage对象
        req.message = msg;
        // SendMessageToWX.Req.WXSceneSession是分享到好友会话
        // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
        if (shareType == 1) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        // 向微信发送请求
        wxapi.sendReq(req);

        shareClick(shareType, category);
    }

    public static void shareMiniProgramToWeChat(Context context,
                                                int shareType,
                                                String webUrl,
                                                String title,
                                                String content,
                                                String path,
                                                Bitmap bitmap,
                                                int category) {
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, BaseConst.WX_APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtil.Short("您还未安装微信客户端");
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = webUrl; // 兼容低版本的网页链接
        if (shareType == 0) {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
            // 小程序原始id
            miniProgramObj.userName = BaseConst.WX_MINI_PROGRAM;
        } else if (shareType == 1) {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
            miniProgramObj.userName = BaseConst.WX_MINI_PROGRAM_TEST;
        } else {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW;// 正式版:0，测试版:1，体验版:2
            miniProgramObj.userName = BaseConst.WX_MINI_PROGRAM_TEST;
        }

        miniProgramObj.path = path;      //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = content;            // 小程序消息desc
        msg.setThumbImage(bitmap);            // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "优居优住";
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        wxapi.sendReq(req);

        shareClick(1, category);
    }

    /**
     * 分享图片类型至微信
     *
     * @param context   上下文
     * @param imageView 需要分享的view
     */
    public static void shareImageToWeChat(Context context, int shareType, View imageView, int category) {
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, BaseConst.WX_APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            ToastUtil.Short("您还未安装微信客户端");
            return;
        }

        Bitmap bmp = ViewUtils.getBitmapFromView(imageView);

        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "优居优住";
        req.message = msg;
        if (shareType == 1) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        wxapi.sendReq(req);

        shareClick(shareType, category);
    }

    /**
     * 将需要分享的图片链接下载转换成Bitmap(微信分享只接受Bitmap类型的图片,大小不超过32KB)
     */
    public static void imageUrlToBitmap(String url, LoadImageListener listener) {
        Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
            // 下载图片
            emitter.onNext(getLocalOrNetBitmap(url));
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.addDisposable(d);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            listener.loadSuccess(bitmap);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        KLog.e("onComplete");
                    }
                });
    }

    /**
     * 把网络资源图片转化成bitmap(注意，不能再UI线程调用)
     *
     * @param url 网络资源图片
     * @return Bitmap
     */
    public static Bitmap getLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 不可视的view转Bitmap
     */
    public static Bitmap createBitmap(View v, int width, int height) {
        //测量使得view指定大小
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        v.measure(measuredWidth, measuredHeight);
        //调用layout方法布局后，可以得到view的尺寸大小
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public interface LoadImageListener {

        void addDisposable(Disposable disposable);

        void loadSuccess(Bitmap bitmap);

        void loadFailed(String errorMsg);
    }

    /**
     * 分享到新浪微博
     *
     * @param context     分享所在的类
     * @param url         用户点击后跳转的url
     * @param title       分享标题
     * @param umImage     分享的缩略图
     * @param description 分享内容描述
     * @param listener    分享回调
     */
    public static void shareToSinaWeiBo(Activity context, String url, String title, UMImage umImage, String description, UMShareListener listener) {
        UMWeb web = new UMWeb(url);//创建要分享的Web对象，传入分享的url地址
        web.setTitle(title);//设置标题
        web.setThumb(umImage);//设置传入显示的缩略图
        web.setDescription(description);//设置描述
        new ShareAction(context)//开启分享
                .withMedia(web) //填入创建好的分享内容
                .setPlatform(SHARE_MEDIA.SINA)//选择分享平台
                .setCallback(listener)//设置对分享返回结果的监听
                .share();//启动分享操作
    }

    /**
     * 分享链接
     */
    public static void shareWeb(final Activity activity, String WebUrl, String title, String description, String imageUrl, int imageID, SHARE_MEDIA platform, int category) {
        UMWeb web = new UMWeb(WebUrl);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        if (TextUtils.isEmpty(imageUrl) || imageUrl.contains("default.png")) {
            UMImage umImage = new UMImage(activity, imageID);
            umImage.compressFormat = Bitmap.CompressFormat.PNG;
            web.setThumb(umImage);  //本地缩略图
        } else {
            web.setThumb(new UMImage(activity, imageUrl));  //网络缩略图
        }

        switch (platform) {
            case QQ:
                shareClick(3, category);
                break;
            case QZONE:
                shareClick(4, category);
                break;
            case WEIXIN:
                shareClick(1, category);
                break;
            case WEIXIN_CIRCLE:
                shareClick(2, category);
                break;
            case SINA:
                shareClick(5, category);
                break;
        }

        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(() -> {
                            Toast.makeText(activity, share_media + " 分享成功", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                        activity.runOnUiThread(() -> Toast.makeText(activity, share_media + " 分享失败", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        //activity.runOnUiThread(() -> Toast.makeText(activity, share_media + " 分享取消", Toast.LENGTH_SHORT).show());
                    }
                })
                .share();
    }

    /**
     * 分享短信
     */
    public static void shareMSM(Context context, String phone, String msg, int category) {
        Utils.sendSMS(context, phone, msg);
        shareClick(6, category);
    }

    public static void shareClick(int source, int category) {
        shareClick(source, category, null);
    }

    /**
     * @param source   分享渠道 1.小程序（微信好友） 2.微信H5（朋友圈）3.qq好友 4.qq空间 5.微博 6.短信
     * @param category 来源 1.二手房 2.租房 3.新房 4.小程序店铺 5.经济名片 6.专题 7.新房户型
     */
    public static void shareClick(int source, int category, Map<String, Object> otherParams) {

        if (category == OTHER_SHARE) return;

        Map<String, Object> map = new HashMap<>();
        map.put("traceType", "150000");//线索类型
        map.put("source", source);
        map.put("category", category);
        if (otherParams != null) {
            map.putAll(otherParams);
        }
        RetrofitManager.create(BaseApi.class)
                .shareClick(map)
                .compose(RxUtils.responseTransformer())
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        KLog.i("onSuccess" + data);
                    }

                    @Override
                    public void onError(String code, String msg) {
                        KLog.i("onError" + code + " msg " + msg);
                    }
                });
    }

}
