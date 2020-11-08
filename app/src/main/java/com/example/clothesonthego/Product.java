package com.example.clothesonthego;

public class Product {
    private final String id;
    private String type;
    private final String name;
    private int quantity;
    private final String photoUrl;
    private float price;

    public Product(String id, String type, String name, int quantity, String photoUrl, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.photoUrl = photoUrl;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
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

    public void setQuantity (int q) {
        this.quantity = q;
    }
}
