package com.yangshuai.library.base.interfaces;

import android.app.Application;

/**
 *
 * 动态配置Application
 * 有需要初始化的模块组件实现该接口
 * 可以管理各自的初始化逻辑
 * 统一在主app的Application中初始化
 * @author hcp
 * @created 2019/3/13
 */
public interface ModuleInitImpl {

    /**
     * 初始化优先执行的
     * @param application
     * @return
     */
    boolean onInitAhead(Application application);

    /**
     * 初始化靠后执行的
     * @param application
     * @return
     */
    boolean onInitLow(Application application);

}
