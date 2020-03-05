package com.wyh.modulecommon.utils;

import android.os.Looper;
import android.widget.Toast;

import com.wyh.modulecommon.base.MainApplication;


/**
 * Created by js on 2016/5/18.
 */
public class ToastUtil {

    private static String lastMsg;
    private static long lastToastTime = 0L;

    /**
     * 显示Toast消息,短时间
     * @param msg
     */
    public static void showShortToast(final String msg) {
        if (msg == null || msg.equals("请先登录") || !SystemUtil.isRunningForeground()) return;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToastTime < 4000 && lastMsg.equals(msg)) {//3秒内不连续弹出Toast
            return;
        }
        lastToastTime = currentTime;
        lastMsg = msg;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(MainApplication.getIns(), msg, Toast.LENGTH_SHORT).show();
        } else {
            MainApplication.getIns().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainApplication.getIns(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 显示Toast消息,长时间
     * @param msg
     */
    public static void showLongToast(final String msg) {
        //|| msg.equals("请先登录")
        if (msg == null || !SystemUtil.isRunningForeground()) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(MainApplication.getIns(), msg, Toast.LENGTH_LONG).show();
        } else {
            MainApplication.getIns().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainApplication.getIns(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
