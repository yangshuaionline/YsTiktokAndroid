package com.yangshuai.library.base.network.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import me.jessyan.autosize.utils.LogUtils;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义Gson转换器
 *
 * @author hcp
 * @date 2019/03/14
 */
final class CollegeGsonResponseBodyConverter
        <T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CollegeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        jsonString = jsonString.replace(",\"data\":\"\"", "");
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 错误码
            String code = null;
            String msg = null;
            boolean success;

            try {
                success = jsonObject.getBoolean("result");
                if (success) {
                    code = "200";
                } else {
                    code = jsonObject.getString("code");
                }
                msg = jsonObject.getString("message");
            } catch (JSONException ignored) {

            }

            // 如果响应码不为200则表示此次请求是失败的，
            // 需要将data统一转为null(因为错误时并不会用到这个字段)，避免外部定义的数据类型与当前返回的数据类型冲突，
            // 比如定义成功时数据返回Object，而错误时后台却返回String（写Api的程序员不太专业）导致解析出错
            if (code == null) {
                jsonObject.put("code", "500");
            } else if (TextUtils.equals(code, "200")) {
                jsonObject.put("code", code);
            } else {
                jsonObject.put("data", null);
            }
            // 如果msg为空，为了前段展示不报错需要重新赋值
            if (msg == null) {
                jsonObject.put("msg", "服务器错误");
            } else {
                jsonObject.put("msg", msg);
            }
            JsonReader jsonReader = gson.newJsonReader(new StringReader(jsonObject.toString()));
            return adapter.read(jsonReader);
        } catch (JSONException e) {
            LogUtils.d("学院测试：错误：" + e.toString());
            e.printStackTrace();
            JsonReader jsonReader = gson.newJsonReader(new StringReader(jsonString));
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }

    }
}
