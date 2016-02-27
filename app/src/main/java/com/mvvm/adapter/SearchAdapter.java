package com.mvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mvvm.BR;
import com.mvvm.R;
import com.mvvm.databinding.ItemSearchBinding;

/**
 * Created by chiclaim on 2016/02/26
 */
public class SearchAdapter extends BaseAdapter<String> {


    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_search, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        String value = getModel(position);
        itemViewHolder.getBinding().setVariable(BR.value, value);
        itemViewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getMyItemViewType(int position) {
        return 0;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemSearchBinding itemBinding;

        public ItemViewHolder(ItemSearchBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public ItemSearchBinding getBinding() {
            return itemBinding;
        }

    }
}
