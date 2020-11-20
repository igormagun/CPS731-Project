package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

/**
 * An activity showing the list of product categories to the user
 */
public class CategoryListActivity extends AppCompatActivity {

    final String[] categoryNameList = {"Shirts", "Pants", "Jackets", "Shoes"};
    final int[] categoryImages = {R.drawable.shirt, R.drawable.pants, R.drawable.jacket, R.drawable.shoes};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        RecyclerView recyclerView = findViewById(R.id.categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryListAdapter adapter = new CategoryListAdapter(this, categoryNameList, categoryImages);
        recyclerView.setAdapter(adapter);
    }
}