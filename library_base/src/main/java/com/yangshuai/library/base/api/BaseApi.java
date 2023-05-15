package com.yangshuai.library.base.api;

import com.yangshuai.library.base.entity.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Time:$[DATE]
 * Author:hcp
 */
public interface BaseApi {
    /**
     * 用户登出 让token失效
     *康宁，这个登出不需要用post
     * @return
     */
    @GET("/sso/oidc/logout")//康宁，这个登出不需要用post
    Observable<BaseResponse<Object>> oidcLogout();

    /**
     * 分享行为埋点
     */
    @POST("/common.core.api/api/v1/trace")
    Observable<BaseResponse<Object>> shareClick(@Body Map<String,Object> body);

}
