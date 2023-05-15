package com.yangshuai.library.base.utils;

import android.os.Build;

/**
 * 用于判断SDK版本
 */

public class SystemUtils {

    /**
     * ICE_CREAM_SANDWICH_MR1 (Android 4.1) +
     */
    public static boolean v15() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * JELLY_BEAN (Android 4.2) +
     */
    public static boolean v16() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * JELLY_BEAN_MR1
     */
    public static boolean v17() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * KITKAT (Android 4.4) +
     */
    public static boolean v19() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * LOLLIPOP (Android 5.0) +
     * @return
     */
    public static boolean v21() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
    /**
     * LOLLIPOP (Android 5.0) +
     * @return
     */
    public static boolean v22() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * M (Android 6.0) +
     * @return
     */
    public static boolean v23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * N (Android 7.0) +
     * @return
     */
    public static boolean v24_N() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * N (Android 7.1) +
     * @return
     */
    public static boolean v25_N_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }
    /**
     * O (Android 8.1) +
     * @return
     */
    public static boolean v26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * Pie Android 9.0 +
     */
    public static boolean v28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

}
