package com.yangshuai.library.base.interfaces;

import com.baidu.location.BDLocation;

/**
 * @author hcp
 * @create 2019-5-8
 * @Describe
 */
public interface OnLocationListener {
    void onSuccess(BDLocation bdLocation);

    void onFailed(String message);
}
