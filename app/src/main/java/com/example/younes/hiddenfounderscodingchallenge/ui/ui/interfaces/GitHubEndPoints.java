package com.example.younes.hiddenfounderscodingchallenge.ui.ui.interfaces;

import com.example.younes.hiddenfounderscodingchallenge.ui.models.GitHubTredingsCallBack;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by younes on 11/10/2018.
 */

public interface GitHubEndPoints {
    @GET("repositories?sort=stars&order=desc")
    Call<GitHubTredingsCallBack> getUserInfo(@Query("q") String date);
}
