package com.yangshuai.library.base.bugly;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;
import com.yangshuai.library.base.BaseConst;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author hcp
 * @Created 7/16/19
 * @Editor hcp
 * @Edited 7/16/19
 * @Type
 * @Layout
 * @Api
 */
public class BuglyHolder {

//    private static final String APP_ID = "4ed77865b1";
//    private static final String APP_KEY = "c7f9cd99-7367-4ea7-9e8a-90c05d2e7e54";

    public static void init(Context context, boolean openLog) {
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setBuglyLogUpload(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, BaseConst.BUGLY_APP_ID, openLog, strategy);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
