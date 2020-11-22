package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
    private double totalPrice = 0;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        total = findViewById(R.id.totalCost);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.cartRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a cart object - this will read the cart from Firebase
        cart = new Cart(this);

        Button checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(v -> {
            if (destination == null) {
                Toast toast = Toast.makeText(this, R.string.no_destination,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            else if (cart.getProducts().isEmpty()) {
                Toast toast = Toast.makeText(this, R.string.empty_cart, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                DBController controller = new DBController();
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("destination", destination);
                intent.putExtra("total", totalPrice);

                // Clear the user's cart and send them to the checkout page
                controller.clearCart(FirebaseAuth.getInstance().getUid());
                CartActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Set up all cart contents - products, details, and destination
     * This is called by our Cart object once contents are loaded from Firestore
     */
    public void setCartContents() {
        destination = cart.getDestination();
        HashMap<String, Long> products = cart.getProducts();
        ArrayList<Product> productDetails = cart.getProductDetails();
        this.shipping = cart.getShippingList();
        totalPrice = 0;

        CartAdapter adapter = new CartAdapter(this, products, productDetails, cart);
        recyclerView.setAdapter(adapter);

        ArrayList<String> destinations = new ArrayList<>();
        // Add a "Select destination" entry to default to
        destinations.add("Select destination");

        // Add destinations and their prices to the dropdown menu
        for (Shipping dest : shipping) {
            destinations.add(dest.getCity());
        }

        // Load data into spinner
        Spinner destinationSpinner = findViewById(R.id.destinationSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, destinations);
        destinationSpinner.setAdapter(spinnerAdapter);

        // If there is a destination set in the database, set it in the UI
        if (this.destination != null) {
            int index = destinations.indexOf(this.destination);
            destinationSpinner.setSelection(index);

            TextView shippingCost = findViewById(R.id.shippingCost);
            // The index is decremented by 1 here to account for the "Select destination" entry
            String shippingString = getString(R.string.shipping_cost,
                    shipping.get(index - 1).getCost());
            shippingCost.setText(shippingString);

            // Get total price including shipping
            totalPrice(shipping.get(index - 1).getCost());
        }
        // If no destination is set, calculate total price without shipping
        else {
            totalPrice(0);
        }

        // Display total price
        total.setText(getString(R.string.dollar_amount, totalPrice));

        // Add a listener to change the user's destination when a new one is selected
        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView shippingCost = findViewById(R.id.shippingCost);

                // If the position is 0, the user is unselecting a destination
                if (position == 0) {
                    cart.setDestination(null);
                    shippingCost.setText("");
                    totalPrice(0);
                }
                else {
                    // Decrement the position by 1 to account for the "Select destination" entry
                    Shipping newDestination = shipping.get(position - 1);
                    cart.setDestination(newDestination.getCity());
                    String shippingString = getString(R.string.shipping_cost,
                            shipping.get(position - 1).getCost());
                    shippingCost.setText(shippingString);
                    totalPrice(shipping.get(position - 1).getCost());
                }
                total.setText(getString(R.string.dollar_amount, totalPrice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    /**
     * Calculate the total price of items in the cart, plus shipping, currently
     */
    public void totalPrice(double shippingCost) {
        totalPrice = shippingCost;
        ArrayList<Product> productDetails = cart.getProductDetails();
        HashMap<String, Long> products = cart.getProducts();

        for (Product product : productDetails) {
            totalPrice += product.getPrice() * products.get(product.getId());
        }
    }

}