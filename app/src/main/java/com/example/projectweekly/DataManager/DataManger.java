package com.example.projectweekly.DataManager;

import android.util.Log;
import android.widget.Toast;

import com.example.projectweekly.APIs.JsonAPI;
import com.example.projectweekly.APIs.RetrofitInstance;
import com.example.projectweekly.Interfaces.LoginRegisterResponseListener;
import com.example.projectweekly.Interfaces.RetrofitResponseListener;
import com.example.projectweekly.Model.CreateUserResponseModel;
import com.example.projectweekly.Model.Datum;
import com.example.projectweekly.Model.NewUser;
import com.example.projectweekly.Model.TokenResponseModel;
import com.example.projectweekly.Model.UpdateUserModel;
import com.example.projectweekly.Model.UserCredentialModel;
import com.example.projectweekly.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataManger {
    static int page = 1;
    RetrofitResponseListener retrofitResponseListener;
    LoginRegisterResponseListener loginRegisterResponseListener;
    private static final String TAG = "DataManger";

    private List<Datum> userList;

    Retrofit retrofit = RetrofitInstance.getInstance();
    private static final DataManger ourInstance = new DataManger();

    public static DataManger getInstance() {
        return ourInstance;
    }

    JsonAPI api = retrofit.create(JsonAPI.class);

    private DataManger() {
    }

    public void setInstance(RetrofitResponseListener listener) {
        retrofitResponseListener = listener;
    }

    public void setInstance(LoginRegisterResponseListener listener) {
        loginRegisterResponseListener = listener;
    }


    public void getList(int page) {
        Call<Users> call = api.listAllUser(page);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users users = response.body();
                if (users != null) {
                    userList = users.getData();
                    retrofitResponseListener.responseListener(userList);
                    Log.d(TAG, "onResponse: " + userList.get(1).getId());
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.v("sagd", "sjdhg");
            }
        });
    }

    public void registerUser(UserCredentialModel userCredentialModel) {
        Call<TokenResponseModel> call = api.registerUser(userCredentialModel);

        call.enqueue(new Callback<TokenResponseModel>() {
            @Override
            public void onResponse(Call<TokenResponseModel> call, Response<TokenResponseModel> response) {
                TokenResponseModel token = response.body();

                if (response.isSuccessful()) {
                    Log.d("tag", token.getToken());
                    loginRegisterResponseListener.tokenRegisterResonseListener(token);
                }
            }

            @Override
            public void onFailure(Call<TokenResponseModel> call, Throwable t) {

            }
        });
    }

    public void loginUser(UserCredentialModel user) {
        Call<TokenResponseModel> call = api.loginUser(user);

        call.enqueue(new Callback<TokenResponseModel>() {
            @Override
            public void onResponse(Call<TokenResponseModel> call, Response<TokenResponseModel> response) {
                TokenResponseModel token = response.body();
                if (response.isSuccessful()) {
                    loginRegisterResponseListener.tokenLoginResonseListener(token);
                }
            }

            @Override
            public void onFailure(Call<TokenResponseModel> call, Throwable t) {

            }
        });
    }

    public void createUser(NewUser user){
        Call<CreateUserResponseModel> call = api.createUser(user);

        call.enqueue(new Callback<CreateUserResponseModel>() {
            @Override
            public void onResponse(Call<CreateUserResponseModel> call, Response<CreateUserResponseModel> response) {
                CreateUserResponseModel user = response.body();
//                Log.d(TAG, "onResponse: "+ user.getJob() +" " + user.getCreatedAt());
                retrofitResponseListener.createUserListener(user);
            }

            @Override
            public void onFailure(Call<CreateUserResponseModel> call, Throwable t) {

            }
        });
    }

    public void deleteUser(int userPosition){
        Call<Void> call = api.deleteUser(userPosition);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                retrofitResponseListener.deleteUserListener(response.code());
                Log.d(TAG, "onResponse: "+ response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void updateUser(NewUser user){
        Call<UpdateUserModel> call = api.updateUser(user);

        call.enqueue(new Callback<UpdateUserModel>() {
            @Override
            public void onResponse(Call<UpdateUserModel> call, Response<UpdateUserModel> response) {
                UpdateUserModel updatedUser = response.body();
                retrofitResponseListener.updateUserListener(updatedUser);
            }

            @Override
            public void onFailure(Call<UpdateUserModel> call, Throwable t) {

            }
        });
    }

}


