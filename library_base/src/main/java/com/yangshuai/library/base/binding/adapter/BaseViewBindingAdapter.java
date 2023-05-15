package com.yangshuai.library.base.binding.adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * 基础界面绑定方法
 *
 * @author hcp
 * @date 15:05
 */
public class BaseViewBindingAdapter {
    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean isVisibility) {
        view.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("invisibility")
    public static void setInvisibility(View view, boolean isVisibility) {
        view.setVisibility(isVisibility ? View.VISIBLE : View.INVISIBLE);
    }
}
