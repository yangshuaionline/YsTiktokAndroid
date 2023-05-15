package com.yangshuai.library.base.utils;

import com.yangshuai.library.base.api.BaseApi;
import com.yangshuai.library.base.network.RetrofitManager;
import com.yangshuai.library.base.observer.ResponseObserver;

/**
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2020/1/6 22:53
 * @UpdateUser: 登录相关工具类
 * @UpdateDate: 2020/1/6 22:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ExitUtil {

    /**
     * 退出登录
     * */
    public static void exitLogin() {
        RetrofitManager.create(BaseApi.class)
                .oidcLogout()
                .compose(RxUtils.responseTransformer())
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        KLog.i("onSuccess" + data);
                    }

                    @Override
                    public void onError(String code, String msg) {
                        KLog.i("onError" + code + " msg " + msg);
                    }
                });
    }

}