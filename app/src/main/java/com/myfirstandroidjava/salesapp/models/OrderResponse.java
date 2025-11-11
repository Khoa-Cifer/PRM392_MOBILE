package com.myfirstandroidjava.salesapp.models;

import java.io.Serializable;
import java.util.List;

public class OrderResponse implements Serializable {
    private int orderId;
    private int cartId;
    private int userId;
    private String paymentMethod;
    private String billingAddress;
    private String orderStatus;
    private String orderDate;
    private Cart cart;

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    // Nested Cart class
    public static class Cart implements Serializable {
        private int cartId;
        private int userId;
        private double totalPrice;
        private String status;
        private List<CartItem> cartItems;

        public int getCartId() { return cartId; }
        public void setCartId(int cartId) { this.cartId = cartId; }

        public int getUserId() { return userId; }
        public void setUserId(int userId) { this.userId = userId; }

        public double getTotalPrice() { return totalPrice; }
        public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public List<CartItem> getCartItems() { return cartItems; }
        public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }
    }
}