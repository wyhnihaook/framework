package com.wyh.moduleuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyh.modulecommon.adapter.BaseRecyclerAdapter;
import com.wyh.moduleuser.R;
import com.wyh.moduleuser.holder.ListHolder;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public class RecyclerViewVerticalAdapter extends BaseRecyclerAdapter {


    private Context mContext;

    private int MAIN_INFO = 1;//列表

    private int status;//适配上述界面

    public RecyclerViewVerticalAdapter(Context mContext,int status) {
        this.mContext = mContext;
        this.status=status;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        if (viewType == 1) {
            return new ListHolder(view, viewType);
        }
        return new ListHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, boolean isItem) {
        if (status == MAIN_INFO) {
            String data= (String) getDatas().get(position);
            ListHolder holder= (ListHolder) viewHolder;
            holder.tv.setText(data);
        }
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == MAIN_INFO) {
            return LayoutInflater.from(mContext).inflate(R.layout.module_user_adapter_user_list, parent, false);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (viewType == 1) {
            return new ListHolder(realContentView, viewType);
        }
        return new ListHolder(realContentView, viewType);
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (status == 1) {
            return MAIN_INFO;
        }
        return super.getAdapterItemViewType(position);
    }

    @Override
    public int getAdapterItemCount() {
        return getDatas().size();
    }


}
