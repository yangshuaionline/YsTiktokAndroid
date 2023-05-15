package com.yangshuai.library.base.utils;

/**
 * 判断当前点击事件是否是连续点击
 *
 * @author hcp
 * @create 2019-6-11
 * @Describe
 */
public class QuickClickUtils {
    private static long oldTime;

    /**
     * @return 快速点击
     */
    public static boolean isQuickClick() {
        return isQuickClick(500);
    }

    /**
     * @param time
     * @return 快速点击
     */
    public static boolean isQuickClick(int time) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - oldTime > time) {
            oldTime = currentTime;
            return false;
        } else {
            return true;
        }
    }

}
