package com.mvvm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mvvm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chiclaim on 2016/01/27
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {


    //内部维护数据源
    protected List<T> list = new ArrayList<>();

    public boolean mShowFooter;
    public boolean mShowHead;
    public final static int FOOTER_TYPE = 99;
    protected Context mContext;

    protected LayoutInflater inflater;

    public BaseAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }


    public static class UnknownViewHolder extends RecyclerView.ViewHolder {
        public UnknownViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class Footer extends RecyclerView.ViewHolder {
        public Footer(View itemView) {
            super(itemView);
        }
    }


    public T getModel(int position) {
        if (list.size() == 0) {
            return null;
        }
        return list.get(position);
    }

    public int getModelSize() {
        return list.size();
    }


    public void showFooter() {
        hideHead();
        mShowFooter = true;
        notifyDataSetChanged();
    }

    public void hideFooter() {
        mShowFooter = false;
        notifyDataSetChanged();
    }

    public void showHead() {
        mShowHead = true;
        hideFooter();
    }

    public void hideHead() {
        mShowHead = false;
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowFooter && position == getMyItemCount()) {
            return FOOTER_TYPE;
        }
        return getMyItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder arg0, int positon) {
        int type = getItemViewType(positon);
        switch (type) {
            case FOOTER_TYPE:
                break;
            default:
                onMyBindViewHolder(arg0, mShowHead ? positon - 1 : positon);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case FOOTER_TYPE:
                return new Footer(getLayout(R.layout.footer_loading_layout, viewGroup));
            default:
                return onMyCreateViewHolder(viewGroup, viewType);
        }
    }

    @Override
    public int getItemCount() {
        return getMyItemCount() + (mShowFooter ? 1 : 0) + (mShowHead ? 1 : 0);
    }

    /**
     * 替代getItemViewType
     *
     * @return
     */
    public int getMyItemCount() {
        return list.size();
    }

    /**
     * 替代onCreateViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 替代onBindViewHolder
     *
     * @param viewHolder
     * @param position
     */
    public abstract void onMyBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    /**
     * 替代getItemViewType
     *
     * @param position
     * @return
     */
    public abstract int getMyItemViewType(int position);


    public void appendItems(List items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        //int startPosition = list.size();
        list.addAll(items);
        //notifyItemRangeInserted(startPosition, items.size());
        notifyDataSetChanged();
    }

    public void appendItem(T item) {
        if (item == null) {
            return;
        }
        appendItems(Arrays.asList(item));
    }


    public void removeAll() {
        list.clear();
        hideFooter();
        notifyDataSetChanged();
    }

    public View getLayout(int layoutId, ViewGroup parent) {
        return inflater.inflate(layoutId, parent, false);
    }


    public RecyclerView.ViewHolder getUnKnowViewHolder(ViewGroup parent) {
        return new UnknownViewHolder(getLayout(R.layout.item_unknown, parent));
    }

}
