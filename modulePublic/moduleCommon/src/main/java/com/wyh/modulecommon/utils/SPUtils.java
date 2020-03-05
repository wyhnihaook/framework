package com.wyh.modulecommon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.wyh.modulecommon.base.MainApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils
{
    /**
     * 保存在手机里面的文件名 
     */
    public static final String FILE_NAME = "NativeStorage";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        put(MainApplication.getIns(), key, object);
    }

    private static void put(Context context, String key, Object object)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值 
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    private static Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Double)
        {
            String val = sp.getString(key, String.valueOf(defaultObject));
            return Double.parseDouble(val);
        } else {
            return sp.getString(key, (String) defaultObject);
        }
    }

    /**
     * sp中读取string字段
     * @param key
     * @return
     */
    public static String getString(String key) {
        String ret = (String) get(MainApplication.getIns(), key ,null);
        return ret;
    }

    /**
     * sp中读取string字段
     * @param key
     * @param def
     * @return
     */
    public static String getString(String key, String def) {
        String ret = (String) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * sp中读取int字段
     * @param key
     * @return
     */
    public static int getInt(String key) {
        Integer def = 0;
        Integer ret = (Integer) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * sp中读取int字段
     * @param key
     * @return
     */
    public static int getInt(String key, int def) {
        return (Integer) get(MainApplication.getIns(), key ,def);
    }

    /**
     * sp中读取bool字段
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        Boolean def = false;
        Boolean ret = (Boolean) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * sp中读取bool字段
     * @param key
     * @return
     */
    public static boolean getBoolean(String key, boolean def) {
        Boolean ret = (Boolean) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * sp中读取float字段
     * @param key
     * @return
     */
    public static float getFloat(String key) {
        Float def = 0f;
        Float ret = (Float) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * sp中读取long字段
     * @param key
     * @return
     */
    public static long getLong(String key) {
        Long def = 0l;
        Long ret = (Long) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * sp中读取double字段
     * @param key
     * @return
     */
    public static double getDouble(String key) {
        Double def = 0d;
        Double ret = (Double) get(MainApplication.getIns(), key ,def);
        return ret;
    }

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getString("token"));
    }

    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public static void remove(String key) {
        remove(MainApplication.getIns(), key);
    }

    /**
     * 移除某个key值已经对应的值 
     * @param context
     * @param key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据 
     * @param context
     */
    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 是否包含key
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return contains(MainApplication.getIns(), key);
    }

    /**
     * 查询某个key是否已经存在 
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对 
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类 
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法 
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit 
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }

}  