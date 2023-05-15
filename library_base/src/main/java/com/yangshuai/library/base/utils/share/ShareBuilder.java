package com.yangshuai.library.base.utils.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.yangshuai.library.base.utils.RxUtils;
import com.yangshuai.library.base.utils.ToastUtil;
import com.yangshuai.library.base.widget.ShareDialog;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 分享构建
 *
 * @author hcp
 * @date 2020/8/4
 */
public class ShareBuilder {
    /**
     * 上下文对象
     */
    private Activity mActivity;
    private Context mContext;
    /**
     * 分享内容
     */
    private ShareContent shareContent;
    /**
     * 分享回调
     */
    private ShareListener shareListener;
    /**
     * 分享图片管理
     */
    private ShareBitmapManager shareBitmapManager;
    /**
     * 分享回调管理
     */
    private ShareBuryingManager shareBuryingManager;
    /**
     * 分享到对应平台管理
     */
    private SharePushManager sharePushManager;

    public ShareBuilder(Activity activity) {
        // TODO: 2020/8/6
        //  1，可以将ShareContent内容拆分为不同类，比如图片内容，埋点内容等，然后分别于对应Manager相互协同，
        //  2，支持多种形式传递图片文件，目前支持bitmap 和 imgUrl
        this.mActivity = activity;
        this.mContext = activity;
        shareContent = new ShareContent();
        shareBitmapManager = new ShareBitmapManager();
        shareBuryingManager = new ShareBuryingManager();
        sharePushManager = new SharePushManager();
    }

    public ShareContent getShareContent() {
        return shareContent;
    }

    public Context getContext() {
        return mContext;
    }

    public Activity getActivity() {
        return mActivity;
    }

    /**
     * {@link ShareContent#setTitle(String)}
     */
    public ShareBuilder setTitle(String title) {
        shareContent.setTitle(title);
        return this;
    }

    /**
     * {@link ShareContent#setShareContent(String)}
     */
    public ShareBuilder setShareContent(String content) {
        shareContent.setShareContent(content);
        return this;
    }

    public ShareBuilder setShareCategory(int category) {
        shareContent.setCategory(category);
        return this;
    }

    public ShareBuilder setShareWebUrl(String webUrl) {
        shareContent.setWebUrl(webUrl);
        return this;
    }

    public ShareBuilder setShareImageUrl(String imageUrl) {
        shareContent.setImagePath("");
        shareContent.setImageUrl(imageUrl);
        return this;
    }


    public ShareBuilder setShareImagePath(String imagePath) {
        shareContent.setImageUrl("");
        shareContent.setImagePath(imagePath);
        return this;
    }

    public ShareBuilder setShareBitmap(Bitmap bitmap) {
        shareContent.setShareBitmap(bitmap);
        return this;
    }

    public ShareBuilder setShareContentType(int sourceType) {
        shareContent.setShareContentType(sourceType);
        return this;
    }

    public ShareBuilder setSharePlatform(int sharePlatform) {
        shareContent.setSharePlatform(sharePlatform);
        return this;
    }

    public ShareBuilder setShareOnlyText(String shareOnlyText) {
        shareContent.setShareOnlyText(shareOnlyText);
        shareContent.setShareContentType(ShareContent.SHARE_TYPE.TEXT);
        return this;
    }

    public ShareBuilder setDefaultShareResId(int defaultShareResId) {
        shareContent.setDefaultShareResId(defaultShareResId);
        return this;
    }

    public ShareBuilder setShareListener(ShareListener shareListener) {
        this.shareListener = shareListener;
        return this;
    }

    public ShareBuilder setShareSmsPhone(String shareSmsPhone) {
        shareContent.setShareSmsPhone(shareSmsPhone);
        return this;
    }

    public ShareBuilder setShareSmsContent(String shareSmsContent) {
        shareContent.setShareSmsContent(shareSmsContent);
        return this;
    }

    public ShareBuilder setShareBuryingParams(Map<String, Object> params) {
        shareContent.setBuryingParams(params);
        return this;
    }

    public ShareBuilder setShareWxMinPath(String shareWxMinPath) {
        shareContent.setShareWxMinPath(shareWxMinPath);
        return this;
    }

    public ShareBuilder setCopyStr(String copyStr) {
        shareContent.setCopyStr(copyStr);
        return this;
    }

    public ShareBuilder setShareWxMin(boolean isWxMin) {
        shareContent.setShareWxMin(isWxMin);
        return this;
    }

    public ShareBuilder setTraceType(String traceType) {
        shareContent.setTraceType(traceType);
        return this;
    }

    public ShareListener getShareListener() {
        return shareListener;
    }

    public void share() {
        Observable.concat(shareBitmapManager.getShareBitmap(this),
                sharePushManager.pushShare(this),
                shareBuryingManager.getShareBurying(this))
                .doOnSubscribe(disposable -> {
                    if (shareListener != null) {
                        shareListener.addDisposable(disposable);
                    }
                })
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new Observer<ShareBuilder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBuilder shareBuilder) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // TODO: 2020/8/6 复制成功在SharePushManager不能成功吐司，具体原因待查
                        if (shareContent.getSharePlatform() == ShareDialog.SHARE_PLATFORM.SHARE_COPY) {
                            ToastUtil.Short("复制成功");
                        } else if (shareContent.getSharePlatform() == ShareDialog.SHARE_PLATFORM.SHARE_SAVE) {
                            ToastUtil.Short(shareContent.isSaveResult() ? "保存成功" : "保存失败");
                        }
                    }
                });
    }
}
