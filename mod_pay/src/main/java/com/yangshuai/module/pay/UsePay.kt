package com.yangshuai.module.pay

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.yangshuai.library.base.account.AccountManager
import com.yangshuai.library.base.utils.ToastUtil
import com.yangshuai.library.base.utils.UVerify
import com.yangshuai.module.pay.bean.OrderListBean
import com.yangshuai.module.pay.request.BaseRequestBean
import com.yangshuai.module.pay.request.ResourceRequest
import com.yangshuai.module.pay.request.ServiceRequest
import com.xxth.module.pay.manager.PayManager

/**
 * @Author yangshuai
 * @Date 2023-04-11 周二 17:52
 * @Description TODO
 */
class UsePay {
    companion object{
        /**
         *  资产场景服务、服务市场支付
         *  @param bean ServiceRequest 服务购买 ResourceRequest  资产服务场景
         * */
        fun goPay(activity: Activity, bean: BaseRequestBean){
            if(AccountManager.getToken()==null){
                ToastUtil.Short("请登录后使用该功能")
                return
            }
            when(bean){
                is ServiceRequest ->{
                    val data = bean as ServiceRequest
                    var judge = UVerify.get()
                        .empty(data.billVipAmt, "商品价格不能为空")
                        .empty(data.serviceId, "商品id不能为空")
                        .verify()
                    if(judge!=null){
                        ToastUtil.Short(judge)
                        return
                    }
                    PayManager().buy(activity,data)
                }
                is ResourceRequest ->{
                    val data = bean as ResourceRequest
                    var judge = UVerify.get()
                        .empty(data.serviceId, "商品id不能为空")
                        .empty(data.billVipAmt, "现金金额不能为空")
                        .verify()
                    if(judge!=null){
                        ToastUtil.Short(judge)
                        return
                    }
                    PayManager().buy(activity,data)
                }
            }
        }

        /**
         * 订单继续支付
         * */
        fun orderPay(dataBean: OrderListBean.RecordsBean, fromType:String?, needFlag:String?){
            ARouter.getInstance()
                .build(PayRouterPath.ROUT_PAY_PATH)
                .withString("xsdmxBillNo",dataBean.xsdmxBillno)
                .withString("billVipAmt",dataBean.orderAmt)
                .withString("RouterType","4")
                .withString("type","2")
                .withString("fromType",fromType)
                .withString("id",dataBean.id)
                .navigation()
        }
    }
}