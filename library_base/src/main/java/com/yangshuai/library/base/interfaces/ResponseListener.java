package com.yangshuai.library.base.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * 数据请求的响应回调(应用场景：ViewModel中处理加载数据方法实现本接口，将状态返回给Activity，进行一些例如loadingView的操作)
 *
 * @author hcp
 * @created 2019/4/18
 */
public interface ResponseListener<T> {

    /**
     * 加载成功
     */
    void loadSuccess(T data);

    /**
     * 加载失败
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误提示语
     */
    void loadFailed(String errorCode, String errorMsg);

    /**
     * 添加Rx订阅
     * @param disposable
     */
    void addDisposable(Disposable disposable);

}
