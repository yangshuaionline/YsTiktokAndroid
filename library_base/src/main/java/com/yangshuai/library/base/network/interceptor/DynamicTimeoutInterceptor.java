package com.yangshuai.library.base.network.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author hcp
 * @date 2020/7/22
 */
public class DynamicTimeoutInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String questUrl = request.url().toString();
        if (questUrl.contains("erp.transaction.api/order/estate/queryOrder")) {
            chain.withConnectTimeout(15, TimeUnit.SECONDS);
        }
        return chain.proceed(request);
    }
}