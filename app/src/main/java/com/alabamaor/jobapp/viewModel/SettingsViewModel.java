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

    public MutableLiveData<Boolean> mIsFilterCategories = new MutableLiveData<>();
    public MutableLiveData<Integer> mPosition = new MutableLiveData<>();
    public MutableLiveData<List<CategoryModel>> mCategoriesList = new MutableLiveData<>();


    private JobsService mJobsService = JobsService.getInstance();
    private CompositeDisposable mDisposable = new CompositeDisposable();


    public void init() {
        fetchCategories();
    }


    public ArrayList getCategoryList() {
        ArrayList<String> arr = new ArrayList<>();

        for (CategoryModel c : mCategoriesList.getValue()) {
            arr.add(c.getName());
        }
        return arr;
    }


    private void fetchCategories() {

        mDisposable.add(

                mJobsService.getCategories()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Categories>() {
                            @Override
                            public void onSuccess(Categories categories) {
                                mCategoriesList.setValue(categories.getCategoriesList());
                                mIsFilterCategories.setValue(true);

                            }

                            @Override
                            public void onError(Throwable e) {
                                mCategoriesList.setValue(null);
                                mIsFilterCategories.setValue(false);
                            }
                        })

        );
    }
}