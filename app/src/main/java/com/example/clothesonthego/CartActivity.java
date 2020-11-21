package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity showing the user's cart
 */
public class CartActivity extends AppCompatActivity {

    private Cart cart;
    private ArrayList<Shipping> shipping = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Create a cart object - this will read the cart from Firebase
        cart = new Cart(this);

        // Start loading the shipping costs from the database
        DBController controller = new DBController();
        controller.loadShipping(this);

        // TODO: Determine how best to display the data in the UI, implement accordingly
    }

    /**
     * Sets the list of shipping costs to be used
     * @param shipping The list of shipping costs
     */
    public void setShipping(ArrayList<Shipping> shipping) {
        this.shipping = shipping;
    }

    /**
     * Get the destination and products from the cart
     * This is called by our Cart object once contents are loaded from Firestore
     */
    public void setCartContents() {
        String destination = cart.getDestination();
        HashMap<String, Integer> products = cart.getProducts();
    }
}