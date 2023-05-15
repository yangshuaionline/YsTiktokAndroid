package com.yangshuai.module.login;

import android.app.Application;

import com.yangshuai.library.base.account.AccountManager;
import com.yangshuai.library.base.interfaces.ModuleInitImpl;
import com.yangshuai.library.base.utils.KLog;

/**
 * @Author hcp
 * @Created 4/20/19
 * @Editor hcp
 * @Edited 4/20/19
 * @Type
 * @Layout
 * @Api
 */
public class SigninModuleInit implements ModuleInitImpl {
    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("注册模块初始化 -- onInitAhead");
        AccountManager.init(application.getApplicationContext());
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
