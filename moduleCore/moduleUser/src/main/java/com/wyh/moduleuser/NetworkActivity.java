package com.wyh.moduleuser;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.wyh.modulecommon.EventCenter;
import com.wyh.modulecommon.base.BaseActivity;
import com.wyh.modulecommon.constant.Routers;
import com.wyh.modulecommon.network.bean.HttpResultBean;
import com.wyh.modulecommon.network.bean.UpdateBean;
import com.wyh.modulecommon.network.subscriber.SafeOnlyNextSubscriber;
import com.wyh.modulecommon.utils.SystemUtil;
import com.wyh.moduleuser.databinding.ModuleUserActivityNetworkBinding;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.USER_NET)
public class NetworkActivity extends BaseActivity {


    //在布局文件中生成对应的binding布局之后通过rebuild 生成build下的dataBinding中实例
    ModuleUserActivityNetworkBinding mBind;

    @Autowired()
    String info = "";

    @BindView(R2.id.btn)
    Button btn;

    @BindView(R2.id.btn2)
    Button btn2;

    @BindView(R2.id.tv_net)
    TextView tv_net;

    @OnClick({R2.id.btn,R2.id.btn2})
    public void onClick(View view){
        int id=view.getId();
        if(id==R.id.btn){
            //网络请求示例，绑定生命周期，避免内存泄漏
            systemService.getAppRelease(SystemUtil.getVersion() + "").compose(this.bindToLifecycle()).subscribe(new SafeOnlyNextSubscriber<HttpResultBean<UpdateBean>>() {
                @Override
                public void onNext(HttpResultBean<UpdateBean> args) {
                    if (args.getCode() == 200) {
                        UpdateBean item = args.getData();
                        if (item != null) {
//                            mBind.getDetail().cloneFromBean(item);
                            mBind.setDetail(item);
//                            tv_net.setText(item.getDescription());
//                            tv_net.setText(new Gson().toJson(args).toString());
                        }
                    }
                }
            });
        }else if(id==R.id.btn2){
            ARouter.getInstance().build(Routers.USER_REFRESH)
                    .navigation();
        }
    }


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
        return R.layout.module_user_activity_network;
    }

    @Override
    protected void initViewsAndEvents() {
        mBind=  DataBindingUtil.setContentView(this, R.layout.module_user_activity_network);
        mBind.setDetail(new UpdateBean());
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
