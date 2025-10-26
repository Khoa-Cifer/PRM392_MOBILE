package com.myfirstandroidjava.salesapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfirstandroidjava.salesapp.adapters.ProductAdapter;
import com.myfirstandroidjava.salesapp.models.ProductItem;
import com.myfirstandroidjava.salesapp.models.ProductListResponse;
import com.myfirstandroidjava.salesapp.network.ProductAPIService;
import com.myfirstandroidjava.salesapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProductAdapter adapter;
    private ProductAPIService productAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAPIService = RetrofitClient.getClient().create(ProductAPIService.class);

        loadProducts();
    }

    private void loadProducts() {
        progressBar.setVisibility(View.VISIBLE);

        Call<ProductListResponse> call = productAPIService.getProducts(1, 10);
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                progressBar.setVisibility(View.GONE);
                Log.d("API_DEBUG", "Response code: " + response.code());
                Log.d("API_DEBUG", "Raw body: " + response.raw());
                Log.d("API_DEBUG", "Error body: " + (response.errorBody() != null ? response.errorBody().toString() : "null"));
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductItem> products = response.body().getItems();
                    adapter = new ProductAdapter(products);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(HomeActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}