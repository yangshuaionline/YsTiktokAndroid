package com.yangshuai.library.base.log;

import android.util.Log;

import com.yangshuai.library.base.utils.JsonformatJsonUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Author hcp
 * @Created 4/11/19
 * @Editor hcp
 * @Edited 4/11/19
 * @Type
 * @Layout
 * @Api
 */
public class HttpLogger2 implements HttpLoggingInterceptor.Logger {

    private static final String TAG = "HTTP";

    @Override
    public void log(String message) {
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonformatJsonUtils.formatJson(JsonformatJsonUtils.decodeUnicode(message));
            message = "Body:\n" + message;
        }
        Log.d(TAG, message);
    }
}
