package com.yangshuai.module.pay.authorization

import android.app.Activity
import com.google.gson.Gson
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.Base64
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.module.pay.PayApi

/**
 * @Author yangshuai
 * @Date : 2023-03-02 16:25
 * @Version 1.0
 * Description:
 */
class AuthorizationManager {
    /**
     * 授权确认
     */
    fun addAuthorization(contactName:String,phone:String,activity: Activity){
        val map = mutableMapOf<String,String>()
        map["contactName"] = contactName
        map["phone"] = phone
        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray()) // 方式一
        val params = mutableMapOf<String,String>()
        params["data"] = encode
        RetrofitManager.create(PayApi::class.java)
            .addAuthorization(params)
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<Any?>() {
                override fun onSuccess(data: Any?) {
//                    var dialog: DialogBuilder?=null
//                    dialog = DialogBuilder(activity)
//                        .setAlert("签署成功")
//                        .setTitle("提示")
//                        .setCenterText("知道了")
//                        .setCenterListener{
//                            dialog?.dismiss()
//                            activity.finish()
//                        }
//                        .build() as DialogBuilder?
//                    dialog?.show()
                }

                override fun onError(code: String?, msg: String?) {
//                    var dialog: DialogBuilder?=null
//                    dialog = DialogBuilder(activity)
//                        .setAlert("${code}${msg}")
//                        .setTitle("提示")
//                        .setCenterText("知道了")
//                        .setCenterListener{
//                            dialog?.dismiss()
//                        }
//                        .build() as DialogBuilder?
//                    dialog?.show()
                }

            })
    }
}