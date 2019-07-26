package com.android.app.mvvm.data;

import androidx.lifecycle.MutableLiveData;

import rx.Observable;

public interface DataSource {

    Observable<Item> loadTrendingRepos();
    Observable<Item> loadTrendingDevs();
}
