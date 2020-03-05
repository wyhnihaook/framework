package com.wyh.modulecommon.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wyh.modulecommon.EventCenter;
import com.wyh.modulecommon.R;
import com.wyh.modulecommon.constant.Constants;
import com.wyh.modulecommon.helper.bghelper.VaryViewHelperController;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.internal.CustomAdapt;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 翁益亨 on 2020/1/17.
 * 基类
 */
public abstract class BaseAppCompatActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks,CustomAdapt {
    protected Context mContext;
    protected Unbinder unbind;//用于接收ButterKnife返回对象用于解绑注册事件

    /**
     * 当前的view的控制界面显示---->网络异常,网络过慢,请求空数据显示
     */
    private VaryViewHelperController mVaryViewHelperController;

    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionType()) {
                case LEFT:
                    //进入时动画，上一个页面退出时的动画
                    overridePendingTransition(R.anim.module_public_left_in,R.anim.module_public_push_activity);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.module_public_right_in,R.anim.module_public_push_activity);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.module_public_top_in,R.anim.module_public_push_activity);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.module_public_bottom_in,R.anim.module_public_push_activity);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.module_public_scale_in,R.anim.module_public_push_activity);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.module_public_fade_in,R.anim.module_public_push_activity);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        if(isBindEventBus()){
            EventBus.getDefault().register(this);
        }


        //todo  修改顶部栏背景颜色和字体颜色

        mContext=this;

        if(getContentViewLayoutID()!=0){
            setContentView(getContentViewLayoutID());
        }else{
            throw new IllegalArgumentException("You must return a  contentView layout resource Id");
        }

        ARouter.getInstance().inject(this);//要获取参数传递就要进行路由信息注入，在Application中销毁
        initViewsAndEvents();
    }

    protected void initVaryViewHelper(){}


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbind= ButterKnife.bind(this);
        initVaryViewHelper();
        if (null != getLoadingTargetView()) {
            //展示不同情况下的界面结构
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }


    @Override
    public void finish() {
        super.finish();
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionType()) {
                case LEFT:
                    //上一个页面进入的动画，当前页面退出动画
                    overridePendingTransition(R.anim.module_public_push_activity,R.anim.module_public_left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.module_public_push_activity,R.anim.module_public_right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.module_public_push_activity,R.anim.module_public_top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.module_public_push_activity,R.anim.module_public_bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.module_public_push_activity,R.anim.module_public_scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.module_public_push_activity,R.anim.module_public_fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * activity的跳转
     * */
    protected void readyGo(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    protected void readyGoWithData(Class<?> clazz,Bundle bundle){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoWithDataThenKill(Class<?> clazz,Bundle bundle){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void readyGoWaitResult(Class<?> clazz,int requestCode){
        Intent intent=new Intent(this,clazz);
        startActivityForResult(intent,requestCode);
    }

    protected void readyGoWithDataWaitResult(Class<?> clazz,Bundle bundle,int requestCode){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 显示正在加载中
     *基于getLoadingTargetView()不为空的情况
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }
    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowImageEmpty(boolean toggle, String msg,String go2,int imgId, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showImageEmpty(msg,go2, imgId,onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }
    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }
    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowNewLoading(boolean toggle) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading();
        } else {
            mVaryViewHelperController.restore();
        }
    }
    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }


    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }

    //将授权转移到easyPermissions执行
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 动态授权
     * 在需要授权的界面复写以下方法即可
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // 用户授权成功
        switch (requestCode) {
            case Constants.WRITE_REQUESTCODE:
                //同意授权
                EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_WRITE_REQUESTCODE));
                break;

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // 用户授权失败
        switch (requestCode) {
            case Constants.WRITE_REQUESTCODE:
                //永久性取消授权
                if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
                    //这个方法有个前提是，用户点击了“不再询问”后，才判断权限没有被获取到
                    //手动打开权限弹窗提示
                    EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_DENY_WRITE_REQUESTCODE));
                }else{
                    //普通取消操作
//                    finish();
                }
                break;
        }
    }


    /**
     * 个性化定制，不需要在清单文件中全局声明
     *
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选择一个作为基准进行适配)
     *
     * @return {@code true} 为按照宽度进行适配, {@code false} 为按照高度进行适配
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    /**
     * 设计图尺寸为 1080px * 1920px, 高换算成 dp 为 960 (1920px / 2 = 960dp)
     * <p>
     * 返回的设计尺寸, 单位 dp
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return 单位 dp
     */
    @Override
    public float getSizeInDp() {
        return 667;
    }

    //避免适配失效
    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }


    /**
     * 广播接受数据继承类实现相关操作
     *
     * @param eventCenter
     */
    protected abstract void onEventComming(EventCenter eventCenter);
    /**
     * activity进入退出是否需要动画设置
     * */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * activity动画选择器
     * */
    protected abstract TransitionMode getOverridePendingTransitionType();

    /**
     * 界面是否注册监听器
     * */
    protected abstract boolean isBindEventBus();


    /**
     *设置当前布局
     * */
    protected abstract int getContentViewLayoutID();

    /**
     * 实现数据赋值
     * */
    protected abstract void initViewsAndEvents();

    /**
     * 需要被替代的布局中的布局
     */
    protected abstract View getLoadingTargetView();
}
