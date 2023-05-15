package com.yangshuai.library.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import androidx.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 获取设备唯一标识
 *
 * @Author hcp
 * @Created 9/29/19
 * @Editor hcp
 * @Edited 9/29/19
 * @Type
 * @Layout
 * @Api
 */
public class DeviceUtils {

    private final static String DEVICE_INFO = "device";
    private final static String DEVICE_ID = "device_id";
    private final static String DEVICE_ID_FINE_NAME = ".device_id";
    private final static String EMPTY_ID = "";

    private DeviceUtils() {
    }

    /**
     * 获取设备id
     *
     * @return
     */
    @RequiresPermission(allOf = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static String getDeviceId(Context context) {

        // 先从sp查找id
        String deviceId = getDeviceIdFromSP(context);
        if (StringUtils.isEmpty(deviceId)) {
            // 找不到则从sd卡查找
            deviceId = getDeviceIdFromDisk();
            if (StringUtils.isEmpty(deviceId)) {
                // 还找不到则从手机设备读取
                deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                if (StringUtils.isEmpty(deviceId)) {
                    deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    // 读取不到则生成一个随机id
                    // 有些厂商会默认设置为"9774d56d682e549c"
                    if (StringUtils.isEmpty(deviceId) || "9774d56d682e549c".equals(deviceId)) {
                        deviceId = UUID.randomUUID().toString();
                    } else {
                        saveDeviceIdToSP(context, deviceId);
                        saveDeviceIdToDisk(deviceId);
                    }
                } else {
                    saveDeviceIdToSP(context, deviceId);
                    saveDeviceIdToDisk(deviceId);
                }
            } else {
                saveDeviceIdToSP(context, deviceId);
            }
        }

        return deviceId;
    }


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(DEVICE_INFO, Context.MODE_PRIVATE);
    }

    /**
     * 保存到SharedPreferences
     *
     * @param context
     * @param deviceId
     */
    @SuppressLint("ApplySharedPref")
    private static void saveDeviceIdToSP(Context context, String deviceId) {
        getSharedPreferences(context).edit().putString(DEVICE_ID, deviceId).commit();
    }

    /**
     * 从SharedPreferences中获取
     *
     * @param context
     * @return
     */
    private static String getDeviceIdFromSP(Context context) {
        return getSharedPreferences(context).getString(DEVICE_ID, EMPTY_ID);
    }

    /**
     * 保存到sd卡
     *
     * @param deviceId
     */
    private static void saveDeviceIdToDisk(String deviceId) {
        File file = new File(Environment.getExternalStorageDirectory(), DEVICE_ID_FINE_NAME);
        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            outputStreamWriter.write(deviceId);
            outputStreamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 从sd卡获取
     *
     * @return
     */
    private static String getDeviceIdFromDisk() {
        File file = new File(Environment.getExternalStorageDirectory(), DEVICE_ID_FINE_NAME);
        if (!file.exists()) {
            return EMPTY_ID;
        }
        BufferedReader bufferedReader = null;
        String deviceId = EMPTY_ID;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            deviceId = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return deviceId;
    }

}
