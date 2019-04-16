
package com.example.projectweekly.Model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

@SuppressWarnings("unused")
public class UpdateUserModel {

    @SerializedName("job")
    private String mJob;
    @SerializedName("name")
    private String mName;
    @SerializedName("updatedAt")
    private Timestamp mUpdatedAt;

    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Timestamp getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
