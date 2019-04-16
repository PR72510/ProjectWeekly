package com.example.projectweekly.Interfaces;

import com.example.projectweekly.Model.TokenResponseModel;

public interface LoginRegisterResponseListener {
    void tokenRegisterResonseListener(TokenResponseModel token);
    void tokenLoginResonseListener(TokenResponseModel token);
}
