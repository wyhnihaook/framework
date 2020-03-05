package com.wyh.modulecommon.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.BaseObservable;

import java.lang.reflect.Field;

/**
 * Created by 翁益亨 on 2020/3/2.
 */
public class BasePojo extends BaseObservable implements BeanCloneable<BasePojo> {

    /**Cursor 转 Pojo**/
    public static <T extends BasePojo> T Mappper(Cursor cursor, Class<T> tClass) {
        T obj = null;
        try {
            obj = tClass.newInstance();
            for (Field fd : FieldCache.getFields(tClass)) {
                String name = fd.getName();
                Class<?> type = fd.getType();
                Object val = null;
                if (type == boolean.class || type == Boolean.class) {
                    int _val = cursor.getInt(cursor.getColumnIndex(name));
                    val = _val == 0 ? false : true;
                } else if (type == int.class || type == Integer.class) {
                    val = cursor.getInt(cursor.getColumnIndex(name));
                } else if (type == long.class || type == Long.class) {
                    val = cursor.getLong(cursor.getColumnIndex(name));
                } else if (type == float.class || type == Float.class) {
                    val = cursor.getDouble(cursor.getColumnIndex(name));
                } else if (type == double.class || type == Double.class) {
                    val = cursor.getDouble(cursor.getColumnIndex(name));
                } else if (type == String.class) {
                    val = cursor.getString(cursor.getColumnIndex(name));
                }
                fd.setAccessible(true);
                fd.set(obj, val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**Pojo 转 ContentValues**/
    public static <T extends BasePojo> ContentValues Marshal(T obj) {
        ContentValues cv = new ContentValues();
        try {
            for (Field fd : FieldCache.getFields(obj.getClass())) {
                fd.setAccessible(true);
                Class<?> type = fd.getType();
                if (type == int.class || type == Integer.class) {
                    cv.put(fd.getName(), fd.getInt(obj));
                } else if (type == boolean.class || type == Boolean.class) {
                    cv.put(fd.getName(), fd.getBoolean(obj));
                } else if (type == long.class || type == Long.class) {
                    cv.put(fd.getName(), fd.getLong(obj));
                } else if (type == float.class || type == Float.class) {
                    cv.put(fd.getName(), fd.getFloat(obj));
                } else if (type == double.class || type == Double.class) {
                    cv.put(fd.getName(), fd.getDouble(obj));
                } else if (type == String.class) {
                    cv.put(fd.getName(), (String) fd.get(obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cv;
    }

    @Override
    public void cloneFromBean(BasePojo src) {
        CloneUtil.cloneObject(src, this);
    }

    public void cloneObjectOrginal(BasePojo src) {
        CloneUtil.cloneObjectOrginal(src, this);
    }
}
