package com.yangshuai.library.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * @author  hcp
 * @date 2019/3/20
 */

public class PreferencesUtils {

    private final static String NAME = "cookie";

    public static SharedPreferences getDefaultSharedPreferences(){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 存储String
     * @param key
     * @param value
     */
    public static void set(String key, String value){
        getDefaultSharedPreferences().edit().putString(key, value).commit();
    }

    /**
     * 获取String
     * @param key
     */
    public static String get(String key, String defValue){
        return getDefaultSharedPreferences().getString(key, defValue);
    }

    /**
     * 存储Int
     * @param key
     * @param value
     */
    public static void setInt(String key, int value){
        getDefaultSharedPreferences().edit().putInt(key, value).commit();
    }

    /**
     * 获取Int
     * @param key
     */
    public static int getInt(String key, int defValue){
        return getDefaultSharedPreferences().getInt(key, defValue);
    }

    /**
     * 存储double
     * @param key
     * @param value
     */
    public static void set(String key, double value){
        getDefaultSharedPreferences().edit().putFloat(key, (float)value).commit();
    }

    /**
     * 获取double
     * @param key
     */
    public static double get(String key, double defValue){
        return getDefaultSharedPreferences().getFloat(key, (float)defValue);
    }

    /**
     * 存储boolean
     * @param key
     * @param value
     */
    public static void set(String key, boolean value){
        getDefaultSharedPreferences().edit().putBoolean(key, value).commit();
    }
    /**
     * 获取boolean
     * @param key
     */
    public static boolean get(String key, boolean defValue){
        return getDefaultSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 存储boolean
     * @param key
     * @param value
     */
    public static void set(String key, long value){
        getDefaultSharedPreferences().edit().putLong(key, value).commit();
    }
    /**
     * 获取boolean
     * @param key
     */
    public static long get(String key, long defValue){
        return getDefaultSharedPreferences().getLong(key, defValue);
    }

    /**
     * 清楚key对应的值
     * @param key
     */
    public static  void removeArr(String key){
        getDefaultSharedPreferences().edit().remove(key).commit();
    }

    public static void removeAllArr(){
//        KLog.i("test","清除数据");
        getDefaultSharedPreferences().edit().clear().commit();

    }
}
