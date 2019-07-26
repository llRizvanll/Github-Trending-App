package com.android.app.mvvm.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.app.mvvm.data.DataRepository;
import com.android.app.mvvm.data.Item;
import rx.Observable;

public class MainViewModels extends ViewModel {

    MutableLiveData<Observable<Item>> trendingRepoModelList = new MutableLiveData<>();
    private DataRepository dataRepository = null;

    public void init(){
        dataRepository = DataRepository.getInstance();
        dataRepository.getTrendingRepos();
    }

    public final ObservableField<Boolean> imagesVisible = new ObservableField<>(true);

    public void toggleImageVisibility() {
        imagesVisible.set(imagesVisible.get());
    }

    public MutableLiveData<Observable<Item>> getTrendingRepoModelList() {
        return trendingRepoModelList;
    }

    public Observable<Item> getTrendingReposList() {
        return dataRepository.loadTrendingRepos();
    }
}
