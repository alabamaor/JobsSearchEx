package com.alabamaor.jobapp.dataSource;

import com.alabamaor.jobapp.model.Categories;
import com.alabamaor.jobapp.model.Jobs;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JobsApi {

    @GET("remote-jobs")
    Single<Jobs> getJobs();

    @GET("")
    Single<Jobs> getFilterJobs(@Url String url);

    @GET("remote-jobs/categories")
    Single<Categories> getCategories();
}