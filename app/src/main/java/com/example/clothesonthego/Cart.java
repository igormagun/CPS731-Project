package com.example.clothesonthego;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the user's cart
 */
public class Cart {
    private final HashMap<String, Integer> products = new HashMap<>();
    private String destination = null;
    private static final String DEST_KEY = "destination";
    private final FirebaseAuth mAuth;
    private final DBController controller;

    /**
     * A constructor for the cart, which will read any existing cart contents from Firestore
     */
    public Cart() {
        mAuth = FirebaseAuth.getInstance();
        controller = new DBController();

        // Read all contents from cart
        for (Map.Entry<String,Object> cartEntry : controller.loadCart(mAuth.getUid()).get()
                .entrySet()) {
            if (cartEntry.getKey().equals(DEST_KEY)) {
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
        controller.setDestination(mAuth.getUid(), destination);
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
        }
        // If the item is not in the cart, add it
        else {
            products.put(productId, quantity);
        }
        controller.addToCart(mAuth.getUid(), productId, quantity);
    }

    /**
     * Modifies the quantity of a product in the cart
     * @param productId The product ID for the item
     * @param quantity The new quantity
     */
    public void modifyQuantity(String productId, int quantity) {
        products.put(productId, quantity);
        controller.modifyCartQuantity(mAuth.getUid(), productId, quantity);
    }

    /**
     * Removes a product from the cart
     * @param productId The ID of the product to remove
     */
    public void removeFromCart(String productId) {
        products.remove(productId);
        controller.removeFromCart(mAuth.getUid(), productId);
    }
}
