package com.example.clothesonthego;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A controller to interact with the Firestore database
 */
public class DBController {
    /**
     * Loads all products from Firestore
     * @return An ArrayList of all the products, in their respective categories
     */
    public ArrayList<ProductCategory> loadProducts() {
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
                                    (int) document.get("quantity"),
                                    (String) document.get("photo_url"),
                                    (float) document.get("price")
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
                                    (float) document.get("cost")
                            );
                            shippingList.add(newShipping);
                        }
                    }
                });

        return shippingList;
    }

    /**
     * Create a cart for the specified user ID, if it does not already exist
     * @param userID The user ID
     */
    public void createCart(String userID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference cartRef = db.collection("carts").document(userID);
        cartRef.get().addOnCompleteListener(
                task -> {
                    if (!task.getResult().exists()) {
                        db.collection("carts").document(userID).set(new HashMap<>());
                    }
                } );
    }
}
