package com.example.clothesonthego;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Loads all shipping costs from Firestore, returns them to the requesting CartActivity
     */
    public void loadShipping(CartActivity activity) {
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
                        // Return the list once the query is complete
                        activity.setShipping(shippingList);
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
                                                    (String) document.get("product_type"),
                                                    (String) document.get("name"),
                                                    (long) document.get("quantity"),
                                                    (String) document.get("photo_url"),
                                                    (double) document.get("price")
                                            );
                                            products.add(newProduct);
                                        }
                                        // Send results to Cart object
                                        cart.setupCart(result, products);
                                    }
                                }
                        );
                    }
                }
        );
    }

    /**
     * Add the specified item to the specified user's cart
     * @param userId The user's ID
     * @param productId The product ID
     * @param quantity The quantity of the product
     */
    public void addToCart(String userId, String productId, long quantity) {
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
    public void modifyCartQuantity(String userId, String productId, long newQuantity) {
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
     * Remove the order destination from a user's cart
     * @param userId The user's ID
     */
    public void removeDestination(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("carts").document(userId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> result = task.getResult().getData();
                        result.remove("destination");
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
