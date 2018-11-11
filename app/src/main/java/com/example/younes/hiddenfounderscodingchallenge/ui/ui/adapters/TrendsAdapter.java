package com.example.younes.hiddenfounderscodingchallenge.ui.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by younes on 11/10/2018.
 */

public class TrendsAdapter  extends RecyclerView.Adapter<TrendsAdapter.ViewHolder> {

    List<Item> mItems;
    Context mContext;
    private View cardView;
    public TrendsAdapter(List<Item> items){
        this.mItems = items;
    }
    @Override
    public TrendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        cardView = inflater.inflate(R.layout.trends_item
                , parent, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrendsAdapter.ViewHolder holder, int position) {
        holder.fillData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
//        ImageView offreView;
        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.repo_description)
        TextView repoDescription;
        @BindView(R.id.owner_name)
        TextView repoOwnerName;
        @BindView(R.id.avatar_icon)
        ImageView repoOwnerAvatar;
        @BindView(R.id.stars_count)
        TextView repoStarsCount;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.

            super(itemView);
            ButterKnife.bind(this,itemView);


            //offerImageView = itemView.findViewById(R.id.offre);
        }
        public void fillData(Item item){
            repoName.setText(item.getName());
            repoDescription.setText(item.getDescription());
            repoOwnerName.setText(item.getOwner().getLogin());
            Picasso.get().load(item.getOwner().getAvatarUrl()).resize(100,100).into(repoOwnerAvatar);
            repoStarsCount.setText(item.getStargazersCount().toString());

        }

    }

}
