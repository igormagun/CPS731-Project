package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * An activity displaying all the products in a given category
 */
public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the category name passed in, and retrieve the list of products in it
        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null) {
            String category = intentExtras.getString("category");
            DBController controller = new DBController();
            controller.loadCategory(this, category);
        }
    }

    /**
     * Sets the list of products displayed by the RecyclerView
     * This is meant to be called by the DBController once the Firestore query is completed
     * @param products The list of products to display
     */
    public void setProducts(ArrayList<Product> products) {
        CategoryAdapter adapter = new CategoryAdapter(this, products);
        recyclerView.setAdapter(adapter);
    }
}