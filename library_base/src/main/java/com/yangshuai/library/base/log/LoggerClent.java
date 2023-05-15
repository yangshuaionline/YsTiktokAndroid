package com.yangshuai.library.base.log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yangshuai.library.base.BuildConfig;
import com.yangshuai.library.base.network.RetrofitManager;

/**
 *<p>
 *
 *     日志框架管理类,在Appaction中初始化
 *    用于打印网络请求日志，项目中日志输入
 *</p>
 * @author hcp
 * @date 2019/03/14
 */
public class LoggerClent {
    private static LoggerClent mInstance;


    public static LoggerClent getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new LoggerClent();
                }
            }
        }
        return mInstance;
    }


    public void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)         // （可选）要显示的方法行数。 默认2
                .methodOffset(5)       // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("ujuz.agent-android")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}
