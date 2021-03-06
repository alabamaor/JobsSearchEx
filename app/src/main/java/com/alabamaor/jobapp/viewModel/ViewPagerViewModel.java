package com.alabamaor.jobapp.viewModel;

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

public class ViewPagerViewModel extends ViewModel {

    public MutableLiveData<List<SingleJobModel>> mJobsList = new MutableLiveData<>();
    public MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> mHasError = new MutableLiveData<>();
    public MutableLiveData<Integer> mPosition = new MutableLiveData<>();

    private JobsService jobsService = JobsService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void getJobs(String filter) {

        mIsLoading.setValue(true);
        disposable.add(

                jobsService.getFilterJobs(filter)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Jobs>() {
                            @Override
                            public void onSuccess(Jobs jobs) {
                                mJobsList.setValue(jobs.getJobsList());
                                mHasError.setValue(false);
                                mIsLoading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mJobsList.setValue(null);
                                mHasError.setValue(true);
                                mIsLoading.setValue(false);
                            }
                        })

        );

    }
}