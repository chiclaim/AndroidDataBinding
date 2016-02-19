package com.mvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.R;
import com.mvvm.databinding.ItemContributorBinding;
import com.mvvm.model.Contributor;
import com.squareup.picasso.Picasso;


/**
 * Created by chiclaim on 2016/01/27
 */
public class ContributorAdapter extends BaseAdapter<Contributor> {

    public ContributorAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContributorBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_contributor, parent, false);
        ContributorViewHolder viewHolder = new ContributorViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ContributorViewHolder contributorViewHolder = (ContributorViewHolder) viewHolder;
        Contributor contributor = getModel(position);
        contributorViewHolder.getBinding().setVariable(com.mvvm.BR.contributor, contributor);
        contributorViewHolder.getBinding().executePendingBindings();
        Picasso.with(mContext).load(contributor.getAvatar_url()).
                into(contributorViewHolder.binding.ivAvatar);
    }

    @Override
    public int getMyItemViewType(int position) {
        return 0;
    }


    public class ContributorViewHolder extends RecyclerView.ViewHolder {

        ItemContributorBinding binding;

        public void setBinding(ItemContributorBinding binding) {
            this.binding = binding;
        }

        public ItemContributorBinding getBinding() {
            return binding;
        }

        public ContributorViewHolder(View itemView) {
            super(itemView);
        }
    }

}
