package com.yangshuai.library.base.network.interceptor;


import android.os.Build;

import com.yangshuai.library.base.account.AccountManager;
import com.yangshuai.library.base.appinfo.AppInfoManager;
import com.yangshuai.library.base.utils.AppContext;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token拦截器，用于请求时添加token请求头，以及保存token响应头
 *
 * @Author hcp
 * @Created 4/20/19
 * @Editor hcp
 * @Edited 5/11/19
 * @Type
 * @Layout
 * @Api
 */
public class TokenInterceptor implements Interceptor {

    private static final String HEADER_KEY = "Authorization"; // token

    // 城市分公司
    private static final String ORGAN_ID = "X-Organ-Id"; // 品牌ID
    private static final String CITY_CODE = "X-City-Code"; // 城市ID
    private static final String USER_AGENT = "User-Agent"; // 客户端
    private static final String X_REQUEST_WITH = "X-Requested-With"; // AJAX请求
    private static final String X_DEVICE_NAME = "x-device-name"; // AJAX请求

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // 如果本地存在token，则添加token请求头
        if (AccountManager.getToken() != null) {
            requestBuilder.header(HEADER_KEY, AccountManager.getToken());
        }

        // 城市分公司代码(这里的值后续都需要更换成从API获取)
//        if (request.headers().get(ORGAN_ID) == null && AccountManager.getOrganId() != null) {
//            requestBuilder.header(ORGAN_ID, AccountManager.getOrganId());
//        }
//        if (request.headers().get(CITY_CODE) == null && AccountManager.getCityCode() != null){
//            requestBuilder.header(CITY_CODE, AccountManager.getCityCode());
//        }
        // ajax请求
        requestBuilder.header(X_REQUEST_WITH, "XMLHttpRequest");
        requestBuilder.header(USER_AGENT, AppInfoManager.getUserAgent(AppContext.getAppContext()));
        String phoneModel=Build.MODEL;
        requestBuilder.header(X_DEVICE_NAME,phoneModel);


        // 如果响应头中存在token则更新本地tokena
        Response response = chain.proceed(requestBuilder.build());
        Headers headers = response.headers();
        String token = headers.get(HEADER_KEY);
        if (token != null) {
            AccountManager.saveToken(token);
        }
        return response;
    }
}
