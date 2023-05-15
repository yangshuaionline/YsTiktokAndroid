package com.yangshuai.library.base.interfaces;

import android.view.View;

import java.util.Date;

/**
 * @author hcp
 * on 2019-08-16
 */
public interface OnTimeSelectCallback {

    default void onTimeSelect(Date date, View v){};

    default void onTimeSelect(long timeLong, View v){};

    default void clickConfirmButton(){};
}
