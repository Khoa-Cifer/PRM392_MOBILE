package com.myfirstandroidjava.salesapp.network;

import com.myfirstandroidjava.salesapp.models.AddToCartRequest;
import com.myfirstandroidjava.salesapp.models.LoginRequest;
import com.myfirstandroidjava.salesapp.models.LoginResponse;
import com.myfirstandroidjava.salesapp.models.ProductDetailResponse;
import com.myfirstandroidjava.salesapp.models.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPIService {
    @GET("products")
    Call<ProductListResponse> getProducts(
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );

    @GET("products/{id}")
    Call<ProductDetailResponse> getProductDetail(@Path("id") int id);

    @POST("Cart/add-to-card")
    Call<String> addToCart(@Body AddToCartRequest addToCartRequest);
}
