package com.wyh.modulecommon.network;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  API示例代理类
 */
public class ServiceProxy implements InvocationHandler{

    private Object target;//被代理对象
    private Object proxy;//代理对象

    private ServiceProxy() {
    }

    public ServiceProxy(Object target) {
        super();
        this.target = target;
    }

    /**
     * 获取被代理对象
     * @return
     */
    public Object getTarget() {
        return target;
    }

    /**
     * 获取代理对象
     * @return
     */
    public Object getProxy() {
        if(proxy == null) {
            proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        }
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);//do the origin method
    }
}
