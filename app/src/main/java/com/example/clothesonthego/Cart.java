package com.example.clothesonthego;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the user's cart
 */
public class Cart {
    private HashMap<String, Integer> products;
    private String destination;
    private final SharedPreferences.Editor cartEditor;
    private final String destination_key;

    /**
     * A constructor for the cart, which will read any previous cart contents from SharedPreferences
     * @param context The context provided by the CartActivity
     */
    public Cart(Context context) {
        SharedPreferences cart = context.getSharedPreferences(context.getString(R.string.cart_file),
                Context.MODE_PRIVATE);
        cartEditor = cart.edit();
        destination_key = context.getString(R.string.destination_key);

        // Read all contents from cart
        for (Map.Entry<String,?> cartEntry : cart.getAll().entrySet()) {
            if (cartEntry.getKey().equals(destination_key)) {
                destination = (String) cartEntry.getValue();
            }
            else {
                products.put(cartEntry.getKey(), (Integer) cartEntry.getValue());
            }
        }
    }

    /**
     * Set the destination for the user's order
     * @param destination The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
        cartEditor.putString(destination_key, destination);
        cartEditor.apply();
    }

    /**
     * Get the destination for the user's order
     * @return The destination
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Get the products in the cart
     * @return A HashMap of Product IDs and their corresponding quantities
     */
    public HashMap<String, Integer> getProducts() {
        return this.products;
    }

    /**
     * Add an item to the cart
     * @param productId The product ID for the item
     * @param quantity The quantity to be added
     */
    public void addToCart(String productId, int quantity) {
        // If the item is already in the cart, we increment its quantity in the cart
        if (products.containsKey(productId)) {
            int currentValue = products.get(productId);
            products.put(productId, currentValue + quantity);
            cartEditor.putInt(productId, currentValue + quantity);
        }
        // If the item is not in the cart, add it
        else {
            products.put(productId, quantity);
            cartEditor.putInt(productId, quantity);
        }
        // Apply changes to the SharedPreferences
        cartEditor.apply();
    }

    /**
     * Modifies the quantity of a product in the cart
     * @param productId The product ID for the item
     * @param quantity The new quantity
     */
    public void modifyQuantity(String productId, int quantity) {
        products.put(productId, quantity);
        cartEditor.putInt(productId, quantity);
        cartEditor.apply();
    }

    /**
     * Removes a product from the cart
     * @param productId The ID of the product to remove
     */
    public void removeFromCart(String productId) {
        products.remove(productId);
        cartEditor.remove(productId);
        cartEditor.apply();
    }
}
