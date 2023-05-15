package com.yangshuai.library.base.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("") || "　".equals(str);
    }


    /**
     * @param phone
     * @return
     */
    public static String maskPhone(String phone) {
        if (StringUtils.isEmpty(phone)) return "";
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 显示高亮字符串
     *
     * @param color   颜色
     * @param text    文字
     * @param keyword 关键字
     * @return 包含高亮关键字字符串
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        if (TextUtils.isEmpty(text)) {
            return s;
        }
        String pattern = Pattern.quote(keyword);
        if (TextUtils.isEmpty(pattern)) {
            return s;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * 统计汉字字数
     *
     * @param text
     * @return
     */
    public static int getChineseCount(String text) {
        if (isEmpty(text)) return 0;
        String Reg = "^[\u4e00-\u9fa5]{1}$";//正则
        int result = 0;
        for (int i = 0; i < text.length(); i++) {
            String b = Character.toString(text.charAt(i));
            if (b.matches(Reg)) result++;
        }
        return result;
    }

    /**
     * 把字符串内容替换成指定字符串
     *
     * @param contentStr
     * @param fromStr
     * @param toStr
     * @return
     */
    public static String getReplaceAllStr(String contentStr, String fromStr, String toStr) {
        return Pattern.compile(fromStr).matcher(contentStr).replaceAll(toStr);
    }


    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    public static boolean copyStr(Context context, String copyStr) {
        try {
            if (StringUtils.isEmpty(copyStr)) return false;
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 不以科学计数法显示，并把结果用逗号隔开保留两位小数
     *
     * @param doubleStr
     * @return
     */
    public static String getDoubleFormat(Double doubleStr) {
        if (doubleStr != null) {
            //不以科学计数法显示，并把结果用逗号隔开保留两位小数
            DecimalFormat format = new DecimalFormat("#,##0.00");
            String str = format.format(doubleStr);
            if (str.endsWith(".00")) {
                str = str.replace(".00", "");
            }
            return str;
        }
        return "0";
    }

    /**
     * 不以科学计数法显示，正常显示保留两位小数
     *
     * @param doubleStr
     * @return
     */
    public static String getDoubleDecimal(Float doubleStr) {
        return getDoubleDecimal(Double.valueOf(doubleStr));
    }


    /**
     * 不以科学计数法显示，正常显示保留两位小数
     *
     * @param doubleStr
     * @return
     */
    public static String getDoubleDecimal(Double doubleStr) {
        if (doubleStr != null) {
            BigDecimal bigDecimal = new BigDecimal(doubleStr);//不以科学计数法显示，正常显示保留两位小数
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        }
        return "";
    }


    /**
     * 真为含有表情
     *
     * @param str
     * @return
     */
    public static boolean isContainsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }


    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            if (c < 256)//ASC11表中的字符码值不够4位,补00
            {
                unicode.append("\\u00");
            } else {
                unicode.append("\\u");
            }
            // 转换为unicode
            unicode.append(Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * 正则表达式判断字符串是否仅含有数字和字母
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }


    /**
     * 统计一个字符串中某个字符串出现的个数
     * @param str
     * @param s
     */
    public static int countString(String str, String s) {
        try {
            int count = 0, len = str.length();
            while (str.indexOf(s) != -1) {
                str = str.substring(str.indexOf(s) + 1, str.length());
                count++;
            }
            return count;
        } catch (Exception e) {
            return -1;
        }
    }
}
