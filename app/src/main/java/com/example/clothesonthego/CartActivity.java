package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity showing the user's cart
 */
public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Cart cart;
    private String destination;
    private ArrayList<Shipping> shipping = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            destinations.add(dest.getCity());
        }

        // Load data into spinner
        Spinner destinationSpinner = findViewById(R.id.destinationSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, destinations);
        destinationSpinner.setAdapter(adapter);

        if (this.destination != null) {
            int index = destinations.indexOf(this.destination);
            // We add 1 to the index to account for the "Select destination" entry
            destinationSpinner.setSelection(index + 1);

            TextView shippingCost = findViewById(R.id.shippingCost);
            String shippingString = getString(R.string.shipping_cost, shipping.get(index).getCost());
            shippingCost.setText(shippingString);
        }

        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView shippingCost = findViewById(R.id.shippingCost);

                // If the position is 0, the user is unselecting a destination
                if (position == 0) {
                    cart.setDestination(null);
                    shippingCost.setText("");
                }
                else {
                    // Decrement the position by 1 to account for the "Select destination" entry
                    Shipping newDestination = shipping.get(position - 1);
                    cart.setDestination(newDestination.getCity());
                    String shippingString = getString(R.string.shipping_cost,
                            shipping.get(position - 1).getCost());
                    shippingCost.setText(shippingString);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    /**
     * Get the destination and products from the cart
     * This is called by our Cart object once contents are loaded from Firestore
     */
    public void setCartContents() {
        destination = cart.getDestination();
        HashMap<String, Long> products = cart.getProducts();
        ArrayList<Product> productDetails = cart.getProductDetails();

        CartAdapter adapter = new CartAdapter(this, products, productDetails);
        recyclerView.setAdapter(adapter);
    }
}