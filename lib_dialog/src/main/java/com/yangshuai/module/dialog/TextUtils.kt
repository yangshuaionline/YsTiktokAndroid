package com.yangshuai.module.dialog

import android.text.SpannableString
import android.text.Spanned
import android.view.View
import java.util.regex.Pattern

/**
 * @Author yangshuai
 * @Date : 2023-03-24 17:24
 * @Version 1.0
 * Description:
 */
object TextUtils {
    fun matcherSearchText(text: String, keyword: String,listener:View.OnClickListener): SpannableString {
        val ss = SpannableString(text)
        val pattern = Pattern.compile(keyword)
        val matcher = pattern.matcher(ss)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
//            Log.i("=======>", "start->${start}    end->${end}")
            val myClickableSpan = MyClickableSpan(keyword,listener)
            ss.setSpan(myClickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return ss
    }
}