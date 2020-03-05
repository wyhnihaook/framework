package com.wyh.modulecommon.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wyh.modulecommon.R;
import com.wyh.modulecommon.network.services.SystemService;
import com.wyh.modulecommon.network.subscriber.SafeOnlyNextSubscriber;
import com.wyh.modulecommon.utils.SystemBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 翁益亨 on 2020/1/17.
 */
public abstract class BaseActivity extends BaseAppCompatActivity {

    //初始化网络请求对象
    protected SystemService systemService;

    private void initServices() {
        systemService= MainApplication.getIns().getService(SystemService.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        initServices();
        super.onCreate(savedInstanceState);
        SystemBarUtil.setSystemBar(this,getResources().getColor(R.color.module_public_ffffff),true);
    }

    public void showError(String msg, View.OnClickListener clickListener) {
        toggleShowError(true, msg, clickListener);
    }
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    public void showNetError() {
        toggleNetworkError(true, null);
    }

    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    public void showEmpty(String msg,String go2, int imgId, View.OnClickListener clickListener) {
        toggleShowImageEmpty(true,msg,go2,imgId,clickListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //下拉刷新上拉加载框架植入

    public boolean isRefresh = true;

    public int page = 0;//列表数据加载页数
    public int pageCount = 20;//列表数据加载条数


    //设定上拉加载，下拉刷新监听器
   public void refreshLoad(SmartRefreshLayout refreshLayout) {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadData(false);
            }
        });
    }

    protected void loadData(boolean b) {
        isRefresh = b;
    }


}
