package com.myfirstandroidjava.salesapp.network;

import com.myfirstandroidjava.salesapp.models.StoreLocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreAPIService {
    @GET("StoreLocation")
    Call<StoreLocationResponse> getStoreLocation();
}