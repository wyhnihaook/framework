package com.wyh.modulecommon.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by 翁益亨 on 2020/1/19.
 * 关于RecyclerView和SmartRefreshLayout的操作
 */
public class RefreshUtil {

    /**
     * RecyclerView竖直初始化
     * */
    public static void initialRecyclerViewLinearLayout(Activity activity,RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    /**
     * RecyclerView水平排布初始化
     * */
    protected void initialRecyclerViewGrid(Activity activity, int num, RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, num));
    }

    /**
     * 完成加载，将状态修改
     * @param refreshLoad true:下拉刷新  false：上拉记载
     * @param collection 组件
     * @param isFinishData true:没有更多数据，禁止上拉加载  false：还有数据，允许上拉加载  -->注意：在页面初始化的时候没有数据定位后就要退出页面再进入
     * */
    public static void initRefreshLoadMore(boolean refreshLoad, SmartRefreshLayout collection, boolean isFinishData) {
        if (refreshLoad) {
            collection.finishRefresh();
        } else {
            if (isFinishData) {
                collection.finishLoadMoreWithNoMoreData();
            } else {
                collection.finishLoadMore();
            }
        }
    }
}
