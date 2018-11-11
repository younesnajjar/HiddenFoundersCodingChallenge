package com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.models.GitHubTredingsCallBack;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.adapters.TrendsAdapter;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.api.RetrofitManager;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.listners.ScrollListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by younes on 11/9/2018.
 */

public class TrendingFragment extends Fragment {

    private RecyclerView trendingRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private NestedScrollView mNestedScroll;
    private ScrollListener mActivityScrollListener;
    private boolean isLoaded = false;


    public TrendingFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_trending, container, false);
        trendingRecyclerView = rootView.findViewById(R.id.trending_recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        trendingRecyclerView.setLayoutManager(mLayoutManager);

        // Inflate the layout for this fragment
        if(!isLoaded)
            RetrofitManager.getInstance(getContext()).getRepositoriesInfos("created:>2017-10-22",new GitHubCallBack());
        else{
            trendingRecyclerView.setAdapter(mAdapter);
        }
        trendingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mActivityScrollListener.onScrollChange(dx,dy);
            }
        });

        return rootView;
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

    private class GitHubCallBack implements Callback<GitHubTredingsCallBack> {
        @Override
        public void onResponse(Call<GitHubTredingsCallBack> call, Response<GitHubTredingsCallBack> response) {
            mAdapter = new TrendsAdapter(response.body().getItems());
            trendingRecyclerView.setAdapter(mAdapter);
            isLoaded = true;
        }

        @Override
        public void onFailure(Call<GitHubTredingsCallBack> call, Throwable t) {

        }
    }
}
