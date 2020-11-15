package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Activity showing the user's cart
 */
public class CartActivity extends AppCompatActivity {
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Create a cart object - this will read the cart from SharedPreferences
        cart = new Cart();

        // TODO: Complete the code
    }
}