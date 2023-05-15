package com.yangshuai.library.base.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

import com.yangshuai.library.base.observer.ResponseObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @Author hcp
 * @Created 6/30/19
 * @Editor hcp
 * @Edited 6/30/19
 * @Type
 * @Layout
 * @Api
 */
public abstract class EditCompleteWatcher implements TextWatcher {

    private Disposable disposable;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        // 做200ms的延迟搜索，如果200ms内还有输入则取消上一次搜索
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        Observable.just(s.toString())
                .delay(300, TimeUnit.MILLISECONDS)
                .doOnSubscribe(disposable1 -> disposable = disposable1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<String>() {
                    @Override
                    public void onSuccess(String data) {
                        EditCompleteWatcher.this.onComplete(data);
                    }
                });

    }

    public abstract void onComplete(String text);
}
