package com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.models.GitHubTredingsCallBack;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.adapters.TrendsAdapter;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.api.RetrofitManager;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.listners.ScrollListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by younes on 11/9/2018.
 */

public class TrendingFragment extends Fragment {

    private RecyclerView trendingRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TrendsAdapter mAdapter;
    private NestedScrollView mNestedScroll;
    private ScrollListener mActivityScrollListener;
    // Index from which we know if the fragment is already loaded so when we come back we don't have to reload it
    private boolean isLoaded = false;
    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    private int pageToLoad=1;

    public TrendingFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trending, container, false);
        trendingRecyclerView = rootView.findViewById(R.id.trending_recyclerview);
        //Setting the Layout Manager
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        trendingRecyclerView.setLayoutManager(mLayoutManager);

        //Setting an empty Adapter
        mAdapter = new TrendsAdapter();
        trendingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trendingRecyclerView.setAdapter(mAdapter);


        if(!isLoaded)
            loadFirstPage();
        else{
            trendingRecyclerView.setAdapter(mAdapter);
        }

        trendingRecyclerView.addOnScrollListener(new CustomOnScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });




        return rootView;
    }
    private void loadFirstPage() {
        RetrofitManager.getInstance(getContext()).getRepositoriesInfos("created:>"+getDateMinusMonth(), pageToLoad, new Callback<GitHubTredingsCallBack>() {
            @Override
            public void onResponse(Call<GitHubTredingsCallBack> call, Response<GitHubTredingsCallBack> response) {

                mAdapter.addAll(response.body().getItems());
                mAdapter.addLoadingFooter();
                isLoaded = true;
                pageToLoad++;
            }

            @Override
            public void onFailure(Call<GitHubTredingsCallBack> call, Throwable t) {

            }
        });
    }
    private void loadNextPage(){
        RetrofitManager.getInstance(getContext()).getRepositoriesInfos("created:>2017-10-22", pageToLoad, new Callback<GitHubTredingsCallBack>() {
            @Override
            public void onResponse(Call<GitHubTredingsCallBack> call, Response<GitHubTredingsCallBack> response) {
                mAdapter.addAll(response.body().getItems());
                isLoading = false;
                mAdapter.addLoadingFooter();
                pageToLoad++;
            }

            @Override
            public void onFailure(Call<GitHubTredingsCallBack> call, Throwable t) {

            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ScrollListener)
            mActivityScrollListener = (ScrollListener) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mActivityScrollListener = null;
    }


    public abstract class CustomOnScrollListener extends RecyclerView.OnScrollListener {
    /*
    * This Listener is used for Paginating
     */

        LinearLayoutManager layoutManager;

        public CustomOnScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mActivityScrollListener.onScrollChange(dx,dy);
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
    private String getDateMinusMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(result);

    }
}
