package com.alabamaor.jobapp.dataSource;


import com.alabamaor.jobapp.model.Categories;
import com.alabamaor.jobapp.model.Jobs;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobsService {

    private static final String BASE_URL = "https://remotive.io/api/";

    private static final String FILTER_URL = "remote-jobs?";

    ///  https://remotive.io/api/remote-jobs?category=software-dev&search=front%20end

    private static JobsService instance;

    private JobsApi api = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(JobsApi.class);

    private JobsService() {

    }

    public static JobsService getInstance() {
        if (instance == null)
            instance = new JobsService();

        return instance;
    }

    public Single<Jobs> getAllJobs() {
        return api.getJobs();
    }


    public Single<Jobs> getFilterJobs(String filter) {
        String url = BASE_URL + FILTER_URL + "category="+filter;

        return api.getFilterJobs(url);
    }

    public Single<Categories> getCategories() {
        return api.getCategories();
    }


}
