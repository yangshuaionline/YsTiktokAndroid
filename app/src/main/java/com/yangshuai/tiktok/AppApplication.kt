package com.yangshuai.tiktok

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.yangshuai.library.base.BaseConst
import com.yangshuai.library.base.R
import com.yangshuai.library.base.account.AccountManager
import com.yangshuai.library.base.application.BaseApplication
import com.yangshuai.library.base.bugly.BuglyHolder
import com.yangshuai.library.base.config.AppBaseConfig
import com.yangshuai.library.base.config.ModuleLifecycleConfig
import com.yangshuai.library.base.log.LoggerClent
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.permission.BPayPermissionsManager
import com.yangshuai.library.base.permission.BPermissionsManager
import com.yangshuai.library.base.utils.AppContext
import com.yangshuai.library.base.utils.ScreenUtils
import com.yangshuai.library.base.view.ClassicsHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import io.reactivex.plugins.RxJavaPlugins
import org.litepal.LitePal


class AppApplication : BaseApplication (){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        // 全局处理Rxjava抛出的异常，防止Rxjava调用时未处理错误导致崩溃
        RxJavaPlugins.setErrorHandler { obj: Throwable -> obj.printStackTrace() }
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        // 执行各模块的初始化(优先的）
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
        AccountManager.init(applicationContext)
        // SmartRefreshLayout
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout? -> ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate).setProgressResource(R.drawable.yjyz_logo_loading) }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, layout: RefreshLayout? -> ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate).setTextSizeTitle(12f) }
        // 执行各模块的初始化(靠后的）
        ModuleLifecycleConfig.getInstance().initModuleLow(this)
        // 写入项目配置信息
        AppBaseConfig.get().init(this, buildConfig())
        AppBaseConfig.get().loadString("baseUrl")
        //init retrofit网络框架
        RetrofitManager.getInstance().init(BuildConfig.BASE_HOST,true)
        LoggerClent.getInstance()
        AppContext(this)
        ScreenUtils.init(this)
        // 初始化地图start
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        SDKInitializer.initialize(this)
//        SDKInitializer.setCoordType(CoordType.BD09LL)
        //初始化数据库
        LitePal.initialize(this)
        //rx权限库
        BPermissionsManager.init(this)
        BPayPermissionsManager.init(this)
        // 初始化大图加载器
        BigImageViewer.initialize(GlideImageLoader.with(this,RetrofitManager.getOkhttpClient(false)))
        // 初始化bugly
        BuglyHolder.init(applicationContext,true)
        UMShareAPI.get(this)
        setUmengPlatformConfig()
    }

    /**
     * 设置友盟的数据
     */
    private fun setUmengPlatformConfig() {
        PlatformConfig.setSinaWeibo(BaseConst.SINA_APP_ID, BaseConst.SINA_APP_SECRET, BuildConfig.BASE_HOST)
        PlatformConfig.setWeixin(BaseConst.WX_APP_ID, BaseConst.WX_APP_SECRET)
        PlatformConfig.setQQZone(BaseConst.QQ_ID, BaseConst.APP_KEY)
    }

    /**
     * 默认配置项
     */
    fun buildConfig():AppBaseConfig.Config{
        val config=AppBaseConfig.Config()
        config.isDebug = BuildConfig.DEBUG
        config.applicationId = BuildConfig.APPLICATION_ID
        config.versionCode = BuildConfig.VERSION_CODE
        config.versionName = BuildConfig.VERSION_NAME
        config.baseUrl = BuildConfig.BASE_HOST
        return config
    }

    /**
     * 应用前台
     */
    override fun onFront(isFirstStartApp: Int) {
        super.onFront(isFirstStartApp)
    }
    /**
     * 应用后台
     */
    override fun onBack() {
        super.onBack()
    }
}