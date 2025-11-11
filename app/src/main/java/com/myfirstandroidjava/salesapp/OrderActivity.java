package com.myfirstandroidjava.salesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.myfirstandroidjava.salesapp.adapters.OrderAdapter;
import com.myfirstandroidjava.salesapp.models.CartItem;
import com.myfirstandroidjava.salesapp.models.PayPalCreateRequest;
import com.myfirstandroidjava.salesapp.models.PayPalResponse;
import com.myfirstandroidjava.salesapp.network.PayPalAPIService;
import com.myfirstandroidjava.salesapp.network.RetrofitClient;
import com.myfirstandroidjava.salesapp.utils.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    private TextView tvOrderTotal;
    private Button btnPayment;
    private ArrayList<CartItem> orderItems;
    private double total;
    private PayPalAPIService payPalAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();
        payPalAPIService = RetrofitClient.getClient(this, token).create(PayPalAPIService.class);

        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        btnPayment = findViewById(R.id.btnPayment);

        orderItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cartItems");
        total = getIntent().getDoubleExtra("totalPrice", 0.0);

        tvOrderTotal.setText(String.format("Total: $%.2f", total));

        // Setup RecyclerView
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(orderItems);
        orderRecyclerView.setAdapter(adapter);
        btnPayment.setOnClickListener(v -> startPayPalPayment(9.99));
//        btnPayment.setOnClickListener(v -> {
//            // Later, this will trigger the payment process.
//            Toast.makeText(OrderActivity.this, "Payment functionality to be implemented.", Toast.LENGTH_SHORT).show();
//        });
    }

    private void startPayPalPayment(double amount) {
        payPalAPIService.createOrder(new PayPalCreateRequest(amount)).enqueue(new Callback<PayPalResponse>() {
            @Override
            public void onResponse(Call<PayPalResponse> call, Response<PayPalResponse> response) {
                if (response.isSuccessful()) {
                    String approvalUrl = response.body().approvalUrl;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(approvalUrl));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(OrderActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PayPalResponse> call, Throwable t) {
                Log.e("PayPal", "Error: " + t.getMessage());
            }
        });
    }
}
