package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * An activity displaying all the products in a given category
 */
public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the list of products passed in from CategoryListActivity
        Bundle intentExtras = getIntent().getExtras();
        ArrayList<Product> products;
        if (intentExtras != null) {
            products = (ArrayList<Product>) intentExtras.get("products");
        }
        else {
            products = new ArrayList<>();
        }

        CategoryAdapter adapter = new CategoryAdapter(this, products);
        recyclerView.setAdapter(adapter);
    }
}