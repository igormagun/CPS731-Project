package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProductActivity extends AppCompatActivity {

    DBController controller;
    FirebaseAuth mAuth;
    String productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Get the product ID to display
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getString("productId");
        }

        controller = new DBController();
        mAuth = FirebaseAuth.getInstance();

        // Set a listener to add the item to the cart on the user's request
        findViewById(R.id.addToCart).setOnClickListener(v ->
                controller.addToCart(mAuth.getUid(), productId, Integer.parseInt(
                        findViewById(R.id.ProductQuantity).toString())));

        // TODO: Display the product based on what is in the database for the given product ID
    }


}