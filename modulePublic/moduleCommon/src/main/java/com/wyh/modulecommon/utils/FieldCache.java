package com.wyh.modulecommon.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by js on 2016/5/21.
 */
public class FieldCache {

    private static Map<String, Field[]> cache = new HashMap<>();//缓存字段

    public static Field[] getFields(Class<?> clazz) {
        String clazzName = clazz.getName();
        if(cache.containsKey(clazzName)){
            return cache.get(clazzName);
        }
        synchronized (cache) {
            if(!cache.containsKey(clazzName)) {
                cache.put(clazzName, clazz.getDeclaredFields());
            }
        }
        return cache.get(clazzName);
    }

}
