
package com.example.projectweekly.Model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

@SuppressWarnings("unused")
public class CreateUserResponseModel {

    @SerializedName("createdAt")
    private Timestamp mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("job")
    private String mJob;
    @SerializedName("name")
    private String mName;

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

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

}
