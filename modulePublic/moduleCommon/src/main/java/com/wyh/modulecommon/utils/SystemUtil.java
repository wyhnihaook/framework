package com.wyh.modulecommon.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.wyh.modulecommon.base.MainApplication;

import java.io.File;
import java.util.List;

/**
 * Created by js on 2016/6/1.
 */
public class SystemUtil {

    /**获取系统版本号**/
    public static int getVersion() {
        try {
            PackageManager manager = MainApplication.getIns().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MainApplication.getIns().getPackageName(), 0);
            return  info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断App是否在前台运行
     * @return
     */
    public static boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) MainApplication.getIns().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(MainApplication.getIns().getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i("前台", appProcess.processName);
                    return true;
                }else{
                    Log.i("后台", appProcess.processName);
                    return false;
                }
            }
        }
        Log.i("后台", "dmapp");
        return false;
    }

    /**
     * 获取App在SD卡上的缓存目录
     * @return
     */
    public static String getExternalFilesDir(String dirName) {
        File dir = MainApplication.getIns().getExternalFilesDir(dirName);
        if (dir != null && !TextUtils.isEmpty(dir.getAbsolutePath())) {
            return dir.getAbsolutePath();
        }
        dir = Environment.getExternalStorageDirectory();
        if (dir.canWrite()) {
            File cacheDir = new File(dir.getAbsolutePath() + "/yztz", dirName);
            cacheDir.mkdirs();
            return cacheDir.getAbsolutePath();
        }
        File[] list = dir.getParentFile().listFiles();
        for (File file : list) {
            if (file.canWrite()) {
                File cacheDir = new File(file.getAbsolutePath() + "/yztz", dirName);
                cacheDir.mkdirs();
                return cacheDir.getAbsolutePath();
            }
        }
        return "";
    }

    /**
     * 获取相册目录
     * @return
     */
    public static String getExternalDCIMDir() {

        File dir = Environment.getExternalStorageDirectory();
        if (dir.canWrite()) {
            File cacheDir = new File(dir.getAbsolutePath() + "/DCIM");
            return cacheDir.getAbsolutePath();
        }
        File[] list = dir.getParentFile().listFiles();
        for (File file : list) {
            if (file.canWrite()) {
                File cacheDir = new File(file.getAbsolutePath() + "/DCIM");
                return cacheDir.getAbsolutePath();
            }
        }
        return "";
    }

    /**
     * 获得缓存目录
     * @param dirName
     * @return
     */
    public static String getCacheFilesDir(String dirName) {
        File dir = MainApplication.getIns().getCacheDir();
        File cacheDir = new File(dir, dirName);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * 判断是否有网络连接
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) MainApplication.getIns()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        } else {
            return false;
        }
    }

    /**
     * 获取渠道号
     * @return
     */
    public static String getAppChannel() {
        String channel = "fuxin";
        try {
            ApplicationInfo appInfo = MainApplication.getIns().getPackageManager()
                    .getApplicationInfo(MainApplication.getIns().getPackageName(),
                            PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("Channel", channel);
        return channel;
    }

    public static String getAppName() {
        String name = "";
        try {
            ApplicationInfo appInfo = MainApplication.getIns().getPackageManager()
                    .getApplicationInfo(MainApplication.getIns().getPackageName(),
                            PackageManager.GET_META_DATA);
            name = appInfo.metaData.getString("APP_NAME");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("name", name);
        return name;
    }

    /**
     * 获得MetaData
     * @param act
     * @param key
     * @return
     */
    public static String getMetaData(Activity act, String key) {
        String result;
        try {
            ActivityInfo info = act.getPackageManager()
                    .getActivityInfo(act.getComponentName(), PackageManager.GET_META_DATA);
            result = info.metaData.getString(key);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static String getScheme() {
        String scheme = "";
        try {
            ApplicationInfo appInfo = MainApplication.getIns().getPackageManager()
                    .getApplicationInfo(MainApplication.getIns().getPackageName(),
                            PackageManager.GET_META_DATA);
            scheme = appInfo.metaData.getString("APP_SCHEME");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("scheme", scheme);
        return scheme;
    }

    public static String getPackageName() {
        try {
            return MainApplication.getIns().getPackageName();
        } catch (Exception e) {
            return "com.fuxin.stockstrategy";
        }
    }

    public static boolean isBasePackage() {
        return getPackageName().equals("com.fuxin.stockstrategy");
    }

}
