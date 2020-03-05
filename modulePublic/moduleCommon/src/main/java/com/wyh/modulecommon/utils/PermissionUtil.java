package com.wyh.modulecommon.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.wyh.modulecommon.EventCenter;
import com.wyh.modulecommon.R;
import com.wyh.modulecommon.constant.Constants;

import de.greenrobot.event.EventBus;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 翁益亨 on 2020/1/19.
 * 动态获取权限类库
 */
public class PermissionUtil {
    /**
     * 获取存储权限
     * */

    public static  String[] permsWrite = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static void requestWrite(final Context context) {

        if (EasyPermissions.hasPermissions(context,permsWrite)) {
            EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_WRITE_REQUESTCODE));
        } else {
            // request for one permission，第三个参数时提示用户去打开权限
            //第一次是系统自动授权，第二次判断的时候就会弹出设置好的提示框提示用户去设置
            //跳转到设置页面

            EasyPermissions.requestPermissions((Activity) context, "请先在设置中开启存储权限，避免应用功能无法使用",
                    R.string.module_public_confirm,R.string.module_public_empty,
                    Constants.WRITE_REQUESTCODE,permsWrite);
        }

    }

    //跳转到设置页面，让用户自己开启授权
    public static void toSelfSetting(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }
}
