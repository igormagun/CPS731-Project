package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

/**
 * An activity to display an individual product
 */
public class ProductActivity extends AppCompatActivity {

    DBController controller;
    FirebaseAuth mAuth;
    String productId;
    String productName;
    String productDescription;
    String photoUrl;
    double price;

    TextView name;
    TextView description;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.locationHeader);
        description = findViewById(R.id.ProductDescription);

        // Get the product details to display
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getString("productId");
            productName = extras.getString("productName");
            photoUrl = extras.getString("photoUrl");
            productDescription = extras.getString("description");
            price = extras.getDouble("price");

            // TODO: Display price, implement description
            name.setText(productName);
            description.setText(productDescription);

            // Load photo from the specified URL
            Glide.with(this).load(photoUrl).into((ImageView)findViewById(R.id.ProductImage));
        }

        controller = new DBController();
        mAuth = FirebaseAuth.getInstance();

        // Set a listener to add the item to the cart on the user's request
        findViewById(R.id.addToCart).setOnClickListener(v ->
                controller.addToCart(mAuth.getUid(), productId, Integer.parseInt(
                        findViewById(R.id.ProductQuantity).toString())));

        // Set a listener for the Cart button
        findViewById(R.id.viewCart).setOnClickListener(v -> this.startActivity(
                new Intent(this, CartActivity.class)));

        logout = findViewById(R.id.LogoutButtonProduct);

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });
    }


}