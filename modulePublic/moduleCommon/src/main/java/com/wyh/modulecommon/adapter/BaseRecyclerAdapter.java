package com.wyh.modulecommon.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public abstract class BaseRecyclerAdapter <T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected View customLoadMoreView = null;
    protected View customHeaderView = null;
    private boolean isFooterEnable = true;
    protected List<T> datas = new ArrayList<T>();

    public List<T> getDatas() {
        if (datas == null)
            datas = new ArrayList<T>();
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    private void showFooter(View footerview, boolean show) {
//        if (isFooterEnable && footerview != null && footerview instanceof IFooterCallBack) {
//            IFooterCallBack footerCallBack = (IFooterCallBack) footerview;
//            if (show) {
//                if (!footerCallBack.isShowing()) {
//                    footerCallBack.show(show);
//                }
//            } else {
//                if (getAdapterItemCount() == 0 && footerCallBack.isShowing()) {
//                    footerCallBack.show(false);
//                } else if (getAdapterItemCount() != 0 && !footerCallBack.isShowing()) {
//                    footerCallBack.show(true);
//                }
//            }
//        }
    }

    private boolean removeFooter = false;

    public void addFooterView() {

        if (removeFooter) {
            notifyItemInserted(getItemCount());
            removeFooter = false;
            showFooter(customLoadMoreView, true);
        }
    }

    public boolean isFooterShowing() {
        return !removeFooter;
    }

    public void removeFooterView() {
        if (!removeFooter) {
            notifyItemRemoved(getItemCount() - 1);
            removeFooter = true;
        }
    }

    public abstract VH getViewHolder(View view, int viewType);

    /**
     * @param parent
     * @param viewType
     * @param isItem   如果是true，才需要做处理 ,但是这个值总是true
     */
//    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem);

    /**
     * 替代onBindViewHolder方法，实现这个方法就行了
     *
     * @param holder
     * @param position
     */
    public abstract void onBindViewHolder(VH holder, int position, boolean isItem);

    public abstract View onCreateContentView(ViewGroup parent, int viewType);

    public abstract VH onCompatCreateViewHolder(View realContentView, int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        showFooter(customLoadMoreView, false);
        if (viewType == VIEW_TYPES.FOOTER) {
            removeViewFromParent(customLoadMoreView);
            VH viewHolder = getViewHolder(customLoadMoreView, viewType);
            return viewHolder;
        } else if (viewType == VIEW_TYPES.HEADER) {
            removeViewFromParent(customHeaderView);
            VH viewHolder = getViewHolder(customHeaderView, viewType);
            return viewHolder;
        }

        View contentView = onCreateContentView(parent, viewType);//返回当前的布局情况


        return onCompatCreateViewHolder(contentView, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        View itemView = holder.itemView;


        int start = getStart();
        if (!isHeader(position) && !isFooter(position)) {
            onBindViewHolder(holder, position - start, true);
        }
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(isFooter(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setHeaderView(View headerView, RecyclerView recyclerView) {
        if (recyclerView == null) return;
        removeViewFromParent(customLoadMoreView);
        customHeaderView = headerView;
        notifyDataSetChanged();
    }

    public View setHeaderView(@LayoutRes int id, RecyclerView recyclerView) {

        if (recyclerView == null) return null;
        Context context = recyclerView.getContext();
        String resourceTypeName = context.getResources().getResourceTypeName(id);
        if (!resourceTypeName.contains("layout")) {
            throw new RuntimeException(context.getResources().getResourceName(id) + " is a illegal layoutid , please check your layout id first !");
        }
        FrameLayout headerview = new FrameLayout(recyclerView.getContext());
        customHeaderView = LayoutInflater.from(context).inflate(id, headerview, false);
        notifyDataSetChanged();
        return customHeaderView;
    }

    public boolean isFooter(int position) {
        int start = getStart();
        return customLoadMoreView != null && position >= getAdapterItemCount() + start;
    }

    public boolean isHeader(int position) {
        return getStart() > 0 && position == 0;
    }

    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    @Override
    public final int getItemViewType(int position) {
        if (isHeader(position)) {
            return VIEW_TYPES.HEADER;
        } else if (isFooter(position)) {
            return VIEW_TYPES.FOOTER;
        } else {
            position = getStart() > 0 ? position - 1 : position;
            return getAdapterItemViewType(position);
        }
    }

    /**
     * 实现此方法来设置viewType
     *
     * @param position
     * @return viewType
     */
    public int getAdapterItemViewType(int position) {
        return VIEW_TYPES.NORMAL;
    }

    public int getStart() {
        return customHeaderView == null ? 0 : 1;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public final int getItemCount() {
        int count = getAdapterItemCount();
        count += getStart();
        if (customLoadMoreView != null && !removeFooter) {
            count++;
        }
        return count;
    }

    /**
     * Returns the number of items in the adapter bound to the parent
     * RecyclerView.
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();

    /**
     * Swap the item of list
     *
     * @param list data list
     * @param from position from
     * @param to   position to
     */
    public void swapPositions(List<?> list, int from, int to) {
        Collections.swap(list, from, to);
    }

    public void insideEnableFooter(boolean enable) {
        isFooterEnable = enable;
    }

    /**
     * Insert a item to the list of the adapter
     *
     * @param list     data list
     * @param object   object T
     * @param position position
     * @param <T>      in T
     */
    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        notifyItemInserted(position + getStart());
    }

    /**
     * Remove a item of the list of the adapter
     *
     * @param list     data list
     * @param position position
     */
    public void remove(List<?> list, int position) {
        if (list.size() > 0) {
            notifyItemRemoved(position + getStart());
        }
    }

    /**
     * Clear the list of the adapter
     *
     * @param list data list
     */
    public void clear(List<?> list) {
        int start = getStart();
        int size = list.size() + start;
        list.clear();
        notifyItemRangeRemoved(start, size);
    }

    protected class VIEW_TYPES {
        public static final int FOOTER = -1;
        public static final int HEADER = -3;
        public static final int NORMAL = -4;
    }


    /**
     * 自定义接口
     */
    public RecyclerViewClick recyclerViewItemListener;

    public interface RecyclerViewClick {
        void onItemClick(int position);
    }

    public void setOnRecyclerItemListener(RecyclerViewClick recyclerViewItemListener) {
        this.recyclerViewItemListener = recyclerViewItemListener;
    }


    public static void removeViewFromParent(View view) {
        if (view == null) {
            return;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }

}
