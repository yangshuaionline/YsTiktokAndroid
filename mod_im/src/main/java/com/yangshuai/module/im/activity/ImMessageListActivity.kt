package com.yangshuai.module.im.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.BaseToolBarActivity
import com.yangshuai.library.base.dialog.LoadingDialog
import com.yangshuai.library.base.router.RouterManager
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.utils.KLog
import com.yangshuai.module.im.R
import com.yangshuai.module.im.bean.MessageBean
import com.yangshuai.module.im.databinding.ImActMessageBinding
import com.yangshuai.module.im.viewmodel.ImGroupViewModel
import io.rong.imlib.RongIMClient
import io.rong.imlib.listener.OnReceiveMessageWrapperListener
import io.rong.imlib.model.Message
import io.rong.imlib.model.ReceivedProfile

/**
 * @Author yangshuai
 * @Date 2023-05-09 周二 13:44
 * @Description
 * 消息列表页面
 */
@SuppressLint("ClickableViewAccessibility")
@Route(path = RouterPath.IM.IM_MESSAGE)
class ImMessageListActivity  : BaseToolBarActivity<ImActMessageBinding, ImGroupViewModel>() {
    var screenHeight: Int? = 0
    var keyHeight: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.im_act_message)
    }

    override fun initData() {
        super.initData()
        initView()
    }
    val handler = Handler()
    private fun initView() {
        mBinding.viewModel = mViewModel
        handler.postDelayed({
            val list = mutableListOf<MessageBean>()
            for(i in 0..20){
                val bean = MessageBean()
                bean.imagerUrl = "head"
                bean.name = "测试$i"
                list.add(bean)
            }
            mViewModel.itemListData.clear()
            mViewModel.itemListData.addAll(list)
        },1000)
        connectRong()//连接融云
    }

    override fun initParam() {
        ARouter.getInstance().inject(this)
        RouterManager.addPage(RouterPath.IM.IM_MESSAGE,this)
    }
    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        RouterManager.remove(this)
        super.onDestroy()
    }
    //连接融云
    private fun connectRong(){
        var token = "后台获取的 token"
        var saveToken = "上次连接成功存储到本地得token"
        if(token == saveToken){
            //如果相同，说明连接过

        }else{
            //如果不相同，说明需要重新链接
            RongIMClient.connect(token,object : RongIMClient.ConnectCallback(){
                override fun onSuccess(t: String?) {
                    //连接成功，如果 onDatabaseOpened() 时没有页面跳转，也可在此时进行跳转。
                    saveToken = token
                    receivedMessage()//监听收到得消息
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
    //接收消息
    private fun receivedMessage(){
        RongIMClient.addOnReceiveMessageListener(object : OnReceiveMessageWrapperListener() {
            //收到得消息
            override fun onReceivedMessage(message: Message?, profile: ReceivedProfile?) {
                if(profile?.hasPackage() == false){
                    //当前为最后一条消息
                }
                KLog.i(message?.content)
            }
            //从 5.2.3 版本开始，每次连接成功后，离线消息收取完毕时会触发以下回调方法。如果没有离线消息，连接成功后会立即触发。
            override fun onOfflineMessageSyncCompleted() {
                super.onOfflineMessageSyncCompleted()
            }

        })
    }
}