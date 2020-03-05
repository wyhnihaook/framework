package com.wyh.modulecommon.network.api;

import com.wyh.modulecommon.network.bean.HttpResultBean;
import com.wyh.modulecommon.network.bean.UpdateBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public interface SystemProvider {
    /**
     * 获取更新弹窗
     * */
    @GET("/appRelease/release.json")
    Observable<HttpResultBean<UpdateBean>> getAppRelease(@QueryMap Map<String, Object> map);


    /**
     * 获取涨跌排行榜
     **/
    @GET("/data/view/rank.php")
    Observable<String> getStockUpInfo(@QueryMap Map<String, Object> map);

    /**
     * POST请求示例-->url和参数需要手动补全
     * */
    @FormUrlEncoded
    @POST("/user/....json")
    Observable<HttpResultBean<String>> post(@FieldMap Map<String, Object> map);

}
