package com.yangshuai.library.base.utils

import android.view.View

/**
 * @Author yangshuai
 * @Date 2023-05-09 周二 15:13
 * @Description
 * 防连续得点击事件
 */
abstract class OnSingleClickListener :View.OnClickListener{
    private val minTime = 1000 //最小的点击间隔1s
    private var mLastClickTime = 0L//上一次点击的时间

    override fun onClick(v: View) {
        var curClickTime = System.currentTimeMillis();
        if ((curClickTime - mLastClickTime) >= minTime) {
            mLastClickTime = curClickTime;
            onSingleClick(v);
        }
    }
    abstract fun onSingleClick(v:View)
}