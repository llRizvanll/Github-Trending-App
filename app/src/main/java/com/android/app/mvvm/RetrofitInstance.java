package com.android.app.mvvm;

import androidx.lifecycle.MutableLiveData;

import com.android.app.mvvm.models.TrendingDevsModel;
import com.android.app.mvvm.models.TrendingRepoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public class RetrofitInstance {

    private static volatile RetrofitInstance instance = null;
    private GitApi client;


    private RetrofitInstance(){
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl("https://github-trending-api.now.sh")
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create()).build();
        client = retrofit.create(GitApi.class);
    }

    public GitApi provideClient(){ return client; }

    public static RetrofitInstance getInstance() {
        if (instance == null){
            synchronized (RetrofitInstance.class){
                if (instance == null)
                    instance = new RetrofitInstance();
            }
        }
        return instance;
    }

    public interface GitApi{
        @GET("/repositories?language=java")
        Observable<ArrayList<TrendingRepoModel>> getTrendingRepos();

        @GET("/developers?language=java")
        Observable<ArrayList<TrendingDevsModel>> getTrendingDevs();
    }
}
