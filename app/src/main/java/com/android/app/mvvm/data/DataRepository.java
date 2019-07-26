package com.android.app.mvvm.data;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.android.app.mvvm.App;
import com.android.app.mvvm.NetworkUtils;
import com.android.app.mvvm.RetrofitInstance;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataRepository implements DataSource {
    private static DataRepository instance = null;
    private DataRepository() {}

    public static DataRepository getInstance() {
        if (instance == null){
            synchronized (DataRepository.class){
                if (instance == null)
                    instance = new DataRepository();
            }
        }
        return instance;
    }

    public void getTrendingRepos(){
        MutableLiveData<Observable<Item>> trendingRepoModelList = new MutableLiveData<>();
        trendingRepoModelList.setValue(loadTrendingRepos());
    }

    public Observable<Item> loadTrendingRepos(){
        if (NetworkUtils.isConnected(App.INSTANCE))
            loadRepoList().subscribe(this::onResponse,this::onFailure);

        return LocalDataSource.INSTANCE.loadTrendingRepos();
    }

    private void onResponse(Item item) {
        LocalDataSource.INSTANCE.insert(item);
    }

    private void onFailure(Throwable t) {
        Log.e("Network error: ", t.getMessage());
    }

    public Observable<Item> loadTrendingDevs(){
        return RetrofitInstance.getInstance().provideClient().getTrendingDevs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .map(Item::new);
    }

    public Observable<Item> loadRepoList(){
        return RetrofitInstance.getInstance().provideClient().getTrendingDevs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .map(Item::new);
    }
}
