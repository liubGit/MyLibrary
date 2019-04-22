package com.liub.baselibrary.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public class JsonUtils {

    /**
     * 默认的 {@code JSON} 日期/时间字段的格式化模式。
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * {@code Google Gson} 的注解常用的版本号常量 - {@code 1.0}。
     */
    public static final Double SINCE_VERSION_10 = 1.0d;
    /**
     * {@code Google Gson} 的 注解常用的版本号常量 - {@code 1.1}。
     */
    public static final Double SINCE_VERSION_11 = 1.1d;
    /**
     * {@code Google Gson} 的注解常用的版本号常量 - {@code 1.2}。
     */
    public static final Double SINCE_VERSION_12 = 1.2d;

    public static String toJson(Object obj) {
        Gson gson = NetInit.getInstance().getGson();
        return gson.toJson(obj);
    }

    public static <T> T jsonResolve(String strJson, Class<T> cls) {
        try {
            Gson gson = NetInit.getInstance().getGson();
            return gson.fromJson(strJson, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> T jsonResolve(String strJson, TypeToken<T> token) {
        try {
            Gson gson = NetInit.getInstance().getGson();
            return gson.fromJson(strJson, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> T jsonResolve(String strJson, Type type) {
        try {
            Gson gson = NetInit.getInstance().getGson();
            return gson.fromJson(strJson, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String toJsonForNote(Object objects) {
        Gson gson = NetInit.getInstance().getNoteGson();
        return gson.toJson(objects);
    }

    public static <T> T jsonResolveForNote(String strJson, Class<T> cls) {
        try {
            Gson gson = NetInit.getInstance().getNoteGson();
            return gson.fromJson(strJson, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> T jsonResolveForNote(String strJson, TypeToken<T> token) {
        try {
            Gson gson = NetInit.getInstance().getNoteGson();
            return gson.fromJson(strJson, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * json转map
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> keys = jsonObject.keys();
            Map<String, Object> valueMap = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);
                valueMap.put(keys.next(), value);
            }
            return valueMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
