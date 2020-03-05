package com.wyh.modulecommon.base;

import android.util.Log;

import com.wyh.modulecommon.constant.Settings;
import com.wyh.modulecommon.model.HttpParam;
import com.wyh.modulecommon.network.NetConst;
import com.wyh.modulecommon.network.NetProxy;
import com.wyh.modulecommon.network.response.ProgressListener;
import com.wyh.modulecommon.network.response.ProgressResponseBody;
import com.wyh.modulecommon.utils.FormatUtil;
import com.wyh.modulecommon.utils.SPUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 翁益亨 on 2020/1/19.
 * 网络请求基类
 */
public abstract class BaseService {
    private static final String TAG = "BaseService";

    private static long mApiTimeDiff = 0;

    private static long serverTime=0;//服务器时间全局定义

    /**获取服务器当前时间和本地时间的时间差**/
    public static long getApiTime() {
        return mApiTimeDiff;
    }

    public static long getServiceTime(){
        return serverTime;
    }


    /**网络连接客户端**/
    private static OkHttpClient client = new OkHttpClient
            .Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {//if not login and do request, just do not request
                    Request request = chain.request();
                    HttpUrl.Builder authorizedUrlBuilder = request.url()
                            .newBuilder()
                            .scheme(request.url().scheme())
                            .host(request.url().host())
                            .addQueryParameter(Settings.DEVICE_KEY, Settings.DEVICE_TYPE);//添加设备类型
                    if (SPUtils.contains(Settings.TOKEN_KEY)) {//添加Token信息
                        authorizedUrlBuilder.addQueryParameter(Settings.TOKEN_KEY, SPUtils.getString(Settings.TOKEN_KEY, ""));
                    }
                    Request newRequest = request.newBuilder()
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    Response response = chain.proceed(newRequest);
                    //set the default time zone
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                    serverTime = FormatUtil.formatDateTime(response.header("Date"));
                    long localTime = System.currentTimeMillis();
                    if (serverTime > 0) mApiTimeDiff = serverTime - localTime;//记录本地时间和服务器时间时间差
                    return response;
                }
            })
            .build();
    /***
     * FRESCO 图片加载客户端
     */
    static public final OkHttpClient IMAGE_CLIENT = new OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                }
                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    return new ArrayList<>();
                }
            }).build();

    /**网络服务API提供着**/
    static private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(NetConst.API_HOST)
            .callFactory(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    /**网络服务接口缓存**/
    static private Map<String, Object> _caches = Collections.synchronizedMap(new HashMap<String, Object>());
    /**
     * 获取接口服务
     * @param serviceClass
     */
    protected <T> T getApiService(Class<T> serviceClass) {
        if (serviceClass == null) return null;
        String serviceKey = serviceClass.getSimpleName();
        T service = (T) _caches.get(serviceClass.getSimpleName());
        if (service == null) {
            service = new NetProxy(retrofit).getProxy(serviceClass);//代理API
            _caches.put(serviceKey, service);
        }
        return service;
    }

    /**
     * 拓展业务接口-->直接拼接url内容-->应对第三方接口
     * 由于获取的是纯字符串，所以这里不进行数据Gson化解析
     * */
    protected <T> T getApiServiceWithUrl(Class<T> serviceClass,String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .callFactory(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        if (serviceClass == null) return null;
        //每次都实例化
        T service;
        service=retrofit.create(serviceClass);
        return service;
    }

    /**服务接口刷新时间缓存**/
    static private Map<String, Long> _timeCaches = new HashMap<>();
    /**接口刷新时间判断工具**/
    protected boolean needRefresh(String key, long time) {//need to add the network state, @Mark by SUM1818
        long currentTime = System.currentTimeMillis();
        if (key!= null && _timeCaches.containsKey(key)) {
            long lastTime = _timeCaches.get(key);
            if (currentTime - lastTime < time) {
                return false;
            }
        }
        _timeCaches.put(key, currentTime);
        return true;
    }


    /**
     * 获取接口服务
     * @param serviceClass
     * @param listener 下载进度监听
     */
    protected <T> T getDownloadService(Class<T> serviceClass, final ProgressListener... listener) {
        if (serviceClass == null) return null;
        /**创建文件下载客户端**/
        OkHttpClient downClient = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.d(TAG , "download url is:" + request.url());
                        Response response = chain.proceed(request);
                        if (listener != null && listener.length > 0) {
                            return response.newBuilder()
                                    .body(new ProgressResponseBody(response.body(), listener[0]))
                                    .build();
                        } else {
                            return response;
                        }
                    }
                })
                .build();
        /**下载服务API提供着**/
        Retrofit downRetrofit = new Retrofit.Builder()
                .baseUrl(NetConst.API_HOST)
                .callFactory(downClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return downRetrofit.create(serviceClass);
    }


    /**
     * 构建传输参数
     * @return
     */
    protected HttpParam getTransBody() {
        return new HttpParam();
    }


}
