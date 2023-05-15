package com.yangshuai.library.base.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 计算工具,解决Java浮点数运算存在偏差问题
 *
 * @Author hcp
 * @Created 2019-04-24
 * @Editor hcp
 * @Edited 2019-04-24
 */
public class MathUtils {
    /**
     * 加
     * @param args
     * @return
     */
    public static BigDecimal add(Object... args){
        final int len = args.length;
        if (args.length == 1){
            return new BigDecimal(String.valueOf(args[0]));
        }
        BigDecimal bd = new BigDecimal(String.valueOf(args[0]));
        for (int i = 1; i < len; i++) {
            bd = bd.add(new BigDecimal(String.valueOf(args[i])));
        }
        return bd;
    }

    /**
     * 减
     * @param args
     * @return
     */
    public static BigDecimal sub(Object... args){
        final int len = args.length;
        if (args.length == 1){
            return new BigDecimal(String.valueOf(args[0]));
        }
        BigDecimal bd = new BigDecimal(String.valueOf(args[0]));
        for (int i = 1; i < len; i++) {
            bd = bd.subtract(new BigDecimal(String.valueOf(args[i])));
        }
        return bd;
    }

    /**
     * 除
     * @param args
     * @return
     */
    public static BigDecimal divide(Object... args){
        final int len = args.length;
        if (args.length == 1){
            return new BigDecimal(String.valueOf(args[0]));
        }
        BigDecimal bd = new BigDecimal(String.valueOf(args[0]));
        for (int i = 1; i < len; i++) {
            bd = bd.divide(new BigDecimal(String.valueOf(args[i])), 5, BigDecimal.ROUND_HALF_UP);
        }
        return bd;
    }


    /**
     * 乘
     * @param args
     * @return
     */
    public static BigDecimal multiply(Object... args){
        final int len = args.length;
        if (args.length == 1){
            return new BigDecimal(String.valueOf(args[0]));
        }
        BigDecimal bd = new BigDecimal(String.valueOf(args[0]));
        for (int i = 1; i < len; i++) {
            bd = bd.multiply(new BigDecimal(String.valueOf(args[i])));
        }
        return bd;
    }

    /**
     * 清除".0"结尾
     * @param arg
     * @return
     */
    public static String clearEndPoint(Object arg){
        String str = String.valueOf(arg);
        if (str.endsWith(".0")){
            str = str.replace(".0", "");
        }
        if (str.endsWith(".00")){
            str = str.replace(".00", "");
        }
        return str;
    }

    /**
     * 消除科学计数法与".0"结尾,四舍五入
     */
    public static String formatNumbers(String number) {
        if (TextUtils.isEmpty(number)) {
            return "0";
        }

        BigDecimal bd = new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP);

        return clearEndPoint(bd);
    }
}
