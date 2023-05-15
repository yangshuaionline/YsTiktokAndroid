package com.yangshuai.library.base.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * hcp
 * JsonUtils工具类
 */
public class JsonUtils {

    private static boolean isPrintException = true;//日志开关


    /**
     * Google Gson
     * @param jsonInString
     * @return
     */
    public static boolean isJsonStr(String jsonInString) {
        try {
            if (StringUtils.isEmpty(jsonInString)) return false;
            if (gson == null) gson = new Gson();
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }

    /**
     * get Long from jsonObject
     */
    public static long getLong(JSONObject jsonObject, String key, Long defaultValue) {
        if (jsonObject == null || "".equals(key) || key == null) {
            return defaultValue;
        }
        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Long from jsonData
     */
    public static long getLong(String jsonData, String key, Long defaultValue) {
        if ("".equals(jsonData) || jsonData == null) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Int from jsonObject
     */
    public static int getInt(JSONObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Int from jsonData
     */
    public static int getInt(String jsonData, String key, Integer defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Double from jsonObject
     */
    public static double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }
        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Double from jsonData
     */
    public static double getDouble(String jsonData, String key, Double defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String from jsonObject
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public static String getString(JSONObject jsonObject, String key) {
        return getString(jsonObject, key, "");
    }

    /**
     * get String from jsonData
     */
    public static String getString(String jsonData, String key, String defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String array from jsonObject
     */
    public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                String[] value = new String[statusArray.length()];
                for (int i = 0; i < statusArray.length(); i++) {
                    value[i] = statusArray.getString(i);
                }
                return value;
            }
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * get String array from jsonData
     */
    public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonObject
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        return getJSONObject(jsonObject, key, null);
    }

    /**
     * get JSONObject from jsonData
     */
    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * Convert string data to JSONObject
     */
    public static JSONObject getJSONObject(String jsonData) {
        if (jsonData == null || "".equals(jsonData)) {
            return null;
        }

        JSONObject object = null;
        try {
            object = new JSONObject(jsonData);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * get JSONArray from jsonObject
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonData
     */
    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONArray from a jsonData
     */
    public static JSONArray getJSONArray(String jsonData) {
        if (jsonData == null || "".equals(jsonData)) {
            return null;
        }

        JSONArray array = null;
        try {
            array = new JSONArray(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String arrName) {
        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray(arrName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * json 转换为List
     */
    public static <T> List<T> jsonToList(String str, Class<T> cls) {
        List<T> list = new ArrayList<>();
        if (gson == null) {
            gson = new Gson();
        }
        if (!TextUtils.isEmpty(str)) {
            JsonArray jsonArr = new JsonParser().parse(str).getAsJsonArray();
            for (JsonElement jsonElement : jsonArr) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        }
        return list;
    }

    /**
     * get Boolean from jsonObject
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) {
        if (jsonObject == null || key == null || "".equals(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Boolean from jsonData
     */
    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
        if (jsonData == null || "".equals(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    private static Gson gson;

    /**
     * 根据传入的json和数据类型得到解析结果
     *
     * @param content json数据
     * @param clazz   类型
     * @return Json解析结果
     */
    public static <T> T jsonToBean(String content, Class<T> clazz) {

        T result = null;
        //为空直接返回
        if (content == null || "".equals(content))
            return result;

        Gson gson = new Gson();
        try {
            result = gson.fromJson(content, clazz);
        } catch (JsonSyntaxException e) {
            if (isPrintException) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * 将对象直接转成json字符串
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }
    /**
     * json To MAP(有序)
     * @param content json字符串
     * @return  如果转换失败返回null,
     */
    public static LinkedHashMap<String, Object> jsonToMap(String content) {
        content = content.trim();
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        try {
            if (content.charAt(0) == '[') {
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object value = jsonArray.get(i);
                    if (value instanceof JSONArray || value instanceof JSONObject) {
                        result.put(i + "", jsonToMap(value.toString().trim()));
                    } else {
                        result.put(i + "", jsonArray.getString(i));
                    }
                }
            } else if (content.charAt(0) == '{'){
                JSONObject jsonObject = new JSONObject(content);
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Object value = jsonObject.get(key);
                    if (value instanceof JSONArray || value instanceof JSONObject) {
                        result.put(key, jsonToMap(value.toString().trim()));
                    } else {
                        result.put(key, value.toString().trim());
                    }
                }
            }else {
                KLog.e("异常", "json2Map: 字符串格式错误");
            }
        } catch ( JSONException e) {
            KLog.e("异常", "json2Map: "+ e);
            result = null;
        }
        return result;
    }

}
