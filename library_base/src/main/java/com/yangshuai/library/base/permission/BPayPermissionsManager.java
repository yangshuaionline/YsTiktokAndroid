package com.yangshuai.library.base.permission;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yangshuai.library.base.dialog.AlertDialog;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 支付权限管理
 *
 * @Author hcp
 * @Created 7/7/19
 * @Editor hcp
 * @Edited 7/7/19
 * @Type
 * @Layout
 * @Api
 */
public class BPayPermissionsManager {
    private static SharedPreferences paySharedPreferences;
    private static final Set<String> PAYPERMISSIONS = new HashSet<>();

    public static void init(Context context) {
        paySharedPreferences = context.getSharedPreferences("payPermissions", Context.MODE_PRIVATE);
        String json = paySharedPreferences.getString("payPermissions", null);
        if (json != null) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> permissions = new Gson().fromJson(json, type);
            if (permissions != null) PAYPERMISSIONS.addAll(permissions);
        }
    }


    /**
     * 设置权限
     *
     * @param permissions
     */
    public static void setPermissions(List<String> permissions) {
        resetPermissions();
        if (permissions != null && permissions.size() > 0) {
            PAYPERMISSIONS.addAll(permissions);
        }
        String json = new Gson().toJson(PAYPERMISSIONS);
        paySharedPreferences.edit().putString("payPermissions", json).apply();
    }

    /**
     * 重置权限
     */
    public static void resetPermissions() {
        PAYPERMISSIONS.clear();
    }

    /**
     * 查询指定权限是否存在
     *
     * @param permission 权限标识
     * @return
     */
    public static boolean hasPayPermission(String permission) {
        return PAYPERMISSIONS.contains(permission);
    }

    /**
     * 显示无权限弹窗
     */
    public static void showNotPermissionDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setTitle("提示");
        alertDialog.setMessage("此功能为付费产品，请联系公司管理员");
        alertDialog.setMessageGravity(Gravity.LEFT);
        alertDialog.setMessagePaddingTop(20);
        alertDialog.setCancelable(false);
        alertDialog.setRightButton("知道了", v1 -> alertDialog.hide());
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }
}
