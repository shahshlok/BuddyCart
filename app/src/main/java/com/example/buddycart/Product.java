package com.example.buddycart;

public class Product {
    private String name;
    private double price;
    private int imageResource;
    private String description;

    public Product(String name, double price, int imageResource, String description) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }
} 