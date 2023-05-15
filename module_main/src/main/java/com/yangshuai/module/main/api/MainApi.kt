package com.yangshuai.module.main.api

import com.yangshuai.library.base.entity.BaseResponse
import io.reactivex.Observable
import retrofit2.http.POST

interface MainApi {

    /**
     * 网络api
     */
    @POST("xxxxxxxxxxxxxxxx")
    fun getInvitations(): Observable<BaseResponse<List<Any>>>
}