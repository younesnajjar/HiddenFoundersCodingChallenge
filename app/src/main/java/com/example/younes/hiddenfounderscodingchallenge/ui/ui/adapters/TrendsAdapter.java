package com.example.younes.hiddenfounderscodingchallenge.ui.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.models.Item;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.AppConstants;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by younes on 11/10/2018.
 */

public class TrendsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LOADING = 0;
    private static final int ITEM = 1;
    List<Item> mItems;
    Context mContext;
    private boolean isLoadingAdded = false;
    public TrendsAdapter(Context context){
        mContext = context;
        this.mItems = new ArrayList<>();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1;
        if(viewType() == AppConstants.VERTICAL_LINEAR_VIEW)
            v1 = inflater.inflate(R.layout.trends_item, parent, false);
        else
            v1 = inflater.inflate(R.layout.trends_grid_item, parent, false);


        viewHolder = new TrendsViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == ITEM){
            TrendsViewHolder vh = (TrendsViewHolder) holder;
            vh.fillData(mItems.get(position));
        }else{/*    Do Nothing :)     */}
    }
    @Override
    public int getItemViewType(int position) {
        return (position == mItems.size() - 1 && isLoadingAdded) ? 0 : 1;
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public class TrendsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_name)
        TextView repoName;
        @Nullable
        @BindView(R.id.repo_description)
        TextView repoDescription;
        @Nullable
        @BindView(R.id.owner_name)
        TextView repoOwnerName;
        @BindView(R.id.avatar_icon)
        ImageView repoOwnerAvatar;
        @BindView(R.id.stars_count)
        TextView repoStarsCount;

        public TrendsViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);

        }
        public void fillData(Item item){
            if(item.getId() == null) return;
            repoName.setText(item.getName());
            Picasso.get().load(item.getOwner().getAvatarUrl()).error(R.drawable.ic_action_image).into(repoOwnerAvatar);
            repoStarsCount.setText(item.getStargazersCount().toString());
            if(viewType() == AppConstants.VERTICAL_LINEAR_VIEW){
                repoDescription.setText(item.getDescription());
                repoOwnerName.setText(item.getOwner().getLogin());
            }
        }

    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    /*
    * All the functions below are used for pagination --------------------------------------------
     */
    public void add(Item item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(List<Item> items) {
        for (Item mc : items) {
            add(mc);
        }
    }

    public void remove(Item item) {
        int position = mItems.indexOf(item);
        if (position > -1) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }
    private int viewType(){
        return SaveSharedPreference.getViewMode(mContext);
    }
}
