package com.yangshuai.tiktok

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.account.AccountManager
import com.yangshuai.library.base.router.RouterManager
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.viewmodel.NoViewModel
import com.yangshuai.tiktok.databinding.ActivitySelectIdentityBinding
import kotlinx.android.synthetic.main.activity_select_identity.*

@Route(path = RouterPath.App.ROUTE_SELECT_IDENTITY_PATH)
class SelectIdentityActivity : BaseToolBarActivity<ActivitySelectIdentityBinding,NoViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_identity)
        setToolbarTitleCenter("选择身份")
        initParam()
        //投资人
        tv_investor.setOnClickListener {
            AccountManager.saveUsedType("1");
            ARouter.getInstance().build(RouterPath.Main.ROUTE_MAIN_KT_PATH).navigation()
        }
        //资源商
        tv_resource.setOnClickListener {
            AccountManager.saveUsedType("2");
            ARouter.getInstance().build(RouterPath.Main.ROUTE_MAIN_KT_PATH).navigation()
        }
        //服务商
        tv_service.setOnClickListener {
            AccountManager.saveUsedType("3");
            ARouter.getInstance().build(RouterPath.Main.ROUTE_MAIN_KT_PATH).navigation()
        }
        //企业
        tv_enterprise.setOnClickListener {
            AccountManager.saveUsedType("4");
            ARouter.getInstance().build(RouterPath.Main.ROUTE_MAIN_KT_PATH).navigation()
        }

    }
    override fun initParam() {
        ARouter.getInstance().inject(this)
        RouterManager.addPage(RouterPath.App.ROUTE_SELECT_IDENTITY_PATH,this)
    }
    override fun onDestroy() {
        RouterManager.remove(this)
        super.onDestroy()
    }
}