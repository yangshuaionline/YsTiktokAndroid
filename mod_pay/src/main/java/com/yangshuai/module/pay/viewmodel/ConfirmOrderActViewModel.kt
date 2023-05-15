package com.yangshuai.module.pay.viewmodel

import android.app.Activity
import android.app.Application
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.yangshuai.library.base.BaseConst
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.module.pay.PayApi
import com.yangshuai.module.pay.bean.BuyServiceBean
import com.yangshuai.module.pay.bean.PayBean
import java.util.*

/**
 * @author： pc$
 * @Create on： $ 16:18:31$
 * @Dec：
 */
class ConfirmOrderActViewModel(application: Application) : AndroidViewModel(application) {
    var activity:Activity?=null
    var fromType: String? = null
    fun showPriceVisibility():Int{
        return when(fromType){
            BaseConst.FROM_ASSET-> View.GONE
            else -> View.VISIBLE
        }
    }
    //是否需要公章显示
    fun showSealVisibility():Int{
        return when(fromType){
            BaseConst.FROM_ASSET-> View.VISIBLE
            else -> View.GONE
        }
    }
    //showRateVisibility  超额收益显示隐藏
    fun showRateVisibility(bean: BuyServiceBean.DataBean?):Int{
        return when(fromType){
            BaseConst.FROM_ASSET->{
                if(bean == null){
                    View.GONE
                }else{
                    when(bean.payType){
                        1->View.VISIBLE
                        else->View.GONE
                    }
                }
            }
            else -> View.GONE
        }
    }
    fun showPriceDesc(data: BuyServiceBean.DataBean?):String{
        data?.let {
            return when(fromType){
                BaseConst.FROM_ASSET-> data.payTypeDesc
                else -> data.billVipAmt
            }
        }
        return BaseConst.NULL
    }

    /**
     * @param 订单号
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(price:String, planId:String?, needFlag:String?, serviceId: String, excessReturnRate:String?, listener: ResponseListener<PayBean>) {
        val map = mutableMapOf<String, String>()
        if(fromType == BaseConst.FROM_ASSET)  planId?.let { map["planId"] = it  }
        if(fromType == BaseConst.FROM_ASSET) needFlag?.let { map["needFlag"] = it }
        if(fromType == BaseConst.FROM_ASSET) excessReturnRate?.let {
            if(it!=BaseConst.NULL){
                map["excessReturnRate"] = it.replace("%".toRegex(), "")
            }
        }
        map["serviceId"] = serviceId
        map["price"] = price
        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray())
        val params = mutableMapOf<String, String>()
        params["data"] = encode
        RetrofitManager.create(PayApi::class.java)
            .getPay(params)
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .doOnSubscribe(listener::addDisposable)
            .subscribe(object : ResponseObserver<PayBean>() {
                override fun onSuccess(data: PayBean) {
                    listener.loadSuccess(data)
                }

                override fun onError(code: String?, msg: String?) {
                    listener.loadFailed(code, msg)
                }

            })
    }
}