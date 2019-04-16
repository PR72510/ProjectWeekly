
package com.example.projectweekly.Model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {

    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("id")
    private int mId;
    @SerializedName("last_name")
    private String mLastName;

    public Datum(String mAvatar, String mFirstName, int mId, String mLastName) {
        this.mAvatar = mAvatar;
        this.mFirstName = mFirstName;
        this.mId = mId;
        this.mLastName = mLastName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

}
