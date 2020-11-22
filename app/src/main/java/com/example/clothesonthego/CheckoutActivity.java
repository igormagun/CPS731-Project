package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Intent intent1 = getIntent();
        String destination = intent1.getExtras().getString("destination");

        TextView dest = findViewById(R.id.shippingLocation);
        dest.setText(destination);

        Intent intent2 = getIntent();
        String totalPrice = intent2.getExtras().getString("total");

        TextView total = findViewById(R.id.totalCost);
        total.setText(totalPrice);

        home = findViewById(R.id.return_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, CategoryListActivity.class));
            }
        });
    }


}