package com.wyh.modulecommon.network.services;

import com.wyh.modulecommon.network.bean.HttpResultBean;
import com.wyh.modulecommon.network.bean.UpdateBean;

import io.reactivex.Observable;


/**
 * Created by 翁益亨 on 2020/1/19.
 */
public interface SystemService {
    //普通Get请求
    Observable<HttpResultBean<UpdateBean>> getAppRelease(String version);

    //第三方外链Get请求
    Observable<String> getStockUpInfo();

    //普通post请求-->内容需要补全
    Observable<HttpResultBean<String>> post(String data);
}
