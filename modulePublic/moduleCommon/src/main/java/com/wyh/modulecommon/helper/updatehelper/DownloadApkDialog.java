package com.wyh.modulecommon.helper.updatehelper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.wyh.modulecommon.R;

/**
 * Created by 翁益亨 on 2020/1/19.
 * 升级弹窗
 */
public class DownloadApkDialog extends Dialog {


    private Context context;


    public DownloadApkDialog(Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.module_public_dialog_download_apk);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置弹窗对于窗口的状态以及动画效果
        Window mWindow = getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindow.setAttributes(lp);
        // 设置可以动画
        mWindow.setWindowAnimations(R.style.module_public_dialogAnimBottomAndFade);
        // 位置设置在底部
        mWindow.setGravity(Gravity.CENTER);

        initView();
    }

    ProgressBar update_progress;

    private void initView() {
        update_progress=findViewById(R.id.update_progress);
    }


    public ProgressBar getUpdate_progress() {
        return update_progress;
    }
}
