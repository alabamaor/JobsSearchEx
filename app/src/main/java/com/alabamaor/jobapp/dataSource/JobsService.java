package com.alabamaor.jobapp.dataSource;


import com.alabamaor.jobapp.model.Jobs;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobsService {

    private static final String BASE_URL = "https://remotive.io/api/";

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


}
