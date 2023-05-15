package com.yangshuai.module.dialog

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.yangshuai.library.base.utils.AppContext

/**
 * @Author yangshuai
 * @Date : 2023-03-24 17:18
 * @Version 1.0
 * Description:
 */
class MyClickableSpan(private val content: String,private val listener:View.OnClickListener) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = ContextCompat.getColor(AppContext.getAppContext(), R.color.text_blue_dark)
    }

    override fun onClick(widget: View) {
        listener.onClick(widget)
    }
}