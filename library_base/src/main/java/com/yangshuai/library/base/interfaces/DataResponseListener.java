package com.yangshuai.library.base.interfaces;

/**
 * @author hcp
 * Create on 2020-03-27 18:38
 */
public interface DataResponseListener<T> {

    void onResponse(T t);

    default void onClose() {

    }
}
