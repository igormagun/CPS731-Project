package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity showing the user's cart
 */
public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Cart cart;
    private ArrayList<Shipping> shipping = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a cart object - this will read the cart from Firebase
        cart = new Cart(this);

        // Start loading the shipping costs from the database
        DBController controller = new DBController();
        controller.loadShipping(this);
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
        HashMap<String, Long> products = cart.getProducts();

        CartAdapter adapter = new CartAdapter(this, products);
        recyclerView.setAdapter(adapter);

        // TODO: If not null, display the destination and the shipping cost for it
    }
}