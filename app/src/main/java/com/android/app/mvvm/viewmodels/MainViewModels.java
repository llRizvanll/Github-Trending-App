package com.android.app.mvvm.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.app.mvvm.data.DataRepository;
import com.android.app.mvvm.data.Item;
import rx.Observable;

public class MainViewModels extends ViewModel {

    MutableLiveData<Boolean> showLoader = new MutableLiveData<>();

    private DataRepository dataRepository = null;

    public void init(){
        dataRepository = DataRepository.getInstance();
        dataRepository.loadTrendingRepos();
    }

    public final ObservableField<Boolean> imagesVisible = new ObservableField<>(true);

    public void toggleImageVisibility() {
        imagesVisible.set(imagesVisible.get());
    }

    public Observable<Item> getTrendingReposList() {
        return dataRepository.loadTrendingRepos();
    }

    public MutableLiveData<Boolean> getShowLoader() {
        return showLoader;
    }

}
