package com.wyh.modulecommon.helper.updatehelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.wyh.modulecommon.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 翁益亨 on 2019/7/9.
 */
public class ApkUpdateControl {

    private Context mContext;
    //下载弹窗
    DownloadApkDialog downloadApkDialog;
    // 进度值
    private int progress;
    // 下载线程
    private Thread downLoadThread;
    // 终止标记
    private boolean interceptFlag;
    //下载重试次数
    private int retries = 0;
    // 返回的安装包url
    private String apkUrl = "https://heyingbao.oss-cn-hangzhou.aliyuncs.com/app/android/heyingbao.apk";//apk下载地址
    // apk保存完整路径
    private String apkFilePath = getFilePath();
    // 临时下载文件路径
    private String tmpFilePath = getFilePath();

    private static final int MAX_RETRIES = 3;//循环下载次数
    private static final int DOWN_ERROR = -1;//下载失败
    private static final int DOWN_UPDATE = 1;//进度更新
    private static final int DOWN_OVER = 2;//安装通知

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWN_ERROR: {
                    //下载失败，请检查网络
                    ToastUtil.showShortToast("下载失败，请检查网络");
                    break;
                }
                case DOWN_UPDATE: {
                    if(downloadApkDialog!=null){
                        downloadApkDialog.getUpdate_progress().setProgress(progress);
                    }
                    break;
                }
                case DOWN_OVER: {
                   //下载完毕，关闭弹窗，跳转到安装页面
                    if(downloadApkDialog!=null){
                        downloadApkDialog.dismiss();
                        downloadApkDialog=null;
                    }

                    String fileName = getFilePath();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //7.0以上版本
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        uri = FileProvider.getUriForFile(mContext, "com.fuxin.hyb.fileProvider", new File(fileName));
                        Intent install = new Intent(Intent.ACTION_VIEW);
                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        uri = Uri.fromFile(new File(fileName));
                    }
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    mContext.startActivity(intent); //跳转至安装界面

                    //如果不加，最后不会提示完成、打开。
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                }
            }
        }
    };


    /**
     * 初始化弹窗
     * */
    public void checkAppUpdate(Activity context) {
        this.mContext = context;

        downloadApkDialog=new DownloadApkDialog(context);
        downloadApkDialog.show();

        downloadApk();//开始下载apk
    }


    /**
     * 下载apk线程
     */
    private Runnable downApkRunnable = new Runnable() {

        @Override
        public void run() {
            InputStream in = null;
            FileOutputStream out = null;

            try {
                File ApkFile = new File(apkFilePath);

                // 输出临时下载文件
                File tmpFile = new File(tmpFilePath);
                out = new FileOutputStream(tmpFile);

                while (!interceptFlag) {
                    if (retries > MAX_RETRIES) {
                        interceptFlag = true;
                        mHandler.sendEmptyMessage(DOWN_ERROR);
                        break;
                    }

                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int code = conn.getResponseCode();
                    String location = conn.getURL().toString();

                    if (code == 200 && apkUrl.equals(location)) {
                        int length = conn.getContentLength();
                        in = conn.getInputStream();

                        int count = 0;
                        byte buf[] = new byte[1024];

                        do {
                            int numread = in.read(buf);
                            count += numread;
                            // 当前进度值
                            progress = (int) (((float) count / length) * 100);
                            // 更新进度
                            mHandler.sendEmptyMessage(DOWN_UPDATE);
                            if (numread <= 0) {
                                // 下载完成 - 将临时下载文件转成APK文件
                                if (tmpFile.renameTo(ApkFile)) {
                                    // 通知安装
                                    mHandler.sendEmptyMessage(DOWN_OVER);
                                }
                                interceptFlag = true;
                                break;
                            }
                            out.write(buf, 0, numread);
                        } while (!interceptFlag);// 点击取消就停止下载
                    } else {
                        retries++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(DOWN_ERROR);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(DOWN_ERROR);
                }
            }
        }
    };

    /**
     * 下载apk
     *
     * @author wragony
     */
    private void downloadApk() {
        if (retries > MAX_RETRIES) {
            mHandler.sendEmptyMessage(DOWN_ERROR);
            return;
        } else if (TextUtils.isEmpty(apkUrl)) {
            return;
        }

        downLoadThread = new Thread(downApkRunnable);
        downLoadThread.start();
    }

    public String getFilePath() {
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory() + "/fuxin/edtionInfo/";
        String apkName = "hyb.apk";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + apkName;
        return fileName;
    }
}
