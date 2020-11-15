package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.productList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null) {
            products = (ArrayList<Product>) intentExtras.get("products");
        }
        else {
            products = new ArrayList<>();
        }

        adapter = new CategoryAdapter(this, products);
    }
}