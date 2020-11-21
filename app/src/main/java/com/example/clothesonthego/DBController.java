package com.example.clothesonthego;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A controller to interact with the Firestore database
 */
// TODO: Modify controller to wait for the get() task before returning results
public class DBController {
    /**
     * Loads all products from Firestore
     * @return An ArrayList of all the products, in their respective categories
     */
    public ArrayList<ProductCategory> loadProducts() {
        // TODO: Determine if this function is still needed
        HashMap<String, ProductCategory> categories = new HashMap<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product newProduct = new Product(
                                    document.getId(),
                                    (String) document.get("product_type"),
                                    (String) document.get("name"),
                                    (long) document.get("quantity"),
                                    (String) document.get("photo_url"),
                                    (double) document.get("price")
                            );
                            if (categories.containsKey(document.get("product_type"))) {
                                ProductCategory category = categories.get(
                                        document.get("product_type"));
                                category.addProduct(newProduct);
                            }
                            else {
                                ArrayList<Product> categoryProducts = new ArrayList<>();
                                categoryProducts.add(newProduct);
                                ProductCategory newCategory = new ProductCategory(
                                        (String) document.get("product_type"),
                                        categoryProducts
                                );
                            }
                        }
                    }
                });

        return new ArrayList<>(categories.values());
    }

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
                            (String) document.get("product_type"),
                            (String) document.get("name"),
                            (long) document.get("quantity"),
                            (String) document.get("photo_url"),
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
     * Loads all shipping costs from Firestore
     * @return An ArrayList of all shipping costs
     */
    public ArrayList<Shipping> loadShipping() {
        ArrayList<Shipping> shippingList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shipping").get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Shipping newShipping = new Shipping(
                                    document.getId(),
                                    (String) document.get("city"),
                                    (double) document.get("cost")
                            );
                            shippingList.add(newShipping);
                        }
                    }
                });

        return shippingList;
    }

    /**
     * Load cart contents for the specified user from Firestore
     * @param userId The user's ID
     * @return The cart contents, as a HashMap
     */
    public AtomicReference<Map<String, Object>> loadCart(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        AtomicReference<Map<String, Object>> result = null;

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        result.set(task.getResult().getData());
                    }
                }
        );

        return result;
    }

    /**
     * Add the specified item to the specified user's cart
     * @param userId The user's ID
     * @param productId The product ID
     * @param quantity The quantity of the product
     */
    public void addToCart(String userId, String productId, int quantity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> result = task.getResult().getData();
                        if (result.containsKey(productId)) {
                            Long currentQuantity = (Long) result.get(productId);
                            Long newQuantity = currentQuantity + quantity;
                            result.put(productId, newQuantity);

                            db.collection("carts").document(userId).set(result);
                        }
                    }
                }
        );
    }

    /**
     * Modify the quantity of an item in the user's cart
     * @param userId The user's ID
     * @param productId The product ID to modify
     * @param newQuantity The new quantity
     */
    public void modifyCartQuantity(String userId, String productId, int newQuantity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> result = task.getResult().getData();
                        result.put(productId, newQuantity);
                        db.collection("carts").document(userId).set(result);
                    }
                }
        );
    }

    /**
     * Remove an item from the user's cart
     * @param userId The user's ID
     * @param productId The product to remove
     */
    public void removeFromCart(String userId, String productId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> result = task.getResult().getData();
                        result.remove(productId);
                        db.collection("carts").document(userId).set(result);
                    }
                }
        );
    }

    /**
     * Set the order destination in a user's cart
     * @param userId The user's ID
     * @param destination The new destination to set
     */
    public void setDestination(String userId, String destination) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> result = task.getResult().getData();
                        result.put("destination", destination);
                        db.collection("carts").document(userId).set(result);
                    }
                }
        );
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
