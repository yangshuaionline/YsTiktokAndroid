package com.yangshuai.module.publish;

import android.app.Application;

import com.yangshuai.library.base.interfaces.ModuleInitImpl;
import com.yangshuai.library.base.utils.KLog;

/**
 * 用于工作台模块的组件初始化
 * @author hcp
 * @created 2019/3/13
 */
public class MechanismModuleInit implements ModuleInitImpl {

    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("工作台模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
