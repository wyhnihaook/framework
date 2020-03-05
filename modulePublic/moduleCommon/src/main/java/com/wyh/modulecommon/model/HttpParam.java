package com.wyh.modulecommon.model;

import java.util.HashMap;

/**
 * Created by 翁益亨 on 2020/1/19.
 * 用于Okhttp的参数传输
 */
public class HttpParam extends HashMap<String, Object> {

    @Override
    public HttpParam put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}