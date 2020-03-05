package com.wyh.modulecommon.network;

import android.util.Log;


import com.wyh.modulecommon.R;
import com.wyh.modulecommon.base.MainApplication;
import com.wyh.modulecommon.network.bean.HttpResultBean;
import com.wyh.modulecommon.utils.SystemUtil;
import com.wyh.modulecommon.utils.ToastUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by 翁益亨 on 2020/1/17.
 * 用于代理网络请求数据，连接失败后再多次进行请求
 */
public class NetProxy implements InvocationHandler {

    private static final String TAG = "NetProxy";
    private Retrofit retrofit;
    private Object target;

    public NetProxy(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (!SystemUtil.isNetworkConnected()) {
                ToastUtil.showShortToast(MainApplication.getIns().getString(R.string.module_public_network_error));
                return Observable.never();
            } else {
                final Observable<Object> ob = (Observable<Object>) method.invoke(target, args);
                return ob.map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object obj) {
                        if (obj instanceof HttpResultBean) {
                            HttpResultBean baseModel = (HttpResultBean) obj;
                            if (baseModel != null && baseModel.getCode() == 9900) {//没有登陆, 需要跳转到登录页面

                                throw new UnsupportedOperationException("HackReLogin");
                            }
                        }
                        return obj;
                    }
                }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Observable<? extends Throwable> errors) {
                        return errors.zipWith(Observable.range(1, 3), new BiFunction<Throwable, Integer, Object>() {
                            @Override
                            public Object apply(Throwable throwable, Integer i) {
                                if (throwable instanceof UnsupportedOperationException) {
                                    Log.d(TAG, "retry count is:" + i);
                                    return i;
                                } else {
                                    return throwable;
                                }
                            }
                        }).flatMap(new Function<Object, Observable<? extends Long>>() {//在2^i秒中之后重新尝试接口
                            @Override
                            public Observable<? extends Long> apply(Object retryCount) {
                                if (retryCount instanceof Integer) {
                                    return Observable.timer((long) Math.pow(2, (int)retryCount), TimeUnit.SECONDS);
                                } else if (retryCount instanceof Throwable){
                                    return Observable.error((Throwable) retryCount);
                                } else {
                                    return Observable.never();
                                }
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("NetProxy", "invoke error");
            return method.invoke(target, args);
        }
    }

    public <T> T getProxy(Class<T> clazz) {
        target = retrofit.create(clazz);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
        return proxy;
    }

}
