package com.example.clothesonthego;

/**
 * A class representing an individual product
 */
public class Product {
    private final String id;
    private final String name;
    private final String photoUrl;
    private final String description;
    private final double price;

    /**
     * A constructor for the Product
     * @param id The product ID
     * @param name The product name
     * @param photoUrl The URL to the product photo
     * @param description The product description
     * @param price The product price
     */
    public Product(String id, String name, String photoUrl, String description,
                   double price) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.description = description;
        this.price = price;
    }

    /**
     * Get the product ID
     * @return The product ID string
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the product name
     * @return The product name string
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the product description
     * @return Product description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the URl for the product photo
     * @return The photo URL string
     */
    public String getPhotoUrl () {
        return this.photoUrl;
    }

    /**
     * Get the product price
     * @return The product price
     */
    public double getPrice() {
        return this.price;
    }
}
