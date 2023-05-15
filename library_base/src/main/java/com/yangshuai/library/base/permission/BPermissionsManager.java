package com.yangshuai.library.base.permission;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.databinding.BindingAdapter;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 业务权限管理
 *
 * @Author hcp
 * @Created 7/7/19
 * @Editor hcp
 * @Edited 7/7/19
 * @Type
 * @Layout
 * @Api
 */
public class BPermissionsManager {
    private static SharedPreferences sharedPreferences;
    private static final Set<String> PERMISSIONS = new HashSet<>();

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("permissions", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("permissions", null);
        if (json != null) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> permissions = new Gson().fromJson(json, type);
            if (permissions != null) PERMISSIONS.addAll(permissions);
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
            PERMISSIONS.addAll(permissions);
        }
        String json = new Gson().toJson(PERMISSIONS);
        sharedPreferences.edit().putString("permissions", json).apply();
    }

    /**
     * 重置权限
     */
    public static void resetPermissions() {
        PERMISSIONS.clear();
    }

    /**
     * 查询指定权限是否存在
     *
     * @param permission 权限标识
     * @return
     */
    public static boolean hasPermission(String permission) {
        return PERMISSIONS.contains(permission);
    }
    
    /**
     * 根据权限判断按钮是否可以显示
     *
     * @param permission
     * @return
     */
    @BindingAdapter("permission_visibility")
    public static void visible(View view, String permission) {
        if (BPermissionsManager.hasPermission(permission)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
