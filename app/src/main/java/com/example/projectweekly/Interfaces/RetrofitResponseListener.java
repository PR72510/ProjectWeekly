package com.example.projectweekly.Interfaces;

import com.example.projectweekly.Model.CreateUserResponseModel;
import com.example.projectweekly.Model.Datum;
import com.example.projectweekly.Model.UpdateUserModel;

import java.util.List;

public interface RetrofitResponseListener {

    void responseListener(List<Datum> list);

    void createUserListener(CreateUserResponseModel user);

    void deleteUserListener(int code);

    void updateUserListener(UpdateUserModel updateUser);

}
