package com.yangshuai.module.find;

import android.app.Application;
import com.yangshuai.library.base.interfaces.ModuleInitImpl;
import com.yangshuai.library.base.utils.KLog;

/**
 * 用于消息的组件初始化
 *
 * @author hcp
 * @created 2019/5/28
 */
public class MessageModuleInit implements ModuleInitImpl {

    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("主业务模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }

}
