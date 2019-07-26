package com.android.app.mvvm.data;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.android.app.mvvm.App;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public enum  LocalDataSource implements DataSource {
    INSTANCE;
    private Db.Dao dao;


    LocalDataSource() {
        Db db = Room.databaseBuilder(App.INSTANCE, Db.class, Db.DB_NAME).allowMainThreadQueries().build();
        dao = db.dao();
    }

    @Override
    public Observable<Item> loadTrendingRepos() {
//        return dao.getTrendingRepos().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(Observable::from);

//        Subscription subj = Observable.just(dao).subscribeOn(Schedulers.io())
//                .subscribe(dao1 -> dao1.getTrendingRepos());
        return Observable.just(dao.getTrendingRepos()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(Observable::from);
    }

    public void insert(Item repos){
        dao.InsertItem(repos);
    }


    @Override
    public Observable<Item> loadTrendingDevs() {
        return null;
    }

}
