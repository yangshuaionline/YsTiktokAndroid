package com.yangshuai.library.base.account;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yangshuai.library.base.exception.ExceptionCode;
import com.yangshuai.library.base.router.RouterPath;


/**
 * 帐号管理器，存储Authorization token, userId和用户基本信息
 *
 * @Author hcp
 * @Created 4/24/19
 * @Editor hcp
 * @Edited 4/24/19
 * @Type
 * @Layout
 * @Api
 */
public class AccountManager {
    private static SharedPreferences sharedPreferences;
    private static volatile String TOKEN;//网络token
    private static volatile String USER_ROLE ="User_ROLE";//用户身份

    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_USER_ROLE = "KEY_USER_ROLE";


    private AccountManager() {
    }


    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE);
        TOKEN = loadString(KEY_TOKEN);
        USER_ROLE = loadString(KEY_USER_ROLE);

    }
    /**
     * 获取Token
     *
     * @return
     */
    public static String getToken() {
        return TOKEN;
    }

    /**
     * 保存Token
     *
     * @param token
     */
    public static void saveToken(String token) {
        TOKEN = token;
        saveString(KEY_TOKEN, token);
    }

    /**
     * 保存数据
     *
     * @param data
     */
    private static void saveString(String key, String data) {
        if (key != null && data != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, data);
            editor.apply();
        }
    }


    /**
     * 保存数据
     *
     * @param data
     */
    private static void saveBoolean(String key, boolean data) {
        if (key != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, data);
            editor.apply();
        }
    }





    /**
     * 加载数据
     *
     * @param key
     * @return
     */
    private static String loadString(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     * 加载数据
     *
     * @param key
     * @return
     */
    private static boolean loadBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
    /**
     * 清空数据缓存
     */
    public static void clear() {
        TOKEN = null;
        USER_ROLE=null;
        sharedPreferences.edit().clear().apply();
    }

    /**
     * token失效重新登录
     * 错误码地址 https://note.youdao.com/group/#/93805645/(folder/438562026//full:md/438747900)
     *
     * @param code
     */
    public static boolean handleInvalidToken(String code) {
        if (ExceptionCode.isInvalidToken(code)) {

            AccountManager.clear();
            ARouter.getInstance().build(RouterPath.Signin.ROUTE_SIGNIN_PASSWORD)
                    .greenChannel()
                    .navigation();

            return true;
        }
        return false;
    }

    /**
     * 保存用户角色
     */
    /**
     * 获取Token
     *
     * @return
     */
    public static String getUsedType() {
        return USER_ROLE;
    }

    /**
     * 保存Token
     *
     * @param token
     */
    public static void saveUsedType(String token) {
        USER_ROLE = token;
        saveString(USER_ROLE, token);
    }

}
