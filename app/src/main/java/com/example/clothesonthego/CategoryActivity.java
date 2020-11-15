package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null) {
            this.products = (ArrayList<Product>) intentExtras.get("products");
        }
    }
}