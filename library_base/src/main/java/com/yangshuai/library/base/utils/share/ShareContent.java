package com.yangshuai.library.base.utils.share;

import android.graphics.Bitmap;

import androidx.annotation.IntDef;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.widget.ShareDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * 分享内容
 *
 * @author hcp
 * @date 2020/8/4
 */
public class ShareContent {
    /**
     * 分享内容类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SHARE_TYPE.IMAGE_WITH_TEXT, SHARE_TYPE.IMAGE, SHARE_TYPE.TEXT})
    public @interface SHARE_TYPE {
        /**
         * 图文
         */
        int IMAGE_WITH_TEXT = 0;
        /**
         * 图片
         */
        int IMAGE = 1;
        /**
         * 文本
         */
        int TEXT = 2;
    }

    /**
     * 分享来源类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SHARE_SOURCE_TYPE.OTHER_SHARE, SHARE_SOURCE_TYPE.USED_HOUSE_SHARE, SHARE_SOURCE_TYPE.RENT_HOUSE_SHARE,
            SHARE_SOURCE_TYPE.NEW_HOUSE_SHARE, SHARE_SOURCE_TYPE.STORE_SHARE, SHARE_SOURCE_TYPE.BUSINESS_CARD_SHARE,
            SHARE_SOURCE_TYPE.SPECIAL_SHARE, SHARE_SOURCE_TYPE.NEW_HOUSE_TYPE_SHARE})
    public @interface SHARE_SOURCE_TYPE {
        /**
         * 其他分享
         */
        int OTHER_SHARE = 0;
        /**
         * 二手房分享
         */
        int USED_HOUSE_SHARE = 1;
        /**
         * 租房分享
         */
        int RENT_HOUSE_SHARE = 2;
        /**
         * 新房分享
         */
        int NEW_HOUSE_SHARE = 3;
        /**
         * 店铺分享
         */
        int STORE_SHARE = 4;
        /**
         * 名片分享
         */
        int BUSINESS_CARD_SHARE = 5;
        /**
         * 专题分享
         */
        int SPECIAL_SHARE = 6;
        /**
         * 户型分享
         */
        int NEW_HOUSE_TYPE_SHARE = 7;
    }

    /**
     * 分享埋点渠道 1.小程序（微信好友） 2.微信H5（朋友圈）3.qq好友 4.qq空间 5.微博 6.短信
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BURYING_PLATFORM.BURYING_WX, BURYING_PLATFORM.BURYING_WX_MIN, BURYING_PLATFORM.BURYING_QQ,
            BURYING_PLATFORM.BURYING_QQ_ZONE, BURYING_PLATFORM.BURYING_WEI_BO, BURYING_PLATFORM.BURYING_SMS})
    public @interface BURYING_PLATFORM {
        /**
         * 小程序（微信好友）
         */
        int BURYING_WX = 1;
        /**
         * 微信H5（朋友圈）
         */
        int BURYING_WX_MIN = 2;
        /**
         * QQ好友
         */
        int BURYING_QQ = 3;
        /**
         * QQ空间
         */
        int BURYING_QQ_ZONE = 4;
        /**
         * 新浪微博
         */
        int BURYING_WEI_BO = 5;
        /**
         * 短信
         */
        int BURYING_SMS = 6;
    }

    /**
     * 分享标题
     */
    private String title;
    /**
     * 分享内容
     */
    private String shareContent;
    /**
     * 将要分享的社交平台
     *
     * @see ShareDialog.SHARE_PLATFORM
     */
    private int sharePlatform;
    /**
     * 分享埋点的社交平台类型
     */
    private int shareBuryingPlatform;
    /**
     * 是否是分享到微信小程序
     */
    private boolean isShareWxMin;
    /**
     * 分享来源模块
     *
     * @see SHARE_SOURCE_TYPE
     */
    private int category;
    /**
     * 分享内容类型
     *
     * @see SHARE_TYPE
     */
    private int shareContentType;
    /**
     * 分享web地址
     */
    private String webUrl;
    /**
     * 分享图片网络地址
     */
    private String imageUrl;
    /**
     * 分享图片文件地址
     */
    private String imagePath;
    /**
     * 分享图片bitmap
     */
    private Bitmap shareBitmap;
    /**
     * 分享图片缩略图bitmap
     */
    public Bitmap thumpBitmap;
    /**
     * 分享默认图
     */
    private int defaultShareResId;
    /**
     * 分享文本(仅文本，不包括图片)，设置此参数，会默认不加载图片等其他内容，
     * 如需分享图文信息，请使用title+content+pic
     */
    private String shareOnlyText;
    /**
     * 手机号
     */
    private String shareSmsPhone;
    /**
     * 短信内容
     */
    private String shareSmsContent;
    /**
     * 微信小程序路径
     */
    private String shareWxMinPath;
    /**
     * 埋点参数
     */
    private Map<String, Object> buryingParams;
    /**
     * 复制内容
     */
    private String copyStr;
    /**
     * 埋点线索类型
     */
    private String traceType;
    /**
     * 保存图片结果
     */
    private boolean saveResult;

    public ShareContent() {
        defaultShareResId = R.mipmap.icon_share_default;
    }

    public Bitmap getThumpBitmap() {
        return thumpBitmap;
    }

    public void setThumpBitmap(Bitmap thumpBitmap) {
        this.thumpBitmap = thumpBitmap;
    }

    public String getCopyStr() {
        return copyStr;
    }

    public void setCopyStr(String copyStr) {
        this.copyStr = copyStr;
    }

    public String getShareOnlyText() {
        return shareOnlyText;
    }

    public void setShareOnlyText(String shareOnlyText) {
        this.shareOnlyText = shareOnlyText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public int getSharePlatform() {
        return sharePlatform;
    }

    public void setSharePlatform(int sharePlatform) {
        this.sharePlatform = sharePlatform;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isSaveResult() {
        return saveResult;
    }

    public void setSaveResult(boolean saveResult) {
        this.saveResult = saveResult;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getShareBitmap() {
        return shareBitmap;
    }

    public void setShareBitmap(Bitmap shareBitmap) {
        this.shareBitmap = shareBitmap;
    }

    public boolean isShareWxMin() {
        return isShareWxMin;
    }

    public void setShareWxMin(boolean shareWxMin) {
        isShareWxMin = shareWxMin;
    }

    public int getShareContentType() {
        return shareContentType;
    }

    public void setShareContentType(int shareContentType) {
        this.shareContentType = shareContentType;
    }

    public int getDefaultShareResId() {
        return defaultShareResId;
    }

    public void setDefaultShareResId(int defaultShareResId) {
        this.defaultShareResId = defaultShareResId;
    }

    public String getShareSmsPhone() {
        return shareSmsPhone;
    }

    public void setShareSmsPhone(String shareSmsPhone) {
        this.shareSmsPhone = shareSmsPhone;
    }

    public String getShareSmsContent() {
        return shareSmsContent;
    }

    public void setShareSmsContent(String shareSmsContent) {
        this.shareSmsContent = shareSmsContent;
    }

    public String getShareWxMinPath() {
        return shareWxMinPath;
    }

    public void setShareWxMinPath(String shareWxMinPath) {
        this.shareWxMinPath = shareWxMinPath;
    }

    public Map<String, Object> getBuryingParams() {
        return buryingParams;
    }

    public void setBuryingParams(Map<String, Object> buryingParams) {
        this.buryingParams = buryingParams;
    }

    public String getTraceType() {
        return traceType;
    }

    public void setTraceType(String traceType) {
        this.traceType = traceType;
    }

    public int getShareBuryingPlatform() {
        return shareBuryingPlatform;
    }

    public void setShareBuryingPlatform(int shareBuryingPlatform) {
        this.shareBuryingPlatform = shareBuryingPlatform;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
