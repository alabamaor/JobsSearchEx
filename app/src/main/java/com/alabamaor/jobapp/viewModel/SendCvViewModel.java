package com.alabamaor.jobapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.jobapp.model.SingleJobModel;

public class SendCvViewModel extends ViewModel {

    public MutableLiveData<SingleJobModel> selectedJob = new MutableLiveData<>();
    public MutableLiveData<String> cvPath = new MutableLiveData<>();

}