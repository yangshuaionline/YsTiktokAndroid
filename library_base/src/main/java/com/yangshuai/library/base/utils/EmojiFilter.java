package com.yangshuai.library.base.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author hcp
 * @Created 10/15/19
 * @Editor hcp
 * @Edited 10/15/19
 * @Type
 * @Layout
 * @Api
 */
public class EmojiFilter implements InputFilter {

    private Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            ToastUtil.Short("不支持输入表情");
            return "";
        }
        return null;
    }
}
