
package com.example.projectweekly.Model;

import com.google.gson.annotations.SerializedName;

public class TokenResponseModel {

    @SerializedName("token")
    private String mToken;

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

}
