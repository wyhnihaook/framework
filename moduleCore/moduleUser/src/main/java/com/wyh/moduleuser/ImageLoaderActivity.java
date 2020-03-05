package com.wyh.moduleuser;

import android.net.Uri;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wyh.modulecommon.EventCenter;
import com.wyh.modulecommon.base.BaseActivity;
import com.wyh.modulecommon.constant.Routers;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path= Routers.USER_IMAGE)
public class ImageLoaderActivity extends BaseActivity {


    @BindView(R2.id.image)
    SimpleDraweeView image;


    @OnClick({R2.id.image})
    public void onClick(View view){
        int id=view.getId();
        if(id==R.id.image){
            ARouter.getInstance().build(Routers.USER_NET)
                    .withString("info","跳转携带参数")
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
        return R.layout.module_user_activity_image_loader;
    }

    @Override
    protected void initViewsAndEvents() {

        //加载网络图片--》支持加载http和https
        Uri uri = Uri.parse("https://travel.12306.cn/imgs/resources/uploadfiles/images/a9b9c76d-36ba-4e4a-8e02-9e6a1a991da0_news_W540_H300.jpg");
        image.setImageURI(uri);


    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
