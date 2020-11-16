package com.example.clothesonthego;

/**
 * A class representing an individual product
 */
public class Product {
    private final String id;
    private final String type;
    private final String name;
    private int quantity;
    private final String photoUrl;
    private final float price;
    // TODO: Implement description

    /**
     * A constructor for the Product
     * @param id The product ID
     * @param type The product category
     * @param name The product name
     * @param quantity The quantity of the product available
     * @param photoUrl The URL to the product photo
     * @param price The product price
     */
    public Product(String id, String type, String name, int quantity, String photoUrl, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.photoUrl = photoUrl;
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
     * Get the product category/typ e
     * @return The product category string
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the product name
     * @return The product name string
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the product quantity
     * @return The product quantity
     */
    public int getQuantity() {
        return this.quantity;
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
    public float getPrice() {
        return this.price;
    }

    /**
     * Set the product quantity
     * @param q The new quantity to set
     */
    public void setQuantity (int q) {
        this.quantity = q;
    }
}
