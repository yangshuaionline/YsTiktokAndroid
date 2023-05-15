package com.yangshuai.library.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author hcp
 * @date 2020/1/18
 */
public class NewHouseSpUtil {
    /**
     * 保存在手机里面的文件名
     */
    private static final String NEW_HOUSE_CITY_INFO = "newHouseSp";
    private static SharedPreferences sp;

    private static synchronized void initSp(Context mContext) {
        if (sp == null) {
            sp = mContext.getSharedPreferences(NEW_HOUSE_CITY_INFO, Context.MODE_PRIVATE);
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法，同步保存
     */
    @SuppressLint("ApplySharedPref")
    public static void put(Context mContext, String key, Object object) {

        initSp(mContext);

        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param mContext 上下文
     * @param key      键
     * @param o        默认值
     * @return 值
     */
    public static Object get(Context mContext, String key, Object o) {

        initSp(mContext);

        if (o instanceof String) {
            return sp.getString(key, (String) o);
        } else if (o instanceof Integer) {
            return sp.getInt(key, (Integer) o);
        } else if (o instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) o);
        } else if (o instanceof Float) {
            return sp.getFloat(key, (Float) o);
        } else if (o instanceof Long) {
            return sp.getLong(key, (Long) o);
        }
        return sp.getString(key, String.valueOf(o));
    }

    /**
     * 获取选择城市id
     */
    public static int getSelectCityCode(Context context) {
        return (int) get(context, NewHouseSpKey.SELECT_CITY_CODE, 0);
    }

    public static void setSelectCityCode(Context context, Object object) {
        put(context, NewHouseSpKey.SELECT_CITY_CODE, object);
    }

    /**
     * 获取学院分享参数
     */
    public static int getCollegeShareParam(Context context) {
        return (int) get(context, NewHouseSpKey.COLLEGE_SHARE_PARAM, 0);
    }

    public static void setCollegeShareParam(Context context, Object object) {
        put(context, NewHouseSpKey.COLLEGE_SHARE_PARAM, object);
    }

    public interface NewHouseSpKey {
        /**
         * 选择城市id
         */
        String SELECT_CITY_CODE = "selectCityCode";

        /**
         * 学院分享参数  0 未加载完毕 1 没有入口 2 可以打开课程详情
         */
        String COLLEGE_SHARE_PARAM = "collegeShareParam";
    }

    /**
     * 清除所有数据
     */
    @SuppressLint("ApplySharedPref")
    public static void clear(Context mContext) {

        initSp(mContext);

        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
