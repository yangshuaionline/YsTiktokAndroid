package com.yangshuai.module.pay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.Base64
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.module.pay.PayApi
import com.yangshuai.module.pay.bean.PayBean

/**
 * <pre>
 *     author : pty
 *     time   : 2022 07 09 15:59
 *     desc   :  支付 vm
 * </pre>
 */
class PayViewModel(application: Application):AndroidViewModel(application) {
//    /**
//     * @param 订单号
//     * */
//    fun getData(needFlag:String?,xsdmxBillNo: String, listener: ResponseListener<PayBean>) {
//        val map = mutableMapOf<String, String>()
//        needFlag?.let { map["needFlag"] = it }
//        map["xsdmxBillNo"] = xsdmxBillNo
//        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray())
//        val params = mutableMapOf<String, String>()
//        params["data"] = encode
//        RetrofitManager.create(PayApi::class.java)
//            .getPay(params)
//            .compose(RxUtils.responseTransformer())
//            .compose(RxUtils.schedulersTransformer())
//            .doOnSubscribe(listener::addDisposable)
//            .subscribe(object : ResponseObserver<PayBean>() {
//                override fun onSuccess(data: PayBean) {
//                    listener.loadSuccess(data)
//                }
//
//                override fun onError(code: String?, msg: String?) {
//                    listener.loadFailed(code, msg)
//                }
//
//            })
//    }

    /**
     * @param
     * */
    fun getSecondData(releaseType:String?,xsdmxBillNo: String, listener: ResponseListener<PayBean>) {
        val map = mutableMapOf<String, String>()
        releaseType?.let { map["releaseType"] = it }
        map["xsdmxBillNo"] = xsdmxBillNo
        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray())
        val params = mutableMapOf<String, String>()
        params["data"] = encode
        RetrofitManager.create(PayApi::class.java)
            .getSecondPay(params)
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