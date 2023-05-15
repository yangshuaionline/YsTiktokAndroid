package com.yangshuai.library.base.network.interceptor;

import com.yangshuai.library.base.exception.NoNetworkException;
import com.yangshuai.library.base.utils.AppContext;
import com.yangshuai.library.base.utils.NetworkMonitor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Author hcp
 * @Created 4/22/19
 * @Editor hcp
 * @Edited 4/22/19
 * @Type
 * @Layout
 * @Api
 */
public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        boolean connected = NetworkMonitor.isContected(AppContext.getAppContext());
        if (connected) {
            return chain.proceed(chain.request());
        } else {
            //TODO  页面刷新多次数据无网络会闪退 2019/7/6
            throw new NoNetworkException();
        }
    }
}
