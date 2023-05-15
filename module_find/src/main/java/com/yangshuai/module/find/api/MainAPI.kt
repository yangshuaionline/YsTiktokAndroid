package com.yangshuai.module.find.api
import com.yangshuai.module.find.bean.BannerBean
import retrofit2.http.POST

interface MainAPI {
    /**
     * 轮播图
     */
    @POST("/banner/json")
    suspend  fun getBannerBata (): List<BannerBean>


}