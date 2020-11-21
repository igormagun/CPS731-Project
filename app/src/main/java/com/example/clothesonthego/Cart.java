package com.example.clothesonthego;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A class representing the user's cart
 */
public class Cart {
    private final HashMap<String, Long> products = new HashMap<>();
    private String destination = null;
    private static final String DEST_KEY = "destination";
    private final FirebaseAuth mAuth;
    private final DBController controller;
    private final CartActivity activity;

    /**
     * A constructor for the cart, which will read any existing cart contents from Firestore
     */
    public Cart(CartActivity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        controller = new DBController();

        // Read all contents from cart - the DBController will call setupCart() once done
        controller.loadCart(this, mAuth.getUid());
    }

    /**
     * Set up the cart using the retrieved cart document from Firestore
     * @param cart The cart document from Firestore
     */
    public void setupCart(AtomicReference<Map<String, Object>> cart) {
        Map<String, Object> cartMap = cart.get();

        for (String key : cartMap.keySet()) {
            if (key.equals(DEST_KEY)) {
                destination = (String) cartMap.get(DEST_KEY);
            }
            else {
                products.put(key, (Long) cartMap.get(key));
            }
        }
        // Update the CartActivity now that everything is loaded
        activity.setCartContents();
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
    public HashMap<String, Long> getProducts() {
        return this.products;
    }

    /**
     * Add an item to the cart
     * @param productId The product ID for the item
     * @param quantity The quantity to be added
     */
    public void addToCart(String productId, long quantity) {
        // If the item is already in the cart, we increment its quantity in the cart
        if (products.containsKey(productId)) {
            long currentValue = products.get(productId);
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
    public void modifyQuantity(String productId, long quantity) {
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
