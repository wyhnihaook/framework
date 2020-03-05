package com.wyh.moduleuser.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wyh.moduleuser.R;
import com.wyh.moduleuser.R2;

import butterknife.BindView;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public class ListHolder extends RecyclerView.ViewHolder {

    public int viewType;

    public TextView tv;

    public ListHolder(View itemView, int viewType) {
        super(itemView);
        //当内容不为空的时候网络请求进行数据初始化
        this.viewType = viewType;

        tv=itemView.findViewById(R.id.tv);
    }
}
