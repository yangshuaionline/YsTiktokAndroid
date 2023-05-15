package com.yangshuai.module.im

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.dialog.LoadingDialog
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.viewmodel.NoViewModel
import com.yangshuai.module.im.databinding.ImActMainBinding
import io.rong.imlib.RongIMClient

/**
 * 消息中转页
 * */
@Route(path = RouterPath.IM.IM_MAIN)
class ImMainActivity : BaseToolBarActivity<ImActMainBinding, NoViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.im_act_main)
    }

    override fun initData() {
        super.initData()
        initView()
    }

    private fun initView() {
        val token = "后台获取的 token"
        val saveToken = "上次连接成功存储到本地得token"
        if(token == saveToken){
            //如果相同，说明连接过


        }else{
            //如果不相同，说明需要重新链接
            RongIMClient.connect(token,object :RongIMClient.ConnectCallback(){
                override fun onSuccess(t: String?) {
                    //连接成功，如果 onDatabaseOpened() 时没有页面跳转，也可在此时进行跳转。
                }

                override fun onError(e: RongIMClient.ConnectionErrorCode?) {
                    when(e){
                        //从 APP 服务请求新 token，获取到新 token 后重新 connect()
                        RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_EXPIRE->{

                        }
                        //连接超时，弹出提示，可以引导用户等待网络正常的时候再次点击进行连接
                        RongIMClient.ConnectionErrorCode.RC_CONNECT_TIMEOUT->{

                        }
                        //其它业务错误码，请根据相应的错误码作出对应处理。
                        else->{

                        }
                    }
                }

                override fun onDatabaseOpened(code: RongIMClient.DatabaseOpenStatus?) {
                    when(code){
                        RongIMClient.DatabaseOpenStatus.DATABASE_OPEN_SUCCESS->{

                        }
                        else->{

                        }
                    }
                }

            })
        }

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