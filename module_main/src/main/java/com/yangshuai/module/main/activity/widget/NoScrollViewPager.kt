package com.yangshuai.module.main.activity.widget

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent

/**
 * 禁止横向滑动的viewpager
 */
class NoScrollViewPager(context: Context?, attrs: AttributeSet?) : ViewPager(context!!, attrs) {
    // 是否禁止 viewpager 左右滑动
    private val noScroll = true
    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) false else super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) false else super.onInterceptTouchEvent(arg0)
    }
}