package com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.models.GitHubTredingsCallBack;
import com.example.younes.hiddenfounderscodingchallenge.ui.models.Item;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.adapters.TrendsAdapter;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.api.RetrofitManager;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.listners.CustomOnScrollListener;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.AppConstants;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.SaveSharedPreference;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.ToastMessages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by younes on 11/9/2018.
 */

public class TrendingFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView trendingRecyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;
    private TrendsAdapter mAdapter;
    // Index from which we know if the fragment is already loaded
    private boolean isLoaded = false;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;
    // Index indicates the index of the page to load next
    private int pageToLoad=1;

    public TrendingFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trending, container, false);

        //Declaring the recyclerView
        trendingRecyclerView = rootView.findViewById(R.id.trending_recyclerview);
        swipeContainer       = rootView.findViewById(R.id.swipeContainer);
        progressBar          = rootView.findViewById(R.id.progress_id);


        //if the fragment hasn't been loaded Before
        if(!isLoaded){
            mAdapter = new TrendsAdapter(getContext()); //Create an empty Adapter
            loadFirstPage();
            progressBar.setVisibility(View.VISIBLE);
        }

        //Setting the Layout Manager
        mLayoutManager = getLayoutManager();
        trendingRecyclerView.setLayoutManager(mLayoutManager);

        //Setting the RecyclerView Animator
        trendingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Setting the Adapter
        trendingRecyclerView.setAdapter(mAdapter);


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
                }, 500);
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirstPage();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return rootView;
    }
    private void loadFirstPage() {
        RetrofitManager
                .getInstance(getContext())
                .getRepositoriesInfos("created:>"+getDateMinusMonth(), 1, new Callback<GitHubTredingsCallBack>() {
            @Override
            public void onResponse(Call<GitHubTredingsCallBack> call, Response<GitHubTredingsCallBack> response) {
                reloadAdapter(response.body().getItems());
                isLoaded = true;
                pageToLoad=2;
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GitHubTredingsCallBack> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                ToastMessages.showInternetErrorToast(getContext());
            }
        });
    }
    private void loadNextPage(){
        RetrofitManager
                .getInstance(getContext())
                .getRepositoriesInfos("created:>"+getDateMinusMonth(), pageToLoad, new Callback<GitHubTredingsCallBack>() {
            @Override
            public void onResponse(Call<GitHubTredingsCallBack> call, Response<GitHubTredingsCallBack> response) {
                mAdapter.addAll(response.body().getItems());
                isLoading = false;
                mAdapter.addLoadingFooter();
                pageToLoad++;
            }

            @Override
            public void onFailure(Call<GitHubTredingsCallBack> call, Throwable t) {
                ToastMessages.showInternetErrorToast(getContext());
            }
        });
    }

    private String getDateMinusMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(result);

    }
    private void reloadAdapter(List<Item> items){
        mAdapter.clear();
        mAdapter.addAll(items);
        mAdapter.addLoadingFooter();
    }
    private LinearLayoutManager getLayoutManager(){
        int viewType = SaveSharedPreference.getViewMode(getContext());
        switch(viewType){
            case AppConstants.VERTICAL_LINEAR_VIEW:
                return new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            case AppConstants.GRID_TWO_ELEMENT_ROW_VIEW:
                return new GridLayoutManager(getContext(),2);
            case AppConstants.GRID_THREE_ELEMENT_ROW_VIEW:
                return new GridLayoutManager(getContext(),3);
            default:
                return new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        }
    }
}
