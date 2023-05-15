package com.yangshuai.module.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.yangshuai.library.base.network.RetrofitManager
import com.yangshuai.library.base.observer.ResponseObserver
import com.yangshuai.library.base.utils.RxUtils
import com.yangshuai.module.main.api.MainApi
import com.yangshuai.module.main.constant.Constants
import com.yangshuai.module.main.entity.CompanyInfo

/**
 * @Author zrh
 * @Created 6/21/19
 * @Editor zrh
 * @Edited 6/21/19
 * @Type
 * @Layout
 * @Api
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    var mutableLiveData :MutableLiveData<String> =MutableLiveData();
    /**
     * 检查更新
     */
    fun checkUpdate() {
        RetrofitManager
            .create(MainApi::class.java)
            .getInvitations()
            .compose(RxUtils.responseTransformer())
            .compose(RxUtils.schedulersTransformer())
            .subscribe(object : ResponseObserver<List<Any>>() {
                override fun onSuccess(data: List<Any>?) {

                }

                override fun onError(code: String?, msg: String?) {
                    super.onError(code, msg)
                }

            })

        var datadata = Gson().fromJson(Constants.datdata, CompanyInfo::class.java);
    }
}