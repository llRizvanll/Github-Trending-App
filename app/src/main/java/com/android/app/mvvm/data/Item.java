package com.android.app.mvvm.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.android.app.mvvm.models.TrendingDevsModel;
import com.android.app.mvvm.models.TrendingRepoModel;

@Entity(tableName = "Item",indices = @Index(value = {"item_repo_name"}, unique = true))
public class Item {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "item_repo_name")
    public String itemRepoName;
    public String itemImageUrl;
    public String itemDevName;
    public String language;

    public Item(TrendingRepoModel trendingRepoModel) {
        this.itemRepoName = trendingRepoModel.getName();
        this.itemImageUrl = trendingRepoModel.getAvatar();
        this.itemDevName = trendingRepoModel.getAuthor();
        this.language  = trendingRepoModel.getLanguage();
    }

    public Item(TrendingDevsModel trendingDevsModel) {
        this.itemRepoName = trendingDevsModel.getRepo().getName();
        this.itemImageUrl = trendingDevsModel.getAvatar();
        this.itemDevName = trendingDevsModel.getUsername();
    }

    public Item() {
    }

    @NonNull
    public String getItemRepoName() {
        return itemRepoName;
    }

    public void setItemRepoName(@NonNull String itemRepoName) {
        this.itemRepoName = itemRepoName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemDevName() {
        return itemDevName;
    }

    public void setItemDevName(String itemDevName) {
        this.itemDevName = itemDevName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
