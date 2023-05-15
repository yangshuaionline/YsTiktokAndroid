package com.yangshuai.library.base.utils.share;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yangshuai.library.base.BaseConst;
import com.yangshuai.library.base.utils.AppVersionUtil;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.utils.ViewUtils;
import com.yangshuai.library.base.widget.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import io.reactivex.Observable;

/**
 * 分享到对应平台
 *
 * @author hcp
 * @date 2020/8/5
 */
public class SharePushManager {
    /**
     * 分享到对应平台
     * 可扩展此方法实现不同分享策略
     */
    public Observable<ShareBuilder> pushShare(ShareBuilder shareBuilder) {
        return Observable.just(shareBuilder)
                .map(shareBuilder1 -> {
                    ShareContent shareContent = shareBuilder1.getShareContent();
                    switch (shareContent.getSharePlatform()) {
                        case ShareDialog.SHARE_WECHAT:
                            // 微信
                            if (shareContent.getShareContentType() == ShareContent.SHARE_TYPE.IMAGE) {
                                shareImageToWeChat(shareBuilder, shareContent);
                                break;
                            }
                            if (shareContent.isShareWxMin()) {
                                shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_WX_MIN);
                                shareMiniProgramToWeChat(shareBuilder1);
                            } else {
                                shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_WX);
                                shareWebToWeChat(shareBuilder1);
                            }
                            break;
                        case ShareDialog.SHARE_WECHAT_COF:
                            // 朋友圈
                            if (shareContent.getShareContentType() == ShareContent.SHARE_TYPE.IMAGE) {
                                shareImageToWeChat(shareBuilder, shareContent);
                                break;
                            }
                            shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_WX);
                            shareWebToWeChat(shareBuilder1);
                            break;
                        case ShareDialog.SHARE_POSTER:
                            // 房源海报
                            break;
                        case ShareDialog.SHARE_SPECIAL_POSTER:
                            //专题海报
                            break;
                        case ShareDialog.SHARE_QQ:
                            // QQ
                            shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_QQ);
                            shareWeb(shareBuilder1, shareContent, SHARE_MEDIA.QQ);
                            break;
                        case ShareDialog.SHARE_QQ_ZONE:
                            // QQ空间
                            shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_QQ_ZONE);
                            shareWeb(shareBuilder1, shareContent, SHARE_MEDIA.QZONE);
                            break;
                        case ShareDialog.SHARE_SINA:
                            // 新浪微博
                            shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_WEI_BO);
                            shareWeb(shareBuilder1, shareContent, SHARE_MEDIA.SINA);
                            break;
                        case ShareDialog.SHARE_COPY:
                            // 复制链接

                            break;
                        case ShareDialog.SHARE_SMS:
                            //短信
                            shareContent.setShareBuryingPlatform(ShareContent.BURYING_PLATFORM.BURYING_SMS);
                            shareSms(shareBuilder1, shareContent);
                            break;
                        case ShareDialog.SHARE_SAVE:
                            //保存图片
                            boolean isSave = ViewUtils.saveBitmap(shareBuilder1.getContext(),
                                    shareContent.getShareBitmap(), System.currentTimeMillis() + ".png");
                            shareContent.setSaveResult(isSave);
                        default:
                    }
                    return shareBuilder1;
                });
    }

    /**
     * 分享链接
     */
    public static void shareWeb(ShareBuilder shareBuilder, ShareContent shareContent, SHARE_MEDIA platform) {
        //连接地址
        UMWeb web = new UMWeb(shareContent.getWebUrl());
        //标题
        web.setTitle(shareContent.getTitle());
        //描述
        web.setDescription(shareContent.getShareContent());
        //网络缩略图
        web.setThumb(new UMImage(shareBuilder.getContext(), shareContent.getThumpBitmap()));
        new ShareAction(shareBuilder.getActivity())
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA shareMedia) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA shareMedia) {
                        if (shareBuilder.getShareListener() != null) {
                            shareBuilder.getShareListener().shareSuccess("分享成功");
                        }
                    }

                    @Override
                    public void onError(final SHARE_MEDIA shareMedia, final Throwable throwable) {
                        if (shareBuilder.getShareListener() != null) {
                            shareBuilder.getShareListener().shareFailed("分享失败");
                        }
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA shareMedia) {
                        if (shareBuilder.getShareListener() != null) {
                            shareBuilder.getShareListener().shareCancel("分享取消");
                        }
                    }
                })
                .share();
    }

    public static void shareImageToWeChat(ShareBuilder shareBuilder, ShareContent shareContent) {
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(shareBuilder.getContext(), BaseConst.WX_APP_ID);
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(shareContent.getImagePath());
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        msg.setThumbImage(shareContent.getThumpBitmap());

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "优居优住";
        req.message = msg;
        if (shareContent.getSharePlatform() == ShareDialog.SHARE_WECHAT) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        //调用api接口，发送数据到微信
        wxapi.sendReq(req);
    }


    /**
     * 分享网页类型至微信
     */
    public static void shareWebToWeChat(ShareBuilder shareBuilder) {
        ShareContent shareContent = shareBuilder.getShareContent();
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(shareBuilder.getContext(), BaseConst.WX_APP_ID);

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = shareContent.getWebUrl();

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getShareContent();
        // 如果没有位图，可以传null，会显示默认的图片
        msg.setThumbImage(shareContent.getThumpBitmap());

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）对应该请求的事务ID，通常由Req发起，回复Resp时应填入对应事务ID
        req.transaction = "优居优住";
        // 上文的WXMediaMessage对象
        req.message = msg;
        // SendMessageToWX.Req.WXSceneSession是分享到好友会话
        // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
        if (shareContent.getSharePlatform() == ShareDialog.SHARE_WECHAT) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        // 向微信发送请求
        wxapi.sendReq(req);
    }

    /**
     * 分享微信小程序
     */
    public static void shareMiniProgramToWeChat(ShareBuilder shareBuilder) {
        ShareContent shareContent = shareBuilder.getShareContent();
        // 通过appId得到IWXAPI这个对象
        IWXAPI wxapi = WXAPIFactory.createWXAPI(shareBuilder.getContext(), BaseConst.WX_APP_ID);
        // 初始化一个WXWebpageObject对象
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = shareContent.getWebUrl(); // 兼容低版本的网页链接
        // 正式版:0，测试版:1，体验版:2
        if (AppVersionUtil.isProd()) {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
            // 小程序原始id
            miniProgramObj.userName = BaseConst.WX_MINI_PROGRAM;
        } else {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW;
            miniProgramObj.userName = BaseConst.WX_MINI_PROGRAM_TEST;
        }

        miniProgramObj.path = shareContent.getShareWxMinPath();      //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = shareContent.getTitle();                    // 小程序消息title
        msg.description = shareContent.getShareContent();            // 小程序消息desc
        msg.setThumbImage(shareContent.getThumpBitmap());            // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "优居优住";
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        wxapi.sendReq(req);
    }

    /**
     * 分享短信
     */
    public static void shareSms(ShareBuilder shareBuilder, ShareContent shareContent) {
        Utils.sendSMS(shareBuilder.getContext(), shareContent.getShareSmsPhone(), shareContent.getShareSmsContent());
    }
}
