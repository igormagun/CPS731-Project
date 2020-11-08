package com.example.clothesonthego;

import java.util.ArrayList;

public class ProductCategory {
    private final String name;
    ArrayList<Product> products = new ArrayList<Product>();

    public ProductCategory (String name, ArrayList<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }
}
