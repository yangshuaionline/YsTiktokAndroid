package com.yangshuai.library.base.entity;

import io.reactivex.disposables.Disposable;

/**
 * @Author hcp
 * @Created 4/20/19
 * @Editor hcp
 * @Edited 4/20/19
 * @Type
 * @Layout
 * @Api
 */
public class BaseDisposable {

    private Disposable disposable;

    public BaseDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    public Disposable get() {
        return disposable;
    }
}
