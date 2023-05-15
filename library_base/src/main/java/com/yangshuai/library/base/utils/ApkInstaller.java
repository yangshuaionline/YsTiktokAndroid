package com.yangshuai.library.base.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.yangshuai.library.base.BaseConst;
import com.yangshuai.library.base.dialog.AlertDialog;

import java.io.File;

/**
 * @Author hcp
 * @Created 5/31/19
 * @Editor hcp
 * @Edited 5/31/19
 * @Type
 * @Layout
 * @Api
 */
public class ApkInstaller {

    public static String getDownloadDir() {
//        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/UJUZ";
        return Utils.sdPath() + BaseConst.APP_FOLOER_APK;
    }

    public static String getApkName(String version){
        return "erp.yjyz." + version + ".apk";
    }

    public static void install(Context context, File file) {

        // 判断是否开启未知应用安装权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!context.getPackageManager().canRequestPackageInstalls()) {
                startInstallPermissionSettingActivity(context, file);
                return;
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(context, "com.zwy.module", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context, File file) {

        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("请到设置中开启安装未知应用的权限,然后重试");
        alertDialog.setLeftButton("重试", v -> {
            install(context, file);
            alertDialog.dismiss();
        });
        alertDialog.setRightButton("去设置", v -> {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
            context.startActivity(intent);
        });
        alertDialog.show();

    }
}
