package com.yangshuai.module.login

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.dialog.LoadingDialog
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.viewmodel.NoViewModel
import com.yangshuai.module.login.databinding.LoginActLoginBinding
import kotlinx.coroutines.*
import java.util.*
/**
 * 登录页
 * */
@Route(path = RouterPath.Signin.ROUTE_SIGNIN_PHONE)
class LoginActivity : BaseToolBarActivity<LoginActLoginBinding, NoViewModel>() {
    @Autowired
    @JvmField
    var xsdmxBillNo: String? = null//订单编号
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act_login)
    }

    override fun initData() {
        super.initData()
        initView()
    }

    private fun initView() {


    }

    override fun initParam() {
        ARouter.getInstance().inject(this)
    }
    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}