package com.yangshuai.library.base.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 限制输入整数和小数位数过滤器
 * @author hcp
 * @created 2019-11-27
 */
public class DecimalAndIntegerFilter implements InputFilter {

    private Pattern mPattern;

    /**
     * 限制输入框可输入的整数和小数位数 (例如：房源价格只能输入6个整数，2个小数)
     *
     * @param integerCount  整数数量
     * @param decimalsCount 小数数量
     */
    public DecimalAndIntegerFilter(int integerCount, int decimalsCount) {
        String pattern = "[0-9]{0," + integerCount + "}+((\\.[0-9]{0," + decimalsCount + "})?)||(\\.)?";//小数
        mPattern = Pattern.compile(pattern);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String formatedSource = source.subSequence(start, end).toString();
        String destPrefix = dest.subSequence(0, dstart).toString();
        String destSuffix = dest.subSequence(dend, dest.length()).toString();
        // 算出变化后的值:
        String result = destPrefix + formatedSource + destSuffix;
        Matcher matcher = mPattern.matcher(result);
        if (!matcher.matches()) {
            String old = dest.subSequence(dstart, dend).toString();
            return old;//过滤,返回被替换前的原始值
        }
        return null;//不过滤
    }
}
