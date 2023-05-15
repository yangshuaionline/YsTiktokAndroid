package com.yangshuai.tiktok

import android.os.Bundle
import android.os.Handler
import com.yangshuai.library.base.BaseActivity
import com.yangshuai.library.base.router.RouterManager
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.viewmodel.NoViewModel
import com.yangshuai.tiktok.databinding.ActivityMainBinding


class SplashActivity :  BaseActivity<ActivityMainBinding, NoViewModel>() {
    val handler =Handler()
    override fun initContentView(savedInstanceState: Bundle?) = R.layout.activity_main

    override fun initVariableId(): Int = BR.viewModel

    override fun initData() {
        super.initData()
        //        handler.postDelayed({ UseIm().goMessageList() },1000)
        handler.postDelayed(Runnable {
            RouterManager.goPage(RouterPath.App.ROUTE_SELECT_IDENTITY_PATH)
            finish()
        },1000)
//        var  intent =Intent(this,SelectIdentityActivity::class.java)
//        handler.postDelayed(Runnable { startActivity(intent) },1000)
    }
}