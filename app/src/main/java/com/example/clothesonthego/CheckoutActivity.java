package com.example.clothesonthego;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * An activity for when the user checks out
 */
public class CheckoutActivity extends AppCompatActivity {

    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String destination = intent.getExtras().getString("destination");
        double totalPrice = intent.getExtras().getDouble("total");

        TextView dest = findViewById(R.id.shippingLocation);
        dest.setText(destination);

        TextView total = findViewById(R.id.totalCost);
        total.setText(getString(R.string.dollar_amount, totalPrice));

        home = findViewById(R.id.return_home);
        home.setOnClickListener(v -> startActivity(new Intent(CheckoutActivity.this,
                CategoryListActivity.class)));
    }


}