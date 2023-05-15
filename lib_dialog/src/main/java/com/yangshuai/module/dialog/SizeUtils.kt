package com.yangshuai.module.dialog

import android.content.Context
import android.util.TypedValue

/**
 * @Author yangshuai
 * @Date : 2023-03-31 14:50
 * @Version 1.0
 * Description:
 */
class SizeUtils(context:Context) {
    var context:Context? = null
    init {
        this.context = context
    }
    /**
     * dp转px
     * */
    fun dp2px(value: Float): Int {
        val unit = TypedValue.COMPLEX_UNIT_DIP
        val displayMetrics = context?.resources?.displayMetrics
        return (TypedValue.applyDimension(unit, value, displayMetrics) + 0.5f).toInt()
    }
}