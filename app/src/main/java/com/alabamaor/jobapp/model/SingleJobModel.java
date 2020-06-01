package com.alabamaor.jobapp.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SingleJobModel implements Parcelable {

    /*
          https://remotive.io/api-documentation

          "id": 123, // Unique Remotive ID
          "url": "https://remotive.io/remote-jobs/product/lead-developer-123", // Job listing detail url
          "title": "Lead Developer", // Job title
          "company_name": "Remotive", // Name of the company which is hiring
          "category": "Software Development", // See https://remotive.io/api/remote-jobs/categories for existing categories
          "tags": ["python", "back end"], // list of tags. See https://remotive.io/api/remote-jobs/tags for existing tags
          "job_type": "full_time",  // "full_time" or "contract" here
          "publication_date": "2020-02-15T10:23:26", // Publication date and time on https://remotive.io
          "candidate_required_location": "Worldwide", // Geographical restriction for the remote candidate, if any.
          "salary": "$40,000 - $50,000", // salary description, usually a yearly salary range, in USD.
          "description": "The full HTML job description here", // HTML full description of the job listing

    */

    public static final Creator<SingleJobModel> CREATOR = new Creator<SingleJobModel>() {
        @Override
        public SingleJobModel createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public SingleJobModel[] newArray(int size) {
            return new SingleJobModel[0];
        }
    };

    @SerializedName("id")
    double id;
    @SerializedName("url")
    String url;
    @SerializedName("title")
    String title;
    @SerializedName("company_name")
    String company_name;
    @SerializedName("category")
    String category;

    @SerializedName("job_type")
    String job_type;
    @SerializedName("publication_date")
    String publication_date;
    @SerializedName("candidate_required_location")
    String candidate_required_location;
    @SerializedName("salary")
    String salary;
    @SerializedName("description")
    String description;

    public SingleJobModel(double id, String url, String title, String company_name, String category, String job_type, String publication_date, String candidate_required_location, String salary, String description) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.company_name = company_name;
        this.category = category;
        this.job_type = job_type;
        this.publication_date = publication_date;
        this.candidate_required_location = candidate_required_location;
        this.salary = salary;
        this.description = description;
    }

    protected SingleJobModel(Parcel in) {
        id = in.readDouble();
        url = in.readString();
        title = in.readString();
        company_name = in.readString();
        category = in.readString();
        job_type = in.readString();
        publication_date = in.readString();
        candidate_required_location = in.readString();
        salary = in.readString();
        description = in.readString();
    }


    public double getId() {
        return id;
    }

    public SingleJobModel setId(double id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SingleJobModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SingleJobModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCompany_name() {
        return company_name;
    }

    public SingleJobModel setCompany_name(String company_name) {
        this.company_name = company_name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public SingleJobModel setCategory(String category) {
        this.category = category;
        return this;
    }


    public String getJob_type() {
        return job_type;
    }

    public SingleJobModel setJob_type(String job_type) {
        this.job_type = job_type;
        return this;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public SingleJobModel setPublication_date(String publication_date) {
        this.publication_date = publication_date;
        return this;
    }

    public String getCandidate_required_location() {
        return candidate_required_location;
    }

    public SingleJobModel setCandidate_required_location(String candidate_required_location) {
        this.candidate_required_location = candidate_required_location;
        return this;
    }

    public String getSalary() {
        return salary;
    }

    public SingleJobModel setSalary(String salary) {
        this.salary = salary;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SingleJobModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(id);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(company_name);
        dest.writeString(category);
        dest.writeString(job_type);
        dest.writeString(publication_date);
        dest.writeString(candidate_required_location);
        dest.writeString(salary);
        dest.writeString(description);
    }
}
