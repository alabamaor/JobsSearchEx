package com.alabamaor.jobapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Jobs {


    @SerializedName("job-count")
    String count;

    @SerializedName("jobs")
    List<SingleJobModel> jobsList;


    public Jobs(String count, List<SingleJobModel> jobsList) {
        this.count = count;
        this.jobsList = jobsList;
    }

    public String getCount() {
        return count;
    }

    public Jobs setCount(String count) {
        this.count = count;
        return this;
    }

    public List<SingleJobModel> getJobsList() {
        return jobsList;
    }

    public Jobs setJobsList(List<SingleJobModel> jobsList) {
        this.jobsList = jobsList;
        return this;
    }
}
