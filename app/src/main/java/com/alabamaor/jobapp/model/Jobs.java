package com.alabamaor.jobapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Jobs {

    @SerializedName("jobs")
    List<SingleJobModel> jobsList;


    public Jobs(List<SingleJobModel> jobsList) {
        this.jobsList = jobsList;
    }

    public List<SingleJobModel> getJobsList() {
        return jobsList;
    }

    public Jobs setJobsList(List<SingleJobModel> jobsList) {
        this.jobsList = jobsList;
        return this;
    }
}
