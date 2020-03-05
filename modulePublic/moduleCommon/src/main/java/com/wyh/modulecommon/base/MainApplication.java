package com.wyh.modulecommon.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.wyh.modulecommon.network.ServiceProxy;
import com.wyh.modulecommon.network.services.impl.SystemServiceImpl;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 翁益亨 on 2020/1/17.
 */
public class MainApplication extends Application {

    private boolean isDebugARouter =true;//判断当前是开发模式还是Release上线版本，对应报错信息要消除

    private  Map<String, Object> _caches = new HashMap<>();

    private Handler mHandler = new Handler();

    private static MainApplication _ins;

    public static MainApplication getIns() {
        return _ins;
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _ins = this;

        if (isDebugARouter) {
            ARouter.openLog();//打印日志
            ARouter.openDebug();//线上版本需要关闭
        }
        ARouter.init(this);


        loadService();//初始化网络请求实例


        //初始化fresco
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, BaseService.IMAGE_CLIENT)
                .build();
        Fresco.initialize(this, config);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();//销毁路由实例
    }

    /**在主线程执行方法**/
    public void runOnUiThread(Runnable runnable) {
        if (runnable != null) mHandler.post(runnable);
    }


    /**获取网络请求实例，保存在缓存中，通过类型获得服务,避免多次新建重复对象**/

    public <T> T getService(Class<T> serviceClass) {
        String name = serviceClass.getSimpleName();
        String key = name.substring(0,1).toLowerCase() + name.substring(1, name.length());
        return (T) _caches.get(key);
    }

    public void loadService() {
        Class[] classNames = new Class[] {
                SystemServiceImpl.class
        };
        for (Class clazz : classNames) {
            String name = clazz.getSimpleName();
            String key = name.substring(0,1).toLowerCase() + name.substring(1, name.length() - 4);
            try {
                _caches.put(key, new ServiceProxy(clazz.newInstance()).getProxy());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
