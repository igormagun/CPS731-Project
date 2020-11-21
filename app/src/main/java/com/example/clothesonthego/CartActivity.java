package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
        ArrayList<String> destinations = new ArrayList<>();
        // Add a "Select destination" entry to default to
        destinations.add("Select destination");

        // Add destinations and their prices to the dropdown
        for (Shipping dest : shipping) {
            destinations.add(dest.getCity() + " - $" + dest.getCost());
        }

        // Load data into spinner
        Spinner destinationSpinner = findViewById(R.id.destinationSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, destinations);
        destinationSpinner.setAdapter(adapter);

        // TODO: Add listener to look for selected item, preselect item if destination is set
    }

    /**
     * Get the destination and products from the cart
     * This is called by our Cart object once contents are loaded from Firestore
     */
    public void setCartContents() {
        String destination = cart.getDestination();
        HashMap<String, Long> products = cart.getProducts();
        ArrayList<Product> productDetails = cart.getProductDetails();

        CartAdapter adapter = new CartAdapter(this, products, productDetails);
        recyclerView.setAdapter(adapter);

        // TODO: If not null, display the destination and the shipping cost for it
    }
}