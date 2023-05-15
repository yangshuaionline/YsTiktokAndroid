package com.yangshuai.module.main

import com.yangshuai.library.base.interfaces.ModuleInitImpl
import android.app.Application
import com.yangshuai.library.base.utils.KLog

/**
 * 用于主模块的组件初始化
 * @author hcp
 * @created 2019/3/13
 */
class MainModuleInit : ModuleInitImpl {
    override fun onInitAhead(application: Application): Boolean {
        KLog.e("主业务模块初始化 -- onInitAhead")
        return false
    }

    override fun onInitLow(application: Application): Boolean {
        return false
    }
}