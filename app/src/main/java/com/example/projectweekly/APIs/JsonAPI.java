package com.example.projectweekly.APIs;

import com.example.projectweekly.Model.CreateUserResponseModel;
import com.example.projectweekly.Model.NewUser;
import com.example.projectweekly.Model.TokenResponseModel;
import com.example.projectweekly.Model.UpdateUserModel;
import com.example.projectweekly.Model.UserCredentialModel;
import com.example.projectweekly.Model.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonAPI {

    @GET("api/users")
    Call<Users> listAllUser(@Query("page") Integer page);

    @POST("api/register")
    Call<TokenResponseModel> registerUser(@Body UserCredentialModel userCredentialModel);

    @POST("api/login")
    Call<TokenResponseModel> loginUser(@Body UserCredentialModel user);

    @POST("api/users")
    Call<CreateUserResponseModel> createUser(@Body NewUser user);

    @PUT("api/users/2")
    Call<UpdateUserModel> updateUser(@Body NewUser user);

    @DELETE("api/users/{user_id}")
    Call<Void> deleteUser(@Path("user_id") Integer userId);

}