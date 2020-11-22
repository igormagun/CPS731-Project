package com.example.clothesonthego;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A class representing the user's cart
 */
public class Cart {
    private final HashMap<String, Long> products = new HashMap<>();
    private ArrayList<Product> productDetails = new ArrayList<>();
    private ArrayList<Shipping> shippingList = new ArrayList<>();
    private String destination = null;
    private static final String DEST_KEY = "destination";
    private final FirebaseAuth mAuth;
    private final DBController controller;
    private final CartActivity activity;
    private final String userID;

    /**
     * A constructor for the cart, which will read any existing cart contents from Firestore
     */
    public Cart(CartActivity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        controller = new DBController();

        // Read all contents from cart - the DBController will call setupCart() once done
        controller.loadCart(this, mAuth.getUid());
    }

    /**
     * Set up the cart using the retrieved cart document from Firestore
     * @param cart The cart document from Firestore
     */
    public void setupCart(AtomicReference<Map<String, Object>> cart,
                          ArrayList<Product> products, ArrayList<Shipping> shippingList) {
        Map<String, Object> cartMap = cart.get();

        for (String key : cartMap.keySet()) {
            if (key.equals(DEST_KEY)) {
                destination = (String) cartMap.get(DEST_KEY);
            }
            else {
                this.products.put(key, (Long) cartMap.get(key));
            }
        }

        productDetails = products;
        this.shippingList = shippingList;
        // Update the CartActivity now that everything is loaded
        activity.setCartContents();
    }

    /**
     * Set the destination for the user's order
     * @param destination The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
        if (destination == null) {
            controller.removeDestination(userID);
        }
        else {
            controller.setDestination(userID, destination);
        }
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
     * Get the product details for the cart
     * @return A HashMap of Product IDs and their corresponding Product objects
     */
    public ArrayList<Product> getProductDetails() {
        return this.productDetails;
    }

    /**
     * Get the list of shipping destinations and costs
     * @return An ArrayList of Shipping objects
     */
    public ArrayList<Shipping> getShippingList() {
        return this.shippingList;
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
            // addToCart will overwrite the existing entry with the new quantity
            controller.addToCart(userID, productId, currentValue + quantity);
        }
        // If the item is not in the cart, add it
        else {
            products.put(productId, quantity);
            controller.addToCart(userID, productId, quantity);
        }
    }

    /**
     * Modifies the quantity of a product in the cart
     * @param productId The product ID for the item
     * @param quantity The new quantity
     */
    public void modifyQuantity(String productId, long quantity) {
        if (quantity == 0) {
            removeFromCart(productId);
        }
        else {
            products.put(productId, quantity);
            // Add to cart will overwrite the existing entry with the new quantity
            controller.addToCart(userID, productId, quantity);
        }
        activity.setCartContents();
    }

    /**
     * Removes a product from the cart
     * @param productId The ID of the product to remove
     */
    public void removeFromCart(String productId) {
        products.remove(productId);
        for (int i = 0; i < productDetails.size(); i++) {
            if (productDetails.get(i).getId().equals(productId)) {
                productDetails.remove(i);
                break;
            }
        }
        controller.removeFromCart(userID, productId);
        activity.setCartContents();
    }
}
