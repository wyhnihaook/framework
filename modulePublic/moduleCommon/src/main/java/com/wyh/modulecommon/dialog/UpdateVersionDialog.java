package com.wyh.modulecommon.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyh.modulecommon.R;
import com.wyh.modulecommon.helper.updatehelper.ApkUpdateControl;

/**
 * Created by 翁益亨 on 2020/1/20.
 */
public class UpdateVersionDialog extends Dialog implements  View.OnClickListener {

    String version;

    String versionContent;

    Boolean isHasUpdate;

    String downUrl;//点击跳转浏览器打开相应网址

    Context context;

    private ImageView iv_dismiss;

    private TextView tv_version,tv_des,tv_confirm;

    //最后一个参数判断是否需要强制更新
    public UpdateVersionDialog(Context context, String version, String versionContent,boolean isHasUpdate,String downUrl) {
        super(context, R.style.module_public_DialogStyle);
        this.context=context;
        this.version = version;
        this.versionContent = versionContent;
        this.isHasUpdate=isHasUpdate;
        this.downUrl=downUrl;
        View view = LayoutInflater.from(context).inflate(R.layout.module_public_dialog_update_version, null);
        setContentView(view);
        // 设置窗口大小
        Window mWindow = getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();

        mWindow.setAttributes(lp);
        // 位置设置在底部
        mWindow.setGravity(Gravity.CENTER);
        // 设置可取消
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();

    }

    private void initView() {

        iv_dismiss=findViewById(R.id.iv_dismiss);
        iv_dismiss.setOnClickListener(this);

        if(isHasUpdate){
            //不是强制更新
            iv_dismiss.setVisibility(View.VISIBLE);
        }else{
            iv_dismiss.setVisibility(View.GONE);
        }


        tv_version=findViewById(R.id.tv_version);
        tv_version.setText(version);

        tv_des=findViewById(R.id.tv_des);
        tv_des.setText(versionContent);


        tv_confirm=findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id= v.getId();
        if(id==R.id.iv_dismiss){
            cancel();
        }else if(id==R.id.tv_confirm){
            //跳转外部链接
//                Uri uri = Uri.parse(downUrl);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                context.startActivity(intent);

            this.dismiss();
            //下载功能,内部阿里云地址下载
            ApkUpdateControl apkUpdateControl=new ApkUpdateControl();
            apkUpdateControl.checkAppUpdate((Activity) context);
        }
    }

}