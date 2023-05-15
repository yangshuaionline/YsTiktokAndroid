package com.yangshuai.library.base.log;

import com.orhanobut.logger.Logger;
import com.yangshuai.library.base.utils.JsonformatJsonUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 *
 * @author  hcp
 * @date 2019/3/15
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {

    private StringBuilder mMessage = new StringBuilder();



    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonformatJsonUtils.formatJson(JsonformatJsonUtils.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Logger.d(mMessage.toString());
        }
    }
}
