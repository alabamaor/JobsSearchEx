package com.alabamaor.jobapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {


    @SerializedName("jobs")
    List<CategoryModel> categories;

    @SerializedName("job-count")
    String count;

    public Categories(List<CategoryModel> categories, String count) {
        this.categories = categories;
        this.count = count;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public Categories setCategories(List<CategoryModel> categories) {
        this.categories = categories;
        return this;
    }

    public String getCount() {
        return count;
    }

    public Categories setCount(String count) {
        this.count = count;
        return this;
    }
}
