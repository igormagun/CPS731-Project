package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
    Float price;

    TextView name;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        name = findViewById(R.id.ProductName);
        description = findViewById(R.id.ProductDescription);

        // Get the product details to display
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getString("productId");
            productName = extras.getString("productName");
            photoUrl = extras.getString("photoUrl");
            productDescription = extras.getString("description");
            price = extras.getFloat("price");

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
    }


}