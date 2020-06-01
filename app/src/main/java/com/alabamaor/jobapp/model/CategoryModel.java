package com.alabamaor.jobapp.model;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("id")
    double id;
    @SerializedName("name")
    String name;



    @SerializedName("slug")
    String slug;

    public CategoryModel(double id, String name) {
        this.id = id;
        this.name = name;
    }

    public double getId() {
        return id;
    }

    public CategoryModel setId(double id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryModel setName(String name) {
        this.name = name;
        return this;
    }
    public String getSlug() {
        return slug;
    }

    public CategoryModel setSlug(String slug) {
        this.slug = slug;
        return this;
    }
}
