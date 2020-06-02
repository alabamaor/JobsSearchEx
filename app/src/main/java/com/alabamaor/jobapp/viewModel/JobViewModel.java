package com.alabamaor.jobapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alabamaor.jobapp.model.SingleJobModel;

public class JobViewModel extends ViewModel {

    public MutableLiveData<SingleJobModel> mSelectedJob = new MutableLiveData<>();

}