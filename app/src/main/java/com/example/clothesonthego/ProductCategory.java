package com.example.clothesonthego;

import java.util.ArrayList;

/**
 * A class for a product category
 */
// TODO: Determine if this class is still necessary given the current app code
public class ProductCategory {
    private final String name;
    final ArrayList<Product> products;

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

    public void addProduct(Product product) {
        products.add(product);
    }
}
