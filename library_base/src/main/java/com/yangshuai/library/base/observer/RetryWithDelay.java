package com.yangshuai.library.base.observer;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;


/**
 * @Author hcp
 * @Created 7/10/19
 * @Editor hcp
 * @Edited 7/10/19
 * @Type
 * @Layout
 * @Api
 */
public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

    private int maxRetryTimes;
    private long retryDelay;
    private TimeUnit timeUnit;

    private int retryTimes = 0;

    public RetryWithDelay(int maxRetryTimes, long retryDelay, TimeUnit timeUnit) {
        this.maxRetryTimes = retryTimes;
        this.retryDelay = retryDelay;
        this.timeUnit = timeUnit;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (retryTimes < maxRetryTimes) {
                        return Observable.timer(retryDelay,
                                timeUnit);
                    }
                    return Observable.error(throwable);
                });
    }
}
