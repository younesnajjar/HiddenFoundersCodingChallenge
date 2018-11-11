package com.example.younes.hiddenfounderscodingchallenge.ui.ui.api;

import android.content.Context;

import com.example.younes.hiddenfounderscodingchallenge.ui.models.GitHubTredingsCallBack;
import com.example.younes.hiddenfounderscodingchallenge.ui.ui.interfaces.GitHubEndPoints;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.AppConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by younes on 11/10/2018.
 */

public class RetrofitManager {
    private static RetrofitManager mInstance;
    private Context mContextFragment;
    private Context mContext;

    private RetrofitManager() {
    }

//    public static RetrofitManager getInstance(Context context) {
//        RetrofitManager instance = mInstance != null ? mInstance : (mInstance = new RetrofitManager());
//        if (context != null)
//            mInstance.mContextFragment = context;
//        return instance;
//    }

    public static RetrofitManager getInstance(Context context) {
        RetrofitManager instance = mInstance != null ? mInstance : (mInstance = new RetrofitManager());
        if (context != null)
            mInstance.mContext = context;
        return instance;
    }

    private Retrofit getRetrofit(String baseURL) {
        return new Retrofit.Builder()
                //.client(okHttpClient)
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
    }

    private GitHubEndPoints getAPIService() {
        return getRetrofit(AppConstants.URL_ENDPOINT_PRIMARY).create(GitHubEndPoints.class);
    }

    public Call<GitHubTredingsCallBack> getRepositoriesInfos(String date, Callback<GitHubTredingsCallBack> callback){
        Call<GitHubTredingsCallBack> call = getAPIService().getUserInfo(date);
        call.enqueue(callback);
        return call;
    }


    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder();

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }).build();
}
