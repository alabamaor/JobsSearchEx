package com.alabamaor.jobapp.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.jobapp.dataSource.JobsService;
import com.alabamaor.jobapp.model.Jobs;
import com.alabamaor.jobapp.model.SingleJobModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<List<SingleJobModel>> mJobsList = new MutableLiveData<>();

    private JobsService jobsService = JobsService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void init() {

        Log.i("ALABAMA->", "init");
        getJobs();
    }

    private void getJobs() {

        disposable.add(

                jobsService.getAllJobs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Jobs>() {
                            @Override
                            public void onSuccess(Jobs jobs) {
                                mJobsList.setValue(jobs.getJobsList());
                            }

                            @Override
                            public void onError(Throwable e) {
                                mJobsList.setValue(null);
                            }
                        })

        );

    }

    public LiveData<List<SingleJobModel>> getJobList() {
        return mJobsList;
    }

}
