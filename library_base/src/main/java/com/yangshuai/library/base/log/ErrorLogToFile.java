package com.yangshuai.library.base.log;

import android.os.Build;
import android.os.Environment;

import com.yangshuai.library.base.BaseConst;
import com.yangshuai.library.base.appinfo.AppInfoManager;
import com.yangshuai.library.base.application.BaseApplication;
import com.yangshuai.library.base.utils.AppVersionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存储写入Err日志,方便出错查原因
 * Time:$[DATE]
 * Author:ThinkPad
 */
public class ErrorLogToFile {

    private static boolean isEnterHead = true; // 标志是否为开始

    /**
     * 写入Err日志,方便出错查原因
     *
     * @param information
     * @Description:
     * @see:
     * @since:
     * @author: CuiWei
     * @date:2020-06-11
     */
    public static void writeErrInformationToSdcard(final String information) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String inform = "";
                    inform = information;
                    if (information != null) {
                        inform = information.substring(0, information.length() < 1000 ? information.length() : 1000);
                    }
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
                            try {
                                Date date = new Date(System.currentTimeMillis());
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String time = formatter.format(date) + "  ";
                                byte[] times = time.getBytes("utf-8");
                                byte[] bytes = inform.getBytes("utf-8");
                                byte[] marking = "<br/>".getBytes("utf-8");
                                File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + BaseConst.APP_FOLDER);
                                if (!filePath.exists()) {
                                    filePath.mkdirs();
                                }
                                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + BaseConst.APP_FOLDER+ "/ErrBLogInfo.html";
                                File file = new File(path);
                                if (file != null && file.exists() && file.length() >= 64 * 1024) {
                                    file.delete();
                                    file = new File(path);
                                    isEnterHead = true;
                                }
                                FileOutputStream out = new FileOutputStream(file, true);
                                if (isEnterHead) {// 加入meta标签用于显示使用utf-8码表解码
                                    out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />".getBytes("utf-8"));
                                    out.write(marking);
                                    out.write(marking);
                                    out.write(marking);//AppVersionUtil.getVersion(UApplication)
                                    out.write(("<font color='green' >" + "app v：" + AppVersionUtil.getVersion(BaseApplication.getCurrentContext())
                                            + " System OS: " + AppInfoManager.getSystemVersion() + "</font> ").getBytes("utf-8"));// 加入版本号
                                    // 和手机机型信息
                                    out.write(marking);
                                    out.write(("<font color='green' > Model : " + Build.MODEL + "</font> ").getBytes("utf-8"));
                                    out.write(marking);
//                        List<Account> accounts = C35AccountManager.getInstance().getAccountsFromSP();
//                        if (accounts != null && !accounts.isEmpty()) {
//                            for (Account account : accounts) {
//                                out.write((account.getEmail() + ",   ").getBytes("utf-8"));
//                            }
//                        }
                                    isEnterHead = false;
                                }
                                out.write(marking);
                                out.write(times);
                                out.write(bytes);
                                out.write(marking);
                                out.flush();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 写入Err日志,方便出错查原因
     *
     * @param throwable
     * @Description:
     * @see:
     * @since:
     * @author: CuiWei
     * @date:2020-06-11
     */
    public static void writeErrInformationToSdcard(Throwable throwable) {

        try {
            // 打印堆栈全部信息用 by cuiwei
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw, true));
            new PrintWriter(sw, true);
            String str = sw.toString();
            writeErrInformationToSdcard(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
