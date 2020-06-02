package com.alabamaor.jobapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {


    @SerializedName("jobs")
    List<CategoryModel> categoriesList;


    public Categories(List<CategoryModel> categoriesList ) {
        this.categoriesList = categoriesList;
    }

    public List<CategoryModel> getCategoriesList() {
        return categoriesList;
    }

    public Categories setCategoriesList(List<CategoryModel> categoriesList) {
        this.categoriesList = categoriesList;
        return this;
    }


}
