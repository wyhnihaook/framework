package com.wyh.modulecommon.network.subscriber;

import android.util.Log;


import com.wyh.modulecommon.R;
import com.wyh.modulecommon.base.MainApplication;
import com.wyh.modulecommon.network.bean.HttpResultBean;
import com.wyh.modulecommon.utils.SPUtils;
import com.wyh.modulecommon.utils.ToastUtil;


import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by 翁益亨 on 2020/1/19.
 * 复写方法，抽象调用
 */
public class SafeOnlyNextSubscriber<T> implements Observer<T> {

    private static final String TAG = "SafeOnlyNextSubscriber";

    @Override
    public void onError(Throwable e) {
        if (e instanceof UnsupportedOperationException) {
            Log.d(TAG, e.getMessage());
        } else if ((e instanceof ConnectException) ) {
            //(e instanceof SocketTimeoutException) ||   || (e instanceof SocketException)
            ToastUtil.showShortToast(MainApplication.getIns().getString(R.string.module_public_network_connect_out));
        } else if (e instanceof com.google.gson.stream.MalformedJsonException){
            ToastUtil.showShortToast("服务器响应异常，请稍后重试");
        }else if(e instanceof SocketTimeoutException){
//            ToastUtil.showShortToast("请求超时！");
        } else {
//            ErrorUtil.showError(e);
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T args){
         if(args instanceof HttpResultBean){
            HttpResultBean httpResultBean= (HttpResultBean) args;
            if(httpResultBean.getCode()==9900){
                //do reLogin 跳转到登录
                ToastUtil.showLongToast("请先登录");
                SPUtils.remove("token");
            } else if (!httpResultBean.isSuccess()) {
                ToastUtil.showShortToast(httpResultBean.getResultMsg());
            }
        }

        //如果有其他类型的实体类，需要添加
    }
}
