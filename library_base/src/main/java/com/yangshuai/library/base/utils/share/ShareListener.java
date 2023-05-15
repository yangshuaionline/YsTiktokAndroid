package com.yangshuai.library.base.utils.share;

import io.reactivex.disposables.Disposable;

/**
 * 分享监听
 *
 * @author hcp
 * @date 2020/8/5
 */
public interface ShareListener {
    /**
     * 添加分享相关订阅事件
     *
     * @param disposable 订阅事件
     */
    default void addDisposable(Disposable disposable){};

    /**
     * 分享成功
     *
     * @param successMsg 成功信息
     */
    default void shareSuccess(String successMsg){};

    /**
     * 分享失败
     *
     * @param errorMsg 错误信息
     */
    default void shareFailed(String errorMsg){};

    /**
     * 分享取消
     *
     * @param cancelMsg 取消信息
     */
    default void shareCancel(String cancelMsg){};
}
