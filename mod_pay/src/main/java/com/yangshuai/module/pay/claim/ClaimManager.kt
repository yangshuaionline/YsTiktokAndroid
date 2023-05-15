package com.xxth.module.pay.claim

import android.app.Activity
import com.google.gson.Gson
import com.yangshuai.library.base.interfaces.ResponseListener
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.Base64
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.library.base.utils.ToastUtil
import com.yangshuai.module.dialog.DialogBuilder
import com.yangshuai.module.pay.PayApi
import com.yangshuai.module.pay.claim.bean.ClaimFlagBean
import io.reactivex.disposables.Disposable

/**
 * @Author yangshuai
 * @Date : 2023-03-02 16:02
 * @Version 1.0
 * Description: 认领
 */
class ClaimManager(activity: Activity) {
    private var activity:Activity?=null

    init {
        this.activity = activity
    }

    fun claimFlagUpdate(claimFlag:String,dialogBuilder: DialogBuilder){
        claimFlagUpdate(claimFlag,object : ResponseListener<ClaimFlagBean> {
            override fun loadSuccess(data: ClaimFlagBean?) {


            }

            override fun loadFailed(errorCode: String?, errorMsg: String?) {
                ToastUtil.Short("${errorCode}${errorMsg}")
            }

            override fun addDisposable(disposable: Disposable?) {
            }

        })
    }
    /**
     * 企业认领-立即认领
     */
    private fun claimFlagUpdate(claimFlag: String, listener: ResponseListener<ClaimFlagBean>){
        val map = mutableMapOf<String,String>()
        map["claimFlag"] = claimFlag
        val encode = Base64.getEncoder().encodeToString(Gson().toJson(map).toByteArray()) // 方式一
        val params = mutableMapOf<String,String>()
        params["data"] = encode
        RetrofitManager.create(PayApi::class.java)
            .claimFlagUpdate(params)
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<ClaimFlagBean?>() {
                override fun onSuccess(data: ClaimFlagBean?) {
                    listener.loadSuccess(data)
                }

                override fun onError(code: String?, msg: String?) {
                    listener.loadFailed(code,msg)
                }

            })
    }
}