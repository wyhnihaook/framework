package com.wyh.moduleuser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wyh.modulecommon.EventCenter;
import com.wyh.modulecommon.base.BaseActivity;
import com.wyh.modulecommon.constant.Routers;
import com.wyh.modulecommon.utils.RefreshUtil;
import com.wyh.moduleuser.adapter.RecyclerViewVerticalAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = Routers.USER_REFRESH)
public class RefreshLayoutActivity extends BaseActivity {

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;


    RecyclerViewVerticalAdapter adapter;

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionType() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.module_user_activity_refresh_layout;
    }

    @Override
    protected void initViewsAndEvents() {

        RefreshUtil.initialRecyclerViewLinearLayout(this, recyclerView);
        refreshLoad(refreshLayout);

        adapter = new RecyclerViewVerticalAdapter(this, 1);

        View headerView=adapter.setHeaderView(R.layout.module_user_adapter_user_list_header, recyclerView);

        TextView textView=headerView.findViewById(R.id.tv_header);
        textView.setText("哈哈哈哈");

        List<String> data=new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add(i+"");
        }

        adapter.setDatas(data);

        recyclerView.setAdapter(adapter);




    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    protected void loadData(boolean b) {
        super.loadData(b);

        RefreshUtil.initRefreshLoadMore(b,refreshLayout,b?false:true);

        if(b){
           adapter.getDatas().remove(0);
        }else{
            List<String> data=new ArrayList<>();
            for(int i=20;i<40;i++){
                data.add(i+"");
            }

            adapter.getDatas().addAll(data);
        }
        adapter.notifyDataSetChanged();
    }
}
