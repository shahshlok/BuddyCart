package com.example.buddycart;

public class CartItem {
    private String name;
    private String price;
    private int quantity;

    public CartItem(String name, String price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
} 