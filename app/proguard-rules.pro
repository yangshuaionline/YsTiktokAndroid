# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/jess/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers android.webkit.WebView {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------

#---------------------------------基本指令区---------------------------------
# 抑制警告
-ignorewarnings
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#混淆包路径
-repackageclasses ''
-flattenpackagehierarchy ''

#保护注解
-keepattributes *Annotation*

#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature

#保留SourceFile和LineNumber属性
-keepattributes SourceFile,LineNumberTable

#忽略警告
#-ignorewarning
#----------记录生成的日志数据,gradle build时在本项目根目录输出---------
#apk 包内所有 class 的内部结构
#-dump class_files.txt
#未混淆的类和成员
#-printseeds seeds.txt
#列出从 apk 中删除的代码
#-printusage unused.txt
#混淆前后的映射
#-printmapping mapping.txt
#----------------------------------------------------------------------------

#---------------------------------反射相关的类和方法-----------------------
-keep public class * extends com.ujuz.library.base.BaseActivity{ *; }
-keep public class * extends com.ujuz.library.base.BaseFragment{ *; }
-keep public class * extends com.ujuz.library.base.BaseToolBarActivity{ *; }

#----------------------------------------------------------------------------

#----------------------------------DataBinding------------------------------------------
-keep class com.ujuz.library.base.BaseBindingAdapter { *; }
#----------------------------------------------------------------------------


 #实体类不参与混淆

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
-keepattributes Signature
-keep class **.R$* {*;}
-ignorewarnings
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

-keepclassmembers enum * {  # 使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


################support###############
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**



################retrofit###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions



################gson###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.sunloto.shandong.bean.** { *; }


################glide###############
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#glide如果你的API级别<=Android API 27 则需要添加
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
################okhttp###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**

-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okio.**

-keep class com.hitomi.** { *; }
-keep interface com.hitomi.** { *; }

-ignorewarnings -keep class * { public private *; }



################EventBus###############
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

################autolayout###############
-keep class com.zhy.autolayout.** { *; }
-keep interface com.zhy.autolayout.** { *; }


################RxJava and RxAndroid###############
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**


-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}


-dontwarn java.lang.invoke.*

################annotation###############
-keep class androidx.annotation.** { *; }
-keep interface androidx.annotation.** { *; }




################RxPermissions#################
-keep class com.tbruyelle.rxpermissions2.** { *; }
-keep interface com.tbruyelle.rxpermissions2.** { *; }

#百度地图
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

################Timber#################
-dontwarn org.jetbrains.annotations.**


#-dontwarn class android.app.ActivityThread

#易盾sdk
-keepattributes *Annotation*
-keep public class com.netease.nis.captcha.**{*;}

-keep public class android.webkit.**

-keepattributes SetJavaScriptEnabled
-keepattributes JavascriptInterface

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#android-gif-drawable
-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}
-keep class pl.droidsonroids.gif.GifInfoHandle{<init>(long,int,int,int);}

#base模块
#实体
-keep class com.ujuz.library.base.account.UserInfo{ *; }
-keep class com.ujuz.library.base.appinfo.AppInfo{ *; }
-keep class com.ujuz.library.base.common.BaseCommon{ *; }
-keep class com.ujuz.library.base.entity.**{ *; }
-keep class com.ujuz.library.base.account.UserInfo{*;}
-keep class com.ujuz.library.base.appinfo.AppInfo{*;}
#dialog
-keep class com.ujuz.library.base.dialog.**{ *; }
#view
-keep class com.ujuz.library.base.view.**{ *; }
-keep class com.ujuz.library.base.widget.**{ *; }


#登录注册模块
#实体
-keep class com.ujuz.module.signin.entity.** { *; }
#view
-keep class com.ujuz.module.signin.view.** { *; }
#dialog
-keep class com.ujuz.module.signin.dialog.** { *; }

#合同模块
#实体
-keep class com.ujuz.module.contract.entity.** { *; }
#view
-keep class com.ujuz.module.contract.widget.** { *; }
#dialog
-keep class com.ujuz.module.contract.dialog.** { *; }

#房源相册
#实体
-keep class com.yjyz.library_house_album.entity.**{ *; }
#装饰器
-keep class com.yjyz.library_house_album.decoration.**{ *; }

#图片选择
#实体
-keep class com.ujuz.library.photo.picker.view.**{ *; }
#view
-keep class com.ujuz.library.photo.picker.model.**{ *; }

#数据分析模块
#实体
-keep class com.yjyz.module_data_analysis.entity.**{*;}
#view
-keep class com.yjyz.module_data_analysis.view.**{*;}
#dialog
-keep class com.yjyz.module_data_analysis.dialog.**{*;}
#databinding
-keep class com.yjyz.module_data_analysis.databinding.**{*;}

#main模块
#实体
-keep class com.ujuz.module.main.entity.**{*;}

#用户模块
#实体
-keep class com.ujuz.module.mine.entity.**{*;}
#databinding
-keep class com.ujuz.module.mine.databinding.**{*;}

#登录注册模块
#实体
-keep class com.ujuz.module.signin.entity.**{*;}
-keep class com.ujuz.module.signin.dialog.**{*;}
-keep class com.ujuz.module.signin.view.**{*;}

#工作台
-keep class com.ujuz.module.work.databinding.**{*;}
-keep class com.ujuz.module.work.entity.**{*;}
-keep class com.ujuz.module.work.model.**{*;}

#电子合同
-keep class com.ujuz.module.econtract.databinding.**{*;}
-keep class com.ujuz.module.econtract.entity.**{*;}

#真人认证模块
-keep class com.taobao.securityjni.**{*;}
-keep class com.taobao.wireless.security.**{*;}
-keep class com.ut.secbody.**{*;}
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep class com.alibaba.security.rp.**{*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.alibaba.security.biometrics.**{*;}
-keep class android.taobao.windvane.**{*;}

# umeng sdk
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep class com.youth.banner.** {
    *;
 }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}









