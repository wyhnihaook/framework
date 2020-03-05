package com.wyh.moduleuser;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wyh.modulecommon.EventCenter;
import com.wyh.modulecommon.base.BaseActivity;
import com.wyh.modulecommon.constant.Constants;
import com.wyh.modulecommon.constant.Routers;
import com.wyh.modulecommon.utils.PermissionUtil;
import com.wyh.modulecommon.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;

@Route(path= Routers.USER_MAIN)
public class MainActivity extends BaseActivity {

    //携带路由参数，如果是对象必须在(name="传递的名称")，才能识别到对象
    @Autowired()
    String name = "";

    @BindView(R2.id.ll_root)
    LinearLayout linearLayout;

    @BindView(R2.id.tv)
    TextView textView;

    @OnClick({R2.id.ll_root})
    public void onClick(View view){
        int id=view.getId();
        //注意这里要使用R去定位而不能使用R2.因为R2就是一个替代品，最后还是会将所有的资源初始化到R文件中
        //注意这里不支持用switch...case...
        if(id==R.id.ll_root){
            ARouter.getInstance().build(Routers.USER_IMAGE)
                    .navigation();
        }
    }

    boolean isDismissFirst = true;
    boolean isDestroy = false;

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        int eventCode = eventCenter.getEventCode();
        switch (eventCode) {
            case Constants.EVENTBUS_WRITE_REQUESTCODE:
                //同意之后还是会走这个方法，已经授权了
                //每次申请权限都会有两次同意
                if(!isDestroy){
                    isDestroy=!isDestroy;
                }else{
                    return ;
                }
                linearLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //goto launch activity or homeactivity
                        //延时三秒跳转到首页
                        ToastUtil.showLongToast("同意了授权");
                    }
                }, 3000);
                break;
            case Constants.EVENTBUS_DENY_WRITE_REQUESTCODE:
                //只响应第一次取消按钮
                if (isDismissFirst) {
                    isDismissFirst = !isDismissFirst;
                    new AppSettingsDialog.Builder(this)
                            .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限")
                            .setTitle("必需权限")
                            .build()
                            .show();
                }
                break;
        }
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
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.module_user_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        //页面过渡占位图示例
        showError("当前显示错误，请点击重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复原布局
                toggleShowError(false,"",null);
            }
        });

    }

    @Override
    protected View getLoadingTargetView() {
        return linearLayout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //一般所有授权都在欢迎页全部操作
        PermissionUtil.requestWrite(this);
    }
}
