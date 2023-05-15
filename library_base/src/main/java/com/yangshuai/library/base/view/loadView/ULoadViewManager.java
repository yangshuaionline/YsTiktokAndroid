package com.yangshuai.library.base.view.loadView;

import androidx.annotation.NonNull;
import android.view.View;

import com.yangshuai.library.base.exception.ExceptionCode;

/**
 * @Author hcp
 * @Created 5/13/19
 * @Editor hcp
 * @Edited 5/13/19
 * @Type
 * @Layout
 * @Api
 */
public class ULoadViewManager {

    private ULoadView uLoadView;
    private View.OnClickListener retryListener;

    public ULoadViewManager(@NonNull View bindView, @NonNull View.OnClickListener retryListener) {
        uLoadView = new ULoadView(bindView);
        this.retryListener = retryListener;
    }

    public void setRetryListener(View.OnClickListener retryListener) {
        this.retryListener = retryListener;
    }

    public void showError(String code, String message) {

        if (code != null) {
            switch (code) {
                case ExceptionCode.NO_NERWORK_ERROR:
                    uLoadView.showNetworkError(retryListener);
                    break;
                case ExceptionCode.NO_DATA_ERROR:
                    uLoadView.showEmpty(message,retryListener);
                    break;
                case "401":
                    showNotAuthority();
                    break;
                default:
                    uLoadView.showErrors(code, message, retryListener);
                    break;
            }
        } else {
            uLoadView.showError(message == null ? "服务器错误" : message, retryListener);
        }

    }

    /**
     * 显示无权限页面(当品牌公司列表数据为空的时候，则显示无权限 edit by lsy 20190801)
     */
    public void showNotAuthority() {
        if (uLoadView != null) {
            uLoadView.showNotAuthority("不好意思，您暂无查看权限！", retryListener);
        }
    }

    public void showLoading() {
        uLoadView.showLoading();
    }

    public void hideLoading() {
        uLoadView.hide();
    }

    public void hideError() {
        uLoadView.hide();
    }

    public ULoadView getuLoadView() {
        return uLoadView;
    }
}
