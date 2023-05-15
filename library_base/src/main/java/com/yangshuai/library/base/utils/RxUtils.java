package com.yangshuai.library.base.utils;

import com.yangshuai.library.base.entity.BaseResponse;
import com.yangshuai.library.base.exception.ApiException;
import com.yangshuai.library.base.exception.NoDataException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author hcp
 * @Created 3/29/19
 * @Editor hcp
 * @Edited 3/29/19
 * @Type
 * @Layout
 * @Api
 */
public class RxUtils {
    /**
     * 线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 处理请求结果，切换线程
     */
    public static <B extends BaseResponse<T>, T extends Object> ObservableTransformer<B, T> responseTransformer() {
        return upstream -> upstream.flatMap(new ResponseFunction<>());
    }

    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> response) throws Exception {

            if (response == null) {
                return Observable.error(new ApiException("500", "服务器未返回响应数据"));
            }

            if (response.getErrorCode().equals("0")) {

                if (response.getData() == null) {
                    return Observable.error(new NoDataException());
                }

                return Observable.just(response.getData());
            } else {
                return Observable.error(new ApiException(response.getErrorCode(), response.getErrorMsg()));
            }
        }
    }
}
