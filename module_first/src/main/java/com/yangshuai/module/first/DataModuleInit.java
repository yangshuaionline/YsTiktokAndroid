package com.yangshuai.module.first;

import android.app.Application;

import com.yangshuai.library.base.interfaces.ModuleInitImpl;
import com.yangshuai.library.base.utils.KLog;

/**
 * 资源组件初始化
 * @author hcp
 * @created 2020/10/12
 */
public class DataModuleInit implements ModuleInitImpl {

    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("通讯录初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
