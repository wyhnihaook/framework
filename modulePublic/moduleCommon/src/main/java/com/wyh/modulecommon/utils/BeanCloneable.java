package com.wyh.modulecommon.utils;

/**
 * Created by js on 2016/11/7.
 */
public interface BeanCloneable<T> {
    /**
     * 复制对象
     * @return
     */
     void cloneFromBean(T src);

}
