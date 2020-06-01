package com.alabamaor.jobapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.jobapp.dataSource.JobsService;
import com.alabamaor.jobapp.model.Categories;
import com.alabamaor.jobapp.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SettingsViewModel extends ViewModel {

    public MutableLiveData<Boolean> isFilterCategories = new MutableLiveData<>();
    public MutableLiveData<Integer> position = new MutableLiveData<>();
    public MutableLiveData<List<CategoryModel>> categoriesList = new MutableLiveData<>();


    private JobsService jobsService = JobsService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();


    public void init() {
        fetchCategories();
    }


    public ArrayList getCategoryList() {
        ArrayList<String> arr = new ArrayList<>();

        for (CategoryModel c : categoriesList.getValue()) {
            arr.add(c.getName());
        }
        return arr;
    }


    private void fetchCategories() {

        disposable.add(

                jobsService.getCategories()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Categories>() {
                            @Override
                            public void onSuccess(Categories categories) {
                                categoriesList.setValue(categories.getCategories());
                                isFilterCategories.setValue(true);

                            }

                            @Override
                            public void onError(Throwable e) {
                                categoriesList.setValue(null);
                                isFilterCategories.setValue(false);
                            }
                        })

        );
    }
}