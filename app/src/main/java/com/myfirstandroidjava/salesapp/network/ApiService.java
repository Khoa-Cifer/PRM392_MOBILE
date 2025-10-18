package com.myfirstandroidjava.salesapp.network;

import com.myfirstandroidjava.salesapp.model.LoginRequest;
import com.myfirstandroidjava.salesapp.model.LoginResponse;
import com.myfirstandroidjava.salesapp.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("Auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("Auth/register")
    Call<LoginResponse> registerUser(@Body RegisterRequest registerRequest); // Assuming register also returns a token in LoginResponse
}