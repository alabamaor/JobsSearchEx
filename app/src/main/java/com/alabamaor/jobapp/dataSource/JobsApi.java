package com.alabamaor.jobapp.dataSource;

import com.alabamaor.jobapp.model.Jobs;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface JobsApi {

    @GET("remote-jobs")
    Single<Jobs> getJobs();


}