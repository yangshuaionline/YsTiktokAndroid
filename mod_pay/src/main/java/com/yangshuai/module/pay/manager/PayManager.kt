package com.xxth.module.pay.manager

import android.app.Activity
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.yangshuai.library.base.BaseConst
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.Base64
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.library.base.utils.ToastUtil
import com.yangshuai.library.base.utils.UVerify
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.dialog.DialogUser
import com.yangshuai.module.dialog.alert.AlertBean
import com.yangshuai.module.pay.PayApi
import com.yangshuai.module.pay.PayRouterPath
import com.yangshuai.module.pay.PayRouterPath.Companion.ROUT_CONFIRM_ORDER_PATH
import com.yangshuai.module.pay.PayRouterPath.Companion.ROUT_PAY_AUTHORIZE
import com.yangshuai.module.pay.bean.BuyServiceBean
import com.yangshuai.module.pay.request.BaseRequestBean
import com.yangshuai.module.pay.request.ResourceRequest
import com.yangshuai.module.pay.request.ServiceRequest
import com.xxth.module.pay.claim.ClaimManager

/**
 * @Author yangshuai
 * @Date : 2023-02-14 16:29
 * @Version 1.0
 * Description: 支付网络请求管理类
 */
class PayManager {
    /**
     * 服务商邀约，资产场景服务购买 生成订单
     * */
    fun buy(activity: Activity,bean: ResourceRequest){
        val map = mutableMapOf<String, String>()
        var judge = UVerify.get()
            .empty(bean.serviceId, "商品id不能为空")
            .empty(bean.billVipAmt, "现金金额不能为空")
            .empty(bean.planId, "资产场景服务计划id不能为空")
            .verify()
        if(judge!=null){
            ToastUtil.Short(judge)
            return
        }
        map["serviceId"] = bean.serviceId
        map["price"] = bean.billVipAmt
        map["planId"] = bean.planId
        Log.i("======>","====>${bean.excessReturnRate}")
        bean.excessReturnRate?.let { map["excessReturnRate"] = it }
        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray())
        val params = mutableMapOf<String, String>()
        params["data"] = encode
        RetrofitManager.create(PayApi::class.java)
            .getBuyService(params)
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<BuyServiceBean>() {
                override fun onSuccess(data: BuyServiceBean?) {
                    bean.listener?.loadSuccess(data)
                    resultOk(activity,bean.serviceId,data,bean, BaseConst.FROM_ASSET)
                }
                override fun onError(code: String?, msg: String?) {
                    bean.listener.loadFailed(code,msg)
                    code?.let {c->
                        msg?.let { m->
                            resultFailt(activity,c,m)
                        }
                    }
                }
            })
    }
    /**
     * 服务购买 生成订单
     * */
    fun buy(activity: Activity,bean: ServiceRequest){
        val map = mutableMapOf<String, String>()
        map["serviceId"] = bean.serviceId
        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray())
        val params = mutableMapOf<String, String>()
        params["data"] = encode
        RetrofitManager.create(PayApi::class.java)
            .getBuyService(params)//service
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<BuyServiceBean>() {
                override fun onSuccess(data: BuyServiceBean?) {
                    bean.listener?.loadSuccess(data)
                    resultOk(activity,bean.serviceId,data,bean,BaseConst.FROM_SERVICE)
                }
                override fun onError(code: String?, msg: String?) {
                    bean.listener.loadFailed(code,msg)
                    code?.let {c->
                        msg?.let { m->
                            resultFailt(activity,c,m)
                        }
                    }
                }
            })
    }

    /**
     * 返回200结果回调流程
     * */
    private fun resultOk(activity: Activity, serviceId:String, data: BuyServiceBean?, bean: BaseRequestBean, fromType:String){
//        var data = BuyServiceBean()
//        data.data = BuyServiceBean.DataBean()
//        data.data.code = 15
        data?.let {d->
            when (d.data.code) {
                //60005：已授权投行提示
                //60011：已购买当前服务
                //60012：不可购买自己发布的服务
                //60015：您已完成上市计划书十二个业务，无需重复购买
                60005,60011,60012,60015->{
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean().setMessageBlack(data.data.message)
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setCenter("知道了"){
                            dialog?.dismiss()
                        }
                        .build()
                }
                //60006：非工商注册企业
                60006->{
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean().setMessageBlack(data.data.message)
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setLeft("暂不授权"){
                            dialog?.dismiss()
                        }
                        .setRight("立即授权"){
                            dialog?.dismiss()
                            ARouter.getInstance()
                                .build(ROUT_PAY_AUTHORIZE)
                                .navigation()
                        }
                        .build()
                }
                //60007：再次认领
                60007->{
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean().setMessageBlack(data.data.message)
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setLeft("暂不认领"){
                            dialog?.dismiss()
                        }
                        .setRight("立即认领"){
                            dialog?.let { it1 ->
                                ClaimManager(activity).claimFlagUpdate("0",
                                    it1
                                )
                            }
                        }
                        .build()
                }
                //60008：未完成上市目标
                //60009：未完成企业基础
                //60001 ：您当前还未完成商业模式业务，不可购买，请先去完成商业模式业务
                60001,60008,60009->{
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean().setMessageBlack(data.data.message)
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setLeft("在考虑一下"){
                            dialog?.dismiss()
                        }
                        .setRight("去完成"){
                            dialog?.dismiss()
                            activity.finish()
//                            EventBus.getDefault().post(EvenCommon.GO_VALUE)
                        }
                        .build()
                }
                //60010：服务确认书签署中（此时contractInfo会有值）
                60010->{
//                    var msg = "签署完成后请回寄合同\n"
//                    msg += "${d.data.contractInfo.explain}\n"
//                    msg += "回寄地址：${d.data.contractInfo.address}\n"
//                    msg += "收件人：${d.data.contractInfo.name}\n"
//                    msg += "联系电话：${d.data.contractInfo.phone}"
                    var msg = "签署完成后请回寄合同\n"
                    msg += "${d.data.contractInfo.explain}\n"
                    msg += "回寄地址：北京朝阳区建国门外大街88号SOHO现代城B座1206\n"
                    msg += "收件人：运营部\n"
                    msg += "联系电话： 15010948403"
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean().setMessageBlue(msg)
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setCenter("知道了"){
                            dialog?.dismiss()
                        }
                        .build()
                }
                // 60013：二次下单
                60013->{
                    var dialog: DialogBuilder?=null
                    val alertBean = AlertBean().setMessageBlack(data.data.message)
                    dialog = DialogUser().buildDialog(activity,alertBean)
                        .setTitle("提示")
                        .setLeft("在考虑一下"){
                            dialog?.dismiss()
                        }
                        .setRight("去支付"){
                            bean.listener?.loadSuccess(data)
//                            EventBus.getDefault().post(EvenCommon.CHANGE_FILE)
                            //跳转支付页面
                            ARouter.getInstance()
                                .build(PayRouterPath.ROUT_PAY_PATH)
                                .withString("xsdmxBillNo",d.data.xsdmxBillno)
                                .withString("billVipAmt",d.data.price)
                                .withString("RouterType","4")
                                .withString("type","2")
                                .withString("fromType",fromType)
                                .withString("id",d.data.id)
                                .navigation()
                            activity.finish()
                        }
                        .build()
                }
                //60014：正常下单
                60014->{
                    bean.listener?.loadSuccess(data)
                    when(fromType){
                        BaseConst.FROM_ASSET->{
                            var resourceBean = bean as ResourceRequest
//                            ARouter.getInstance()//跳转确认订单页
//                                .build(ROUT_CONFIRM_ORDER_PATH)
//                                .withSerializable("dataResource",resourceBean)
//                                .withSerializable("RouterType",fromType)
//                                .withString("serviceId",serviceId)
//                                .withString("fromType",fromType)
//                                .withString("planId",resourceBean.planId)
//                                .withString("id",d.data.id)
//                                .navigation()
                        }
                        else->{
                            val serviceBean = bean as ServiceRequest
                            ARouter.getInstance()//跳转确认订单页
                                .build(ROUT_CONFIRM_ORDER_PATH)
                                .withSerializable("dataService",serviceBean)
                                .withSerializable("RouterType",fromType)
                                .withString("serviceId",serviceId)
                                .withString("fromType",fromType)
                                .withString("id",d.data.id)
                                .navigation()
                        }
                    }

                }
                //60016：机构审核未通过，请重新进行认证 原因：营业执照较为模糊审核不通过
                60016->{
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean()
                        .setMessageBlack(data.data.message)
                        .setMessageOrange(data.data.explain)
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setLeft("暂不认证"){
                            dialog?.dismiss()
                        }
                        .setRight("重新认证"){
                            dialog?.dismiss()
                            activity.finish()
//                            ARouter.getInstance().build(RouterPath.Mine.ROUTE_MINE_AGENCY_AUTH).navigation()
                        }
                        .build()
                }
                else->{
                    var dialog: DialogBuilder?=null
                    val bean = AlertBean().setMessageBlack("错误码：${data.data.code}")
                    dialog = DialogUser().buildDialog(activity,bean)
                        .setTitle("提示")
                        .setCenter("知道了"){
                            dialog?.dismiss()
                        }
                        .build()
                }
            }
        }
    }
    /**
     * 返回非200结果回调
     * */
    private fun resultFailt(activity: Activity, code:String, msg:String){
        var dialog: DialogBuilder?=null
        val bean = AlertBean().setMessageBlack(code+msg)
        dialog = DialogUser().buildDialog(activity,bean)
            .setTitle("提示")
            .setCenter("知道了"){
                dialog?.dismiss()
            }
            .build()
    }

}