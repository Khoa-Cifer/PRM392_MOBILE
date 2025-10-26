package com.myfirstandroidjava.salesapp.models;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.myfirstandroidjava.salesapp.network.ProductAPIService;
import com.myfirstandroidjava.salesapp.R;
import com.myfirstandroidjava.salesapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView textName, textDescription, textPrice, textSpecs;
    private ImageView imageProduct;
    private ProgressBar progressBar;

    private ProductAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        textName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);
        textPrice = findViewById(R.id.textPrice);
        textSpecs = findViewById(R.id.textSpecs);
        imageProduct = findViewById(R.id.imageProduct);
        progressBar = findViewById(R.id.progressBar);

        apiService = RetrofitClient.getClient().create(ProductAPIService.class);

        int productId = getIntent().getIntExtra("productId", -1);
        if (productId != -1) {
            loadProductDetail(productId);
        } else {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadProductDetail(int productId) {
        progressBar.setVisibility(View.VISIBLE);

        apiService.getProductDetail(productId).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailResponse product = response.body();
                    textName.setText(product.getProductName());
                    textDescription.setText(product.getFullDescription());
                    textPrice.setText("$" + product.getPrice());
                    textSpecs.setText(product.getTechnicalSpecifications());

                    Glide.with(ProductDetailActivity.this)
                            .load("http://192.168.1.235:7002" + product.getImageUrl())
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.image_error)
                            .into(imageProduct);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Failed to load product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("DETAIL_ERROR", "Error: " + t.getMessage());
                Toast.makeText(ProductDetailActivity.this, "Error loading detail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
