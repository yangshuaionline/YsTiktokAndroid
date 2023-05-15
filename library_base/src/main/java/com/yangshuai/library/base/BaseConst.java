package com.yangshuai.library.base;

import java.io.File;

/**
 * 存放APP全局常量
 *
 * @author hcp
 * @created 2019-09-05
 */
public class BaseConst {

    // 微信SDK
    public final static String WX_APP_ID = "xxxxxx";
    public final static String WX_APP_SECRET = "xxxxxx";
    public final static String WX_APP_SUBSCRIPTION = "xxxxxx"; // 公众号id

    // 微博分享
    public final static String SINA_APP_ID = "xxxxxx";
    public final static String SINA_APP_SECRET = "xxxxxx";

    // qq分享
    public final static String QQ_ID = "xxxxxx";
    public final static String APP_KEY = "xxxxxx";

    // 腾讯bugly
    public final static String BUGLY_APP_ID = "cb95d68a29";
    public final static String BUGLY_APP_KEY = "xxxxx";

    // umeng_sdk
    public final static String UMENG_SDK = "xxxxxx";

    // APP文件存储目录(统一命名规则：/com.yjyz.ub/功能模块名/文件名)
    public static final String APP_FOLDER = "/com.xxxxxx.ub"; // 根目录
    public static final String APP_FOLDER_CACHE = APP_FOLDER + File.separator + "Cache"; // 缓存目录
    public static final String APP_FOLDER_IM = APP_FOLDER + File.separator + "IM"; // IM缓存目录
    public static final String APP_FOLDER_PHOTO = APP_FOLDER + File.separator + "Photo"; // 图片保存路径
    public static final String APP_FOLDER_VIDEO = APP_FOLDER + File.separator + "Video"; // 视频保存路径
    public static final String APP_FOLDER_LOG = APP_FOLDER + File.separator + "Log"; // 日志
    public static final String APP_FOLOER_APK = APP_FOLDER + File.separator + "Apk"; // 更新包
    public static final String APP_FOLOER_ANNOUNCEMENT = APP_FOLDER + File.separator + "Announcement"; // 公告附件

    // 微信包名
    public static final String APP_WECHAT = "com.tencent.mm";
    // QQ包名
    public static final String APP_QQ = "com.tencent.mobileqq";
    // 新浪微博包名
    public static final String APP_SINA = "com.sina.weibo";

    // 小程序原始id  正式环境 分享原始ID
    public static final String WX_MINI_PROGRAM = "xxxxxx";
    // 小程序原始id  测试环境 分享原始ID （新增测试小程序）：
    public static final String WX_MINI_PROGRAM_TEST = "xxxxxx";

    /**
     * app 是否后台运行
     */
    public static int isAppBack = 0;

    /**
     * 空字符串显示内容
     */
    public static final String NULL = "--";
    /**
     * 1.跳转服务商详情fromType常量
     * */
    public static final String FROM_SERVICE = "0";//传统服务入口
    public static final String FROM_ASSET = "1";//资产场景服务入口
    /**
     * 资产服务场景：是否需要公章 0: 否; 1: 是
     * */
    public static final String NEED_SEEL = "1";
    public static final String UN_NEED_SEEL = "0";
}
