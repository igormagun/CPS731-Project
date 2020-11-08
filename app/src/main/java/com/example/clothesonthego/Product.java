package com.example.clothesonthego;

public class Product {
    private final int id;
    private ProductCategory type;
    private final String name;
    private int quantity;
    private final String photoUrl;
    private float price;

    public Product(int id, ProductCategory type, String name, int quantity, String photoUrl, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.photoUrl = photoUrl;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public ProductCategory getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getPhotoUrl () {
        return this.photoUrl;
    }

    public float getPrice() {
        return this.price;
    }

    public int setQuantitiy (int q) {
        this.quantity = q;
    }
}
