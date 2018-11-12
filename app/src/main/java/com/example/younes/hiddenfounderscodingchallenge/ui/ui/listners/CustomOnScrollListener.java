package com.example.younes.hiddenfounderscodingchallenge.ui.ui.listners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by younes on 11/11/2018.
 */

public abstract class CustomOnScrollListener extends RecyclerView.OnScrollListener {
    /*
    *\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    * This Listener is used for Paginating \\
    *\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
     */

    LinearLayoutManager layoutManager;

    public CustomOnScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && layoutManager.findLastVisibleItemPosition()>=totalItemCount-1) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount > 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLoading();
}