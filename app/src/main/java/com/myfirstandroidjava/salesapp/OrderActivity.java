package com.myfirstandroidjava.salesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.myfirstandroidjava.salesapp.adapters.OrderAdapter;
import com.myfirstandroidjava.salesapp.models.CartItem;
import com.myfirstandroidjava.salesapp.models.OrderResponse;
import com.myfirstandroidjava.salesapp.models.PayPalCreateRequest;
import com.myfirstandroidjava.salesapp.models.PayPalResponse;
import com.myfirstandroidjava.salesapp.network.AuthAPIService;
import com.myfirstandroidjava.salesapp.network.PayPalAPIService;
import com.myfirstandroidjava.salesapp.network.RetrofitClient;
import com.myfirstandroidjava.salesapp.utils.OrderManager;
import com.myfirstandroidjava.salesapp.utils.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private TextView tvOrderId, tvOrderStatus, tvOrderDate, tvPaymentMethod, tvBillingAddress, tvTotalPrice;
    private LinearLayout llCartItems;
    private PayPalAPIService payPalAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        payPalAPIService = RetrofitClient.getClient(this, null).create(PayPalAPIService.class);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        tvBillingAddress = findViewById(R.id.tvBillingAddress);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        llCartItems = findViewById(R.id.llCartItems);
        Button btnPayNow = findViewById(R.id.btnPayNow);

        OrderResponse order = (OrderResponse) getIntent().getSerializableExtra("orderResponse");
        if (order != null) {
            displayOrder(order);

            btnPayNow.setOnClickListener(v -> {
                if (order.getCart() != null) {
                    double totalAmount = order.getCart().getTotalPrice();
                    startPayPalPayment(totalAmount);
                } else {
                    Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void displayOrder(OrderResponse order) {
        tvOrderId.setText("Order ID: " + order.getOrderId());
        tvOrderStatus.setText("Status: " + order.getOrderStatus());
        tvOrderDate.setText("Date: " + order.getOrderDate());
        tvPaymentMethod.setText("Payment: " + order.getPaymentMethod());
        tvBillingAddress.setText("Address: " + order.getBillingAddress());

        if (order.getCart() != null) {
            tvTotalPrice.setText("Total: $" + order.getCart().getTotalPrice());

            llCartItems.removeAllViews();
            for (CartItem item : order.getCart().getCartItems()) {
                TextView tvItem = new TextView(this);
                tvItem.setText(item.getProductName() + " x " + item.getQuantity() + " - $" + item.getTotalItemPrice());
                tvItem.setPadding(0, 8, 0, 8);
                llCartItems.addView(tvItem);
            }
        }
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
