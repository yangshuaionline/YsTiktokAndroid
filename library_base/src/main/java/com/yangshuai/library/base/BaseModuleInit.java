package com.yangshuai.library.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yangshuai.library.base.interfaces.ModuleInitImpl;
import com.yangshuai.library.base.utils.KLog;
import com.yangshuai.library.base.utils.KVCache;

/**
 * 基础库自身的初始化操作(通过ModuleLifecycleReflexs反射调用)
 * @author hcp
 * @created 2019/3/13
 */
public class BaseModuleInit implements ModuleInitImpl {

    @Override
    public boolean onInitAhead(Application application) {
        // 开启打印日志
        KLog.init(true);

        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印路由日志
            ARouter.openDebug();    // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(application); // 尽可能早，推荐在Application中初始化
        KVCache.init(application);
        KLog.e("基础层初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("基础层初始化 -- onInitAhead");
        return false;
    }
}
