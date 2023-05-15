package com.yangshuai.module.mine;

import android.app.Application;

import com.yangshuai.library.base.interfaces.ModuleInitImpl;
import com.yangshuai.library.base.utils.KLog;

/**
 * 用于个人中心的组件初始化
 * @author hcp
 * @created 2019/3/13
 */
public class MineModuleInit implements ModuleInitImpl {

    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("我的模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
