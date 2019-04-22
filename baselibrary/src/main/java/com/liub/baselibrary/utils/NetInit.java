package com.liub.baselibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public class NetInit {
    private static volatile NetInit netInit;
    private Gson gson;
    private Gson noteGson;

    private NetInit() {
        gson = new Gson();
        initNote();
    }

    protected static NetInit getInstance() {
        if (netInit == null) {
            synchronized (NetInit.class) {
                if (netInit == null)
                    netInit = new NetInit();
            }
        }
        return netInit;
    }

    private void initNote() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                .serializeNulls().setDateFormat(JsonUtils.DEFAULT_DATE_PATTERN)//时间转化为特定格式
                .setPrettyPrinting() //对json结果格式化.
                .setVersion(JsonUtils.SINCE_VERSION_10);   //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
        //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
        //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
        noteGson = gsonBuilder.create();
    }

    public Gson getGson() {
        return gson;
    }

    public Gson getNoteGson() {
        return noteGson;
    }
}
