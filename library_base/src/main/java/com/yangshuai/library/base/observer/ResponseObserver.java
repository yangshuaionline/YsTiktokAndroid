package com.yangshuai.library.base.observer;

import android.net.ParseException;
import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.yangshuai.library.base.account.AccountManager;
import com.yangshuai.library.base.entity.BaseResponse;
import com.yangshuai.library.base.exception.ApiException;
import com.yangshuai.library.base.exception.ExceptionCode;
import com.yangshuai.library.base.exception.NoDataException;
import com.yangshuai.library.base.exception.NoNetworkException;
import com.yangshuai.library.base.log.ErrorLogToFile;
import com.yangshuai.library.base.router.RouterPath;
import com.yangshuai.library.base.utils.ToastUtil;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class ResponseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
//        KLog.e("isMainThread "+isMainThread());//isMainThread true
//        ErrorLogToFile.writeErrInformationToSdcard(e);//写入日志
        if (e instanceof HttpException) {
            HttpException exception = ((HttpException) e);

            int code = exception.code();
            ErrorLogToFile.writeErrInformationToSdcard("exception "+code);
            if (code <= 500) {
                String msg = "服务器异常"+code;
                String apiCode = code + "";
                try {
                    msg = ((HttpException) e).response().errorBody().string();
                    ErrorLogToFile.writeErrInformationToSdcard("exception  msg "+msg);
                    BaseResponse response = new Gson().fromJson(msg, BaseResponse.class);
                    if (response != null) {
                        if (response.getErrorMsg() != null)
                            msg = response.getErrorMsg();
                        if (response.getRequestId() != null)

                        if (StringUtils.isNotEmpty(response.getErrorCode()))
                            apiCode = response.getErrorCode();
                    }
                    if (code == 400 && ExceptionCode.RESEST_PASSWORD.equals(apiCode)) {
                        ARouter.getInstance().build(RouterPath.Features.ROUTE_TRANSPARENT_DIALOG)
                                .withString("key", ExceptionCode.RESEST_PASSWORD)
                                .greenChannel()
                                .navigation();
                        return;
                    }

                    if (code == 403 && ExceptionCode.NO_BUY_RESOURCES.equals(apiCode)) {
                        ARouter.getInstance().build(RouterPath.Features.ROUTE_TRANSPARENT_DIALOG)
                                .withString("key", ExceptionCode.NO_BUY_RESOURCES)
                                .greenChannel()
                                .navigation();
                    }
                    if (code == 404 ) {
                        msg="404未找到";
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    msg = "IO异常" ;
                } catch (JsonParseException e2) {
                    e2.printStackTrace();
                    msg = "数据解析异常";
                } catch (Exception e3) {
                    e3.printStackTrace();
                    msg = "数据异常 " + e3.getMessage();
                }

                AccountManager.handleInvalidToken(apiCode);
                if (msg != null ) {
                    onError(apiCode, msg + "\n" + "requestid:" );
                }

            } else {
                onError(code + "", "服务器异常");
            }
        } else if (e instanceof ApiException) {
            ApiException exception = ((ApiException) e);
            AccountManager.handleInvalidToken(exception.getCode());
            onError(exception.getCode(), exception.getMsg());
            ErrorLogToFile.writeErrInformationToSdcard("ApiException  msg "+exception.getMsg());
        } else if (e instanceof NoDataException) {
            ErrorLogToFile.writeErrInformationToSdcard("NoDataException   ");
            onSuccess(null);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ErrorLogToFile.writeErrInformationToSdcard("数据解析异常   ");
            ErrorLogToFile.writeErrInformationToSdcard(e);//写入日志
            onError(ExceptionCode.JSON_PARSE_ERROR, "数据解析异常");
        } else if (e instanceof NoNetworkException) {
            ErrorLogToFile.writeErrInformationToSdcard("当前网络不可用，请检查您的网络设置   ");
            onError(ExceptionCode.NO_NERWORK_ERROR, "当前网络不可用，请检查您的网络设置");
        } else if (e instanceof SocketTimeoutException || e instanceof SSLHandshakeException) {
            ErrorLogToFile.writeErrInformationToSdcard("网络超时，请重试");
            onError(ExceptionCode.TIMEOUT_ERROR, "网络超时，请重试");
        } else {
            onError(ExceptionCode.INTERNAL_ERROR,"其他错误。" );//e.getMessage()  //Unable to resolve host "api-beta.yjyz.com": No address associated with hostname
            ErrorLogToFile.writeErrInformationToSdcard("其他错误。");
            ErrorLogToFile.writeErrInformationToSdcard(e);//写入日志
        }
    }

    @Override
    public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T data);

    public void onError(String code, String msg) {
        ToastUtil.Long(msg);
    }
}