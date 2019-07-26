package com.android.app.mvvm.data;

import rx.Observable;

public interface DataSource {

    Observable<Item> loadTrendingRepos();
    Observable<Item> loadTrendingDevs();
}
