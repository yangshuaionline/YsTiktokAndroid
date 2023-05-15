package com.yangshuai.module.find.api
import io.reactivex.Observable
import com.yangshuai.library.base.entity.BaseResponse
import com.yangshuai.module.find.bean.BannerBean
import com.yangshuai.module.find.bean.FrindeBean
import com.yangshuai.module.find.bean.NewsBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeAPI {
    /**
     * 轮播图
     */
    @POST("/banner/json")
    fun getBannerBata (): Observable<BaseResponse<List<BannerBean>>>

    /**
     * 新闻
     */
    @GET("/article/list/{type}/json")
    fun getHotNewsData (@Path("type") pageNo: Int): Observable<BaseResponse<NewsBean>>

    /**
     * 常用网站
     */
    @GET("/friend/json")
    fun getfriendData (): Observable<BaseResponse<List<FrindeBean>>>
}