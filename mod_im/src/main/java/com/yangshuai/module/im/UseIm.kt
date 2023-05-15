package com.yangshuai.module.im

import com.yangshuai.library.base.router.RouterManager
import com.yangshuai.library.base.router.RouterPath
import io.rong.imlib.RongIMClient




/**
 * @Author yangshuai
 * @Date 2023-05-08 周一 14:11
 * @Description
 * IM业务模块使用类，不要使用其他类
 */
class UseIm {
    //跳转消息列表
    fun goMessageList(){
        RouterManager.goPage(RouterPath.IM.IM_MESSAGE)
    }
    //跳转单聊页面
    fun goChatting(){
        RouterManager.goPage(RouterPath.IM.IM_CHATTING)
    }
    //跳转群聊
    fun goGroup(){
        RouterManager.goPage(RouterPath.IM.IM_GROUP)
    }
    //获取IM未读消息数
    fun getUnreadNum(calBack: ImCalBack){
        RongIMClient.getInstance().getTotalUnreadCount(object : RongIMClient.ResultCallback<Int?>() {
            override fun onSuccess(unReadCount: Int?) {
                unReadCount?.let {
                    calBack.setUnreadCount(it)
                }?:let {
                    calBack.setUnreadCount(0)
                }
            }
            override fun onError(ErrorCode: RongIMClient.ErrorCode?) {

            }
        })
    }
    //返回未读数量
    interface ImCalBack{
        fun setUnreadCount(num:Int)
    }


}