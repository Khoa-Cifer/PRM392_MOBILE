package com.myfirstandroidjava.salesapp.network;

import com.myfirstandroidjava.salesapp.models.LoginRequest;
import com.myfirstandroidjava.salesapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CartAPIService {
    @POST("Auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
