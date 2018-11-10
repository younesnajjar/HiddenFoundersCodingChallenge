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

import java.util.List;

/**
 * Created by younes on 11/10/2018.
 */

public class TrendsAdapter  extends RecyclerView.Adapter<TrendsAdapter.ViewHolder> {

    String[] mData;
    private View cardView;
    public TrendsAdapter(String[] data){
        this.mData = data;
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
        holder.fillData(mData[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
//        ImageView offreView;
        TextView mTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.

            super(itemView);
            mTextView = itemView.findViewById(R.id.textviewx);


            //offerImageView = itemView.findViewById(R.id.offre);
        }
        public void fillData(final String data){
            mTextView.setText(data);
        }

    }

}
