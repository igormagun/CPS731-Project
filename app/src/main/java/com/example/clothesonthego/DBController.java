package com.example.clothesonthego;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A controller to interact with the Firestore database
 */
public class DBController {
    /**
     * Loads all products from Firestore for a given category
     * @param categoryActivity The activity where the products will be displayed
     * @param category The category name
     */
    public void loadCategory(CategoryActivity categoryActivity, String category) {
        ArrayList<Product> products = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").whereEqualTo("product_type", category)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Product newProduct = new Product(
                            document.getId(),
                            (String) document.get("name"),
                            (String) document.get("photo_url"),
                            (String) document.get("description"),
                            (double) document.get("price")
                    );
                    products.add(newProduct);
                }
                // Returns the retrieved products to the CategoryActivity for display
                categoryActivity.setProducts(products);
            }
        });
    }

    /**
     * Load cart contents for the specified user from Firestore
     * @param cart The Cart object to return results to
     * @param userId The user's ID
     */
    public void loadCart(Cart cart, String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        AtomicReference<Map<String, Object>> result = new AtomicReference<>();
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Shipping> shippingList = new ArrayList<>();

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        result.set(task.getResult().getData());

                        // Get product details for the cart once loaded
                        db.collection("products").whereIn(FieldPath.documentId(),
                                Arrays.asList(result.get().keySet().toArray()))
                                .get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task1.getResult()) {
                                            Product newProduct = new Product(
                                                    document.getId(),
                                                    (String) document.get("name"),
                                                    (String) document.get("photo_url"),
                                                    (String) document.get("description"),
                                                    (double) document.get("price")
                                            );
                                            products.add(newProduct);
                                        }
                                        // Get the Shipping list to be displayed in the cart
                                        db.collection("shipping").get()
                                                .addOnCompleteListener(task2 -> {
                                                    if (task2.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document :
                                                                task2.getResult()) {
                                                            Shipping newShipping = new Shipping(
                                                                    (String) document.get("city"),
                                                                    (double) document.get("cost")
                                                            );
                                                            shippingList.add(newShipping);
                                                        }
                                                    }
                                                    // Send results to Cart object
                                                    cart.setupCart(result, products, shippingList);
                                                });
                                    }
                                }
                        );
                    }
                }
        );
    }

    /**
     * Add the specified item to the specified user's cart
     * Can also be used to modify the quantity of an existing item
     * @param userId The user's ID
     * @param productId The product ID
     * @param quantity The quantity of the product
     * @param overwrite Specifies if we should overwrite any existing quantity
     */
    public void addToCart(String userId, String productId, long quantity, boolean overwrite) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> newEntry = new HashMap<>();

        // If overwrite is true, we do not need to check if a quantity already exists
        if (overwrite) {
            newEntry.put(productId, quantity);
            db.collection("carts").document(userId).update(newEntry);
        }
        // If we are not overwriting, we must check for an existing quantity to add to first
        else {
            db.collection("carts").document(userId).get().addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            Map<String, Object> result = task.getResult().getData();
                            if (result.containsKey(productId)) {
                                Long currentQuantity = (Long) result.get(productId);
                                Long newQuantity = currentQuantity + quantity;
                                newEntry.put(productId, newQuantity);
                            }
                            else {
                                newEntry.put(productId, quantity);
                            }
                            db.collection("carts").document(userId).update(newEntry);
                        }
                    }
            );
        }
    }

    /**
     * Remove an item from the user's cart
     * @param userId The user's ID
     * @param productId The product to remove
     */
    public void removeFromCart(String userId, String productId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> entryToDelete = new HashMap<>();
        entryToDelete.put(productId, FieldValue.delete());
        db.collection("carts").document(userId).update(entryToDelete);
    }

    /**
     * Set the order destination in a user's cart
     * @param userId The user's ID
     * @param destination The new destination to set
     */
    public void setDestination(String userId, String destination) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> newDestination = new HashMap<>();
        newDestination.put("destination", destination);
        db.collection("carts").document(userId).update(newDestination);
    }

    /**
     * Remove the order destination from a user's cart
     * @param userId The user's ID
     */
    public void removeDestination(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> deleteDestination = new HashMap<>();
        deleteDestination.put("destination", FieldValue.delete());
        db.collection("carts").document(userId).update(deleteDestination);
    }

    /**
     * Create a cart for the specified user ID, if it does not already exist
     * @param userId The user ID
     */
    public void createCart(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference cartRef = db.collection("carts").document(userId);
        cartRef.get().addOnCompleteListener(
                task -> {
                    if (!task.getResult().exists()) {
                        db.collection("carts").document(userId).set(new HashMap<>());
                    }
                } );
    }
}
