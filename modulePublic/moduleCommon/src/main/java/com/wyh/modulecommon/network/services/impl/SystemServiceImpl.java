package com.wyh.modulecommon.network.services.impl;

import com.wyh.modulecommon.base.BaseService;
import com.wyh.modulecommon.network.api.SystemProvider;
import com.wyh.modulecommon.network.bean.HttpResultBean;
import com.wyh.modulecommon.network.bean.UpdateBean;
import com.wyh.modulecommon.network.services.SystemService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public class SystemServiceImpl extends BaseService implements SystemService{
    @Override
    public Observable<HttpResultBean<UpdateBean>> getAppRelease(String version) {
        return getApiService(SystemProvider.class).getAppRelease(getTransBody().put("version",version));
    }

    @Override
    public Observable<String> getStockUpInfo() {
        return getApiServiceWithUrl(SystemProvider.class,
                "http://stock.gtimg.cn").getStockUpInfo(
                getTransBody().put("t", "rankasz/chr").put("p","1").put("o","0")
                        .put("l","600").put("v","list_data")).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResultBean<String>> post(String data) {
        return getApiService(SystemProvider.class).post(getTransBody().put("data",data));
    }
}
