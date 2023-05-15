package com.yangshuai.library.base.utils;

import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hcp
 * @create 2019-4-12
 * @Describe
 */
public class SpannableStringUtils {

    /**
     * 高亮显示文字
     *
     * @param text   显示的文本
     * @param target 高亮的文本
     * @param color  高亮显示的颜色
     * @return 处理结果
     */
    public static SpannableString highlightText(String text, String target,
                                                String color) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(span, text.indexOf(target), text.lastIndexOf(target) + target.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


    /**
     * 高亮显示文本
     *
     * @param text  显示的文本
     * @param start 开始的位置
     * @param end   结束的位置
     * @param color 高亮显示的颜色
     * @return 处理结果
     */
    public static SpannableString highlightText(String text, int start, int end,
                                                String color) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(span, start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 掩饰手机号
     * //151 1111 1111 -> 151 **** 1111
     *
     * @param phoneNumber
     * @return
     */
    public static String dealPhoneNumber(String phoneNumber) {
        if (!StringUtils.isEmpty(phoneNumber)) {
            int len = phoneNumber.length();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                if (i > 2 && i < 7) {
                    builder.append("*");
                } else builder.append(phoneNumber.charAt(i));
                if (i == 2 || i == 6) {
                    if (i != len - 1) builder.append(" ");
                }
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 显示富文本内容
     */
    public static Spanned showRichText(String richText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(richText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(richText);
        }
    }
}
