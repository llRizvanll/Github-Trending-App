package com.android.app.mvvm.data;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

@Database(entities = {Item.class},version = 1,exportSchema = false)
public abstract class Db extends RoomDatabase {
    public static final String DB_NAME = "MVVM.db";

    public abstract Dao dao();

    @androidx.room.Dao
    interface Dao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void InsertItem(Item item);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateItem(Item item);

        @Query("SELECT DISTINCT * from Item")
        List<Item> getTrendingRepos();


    }
}
