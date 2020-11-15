package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CategoryListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;

    String[] categoryNameList = {"Shirts", "Pants", "Jackets", "Shoes"};
    int[] categoryImages = {R.drawable.shirt, R.drawable.pants, R.drawable.jacket, R.drawable.shoes};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        recyclerView = findViewById(R.id.categoryList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Adapter = new CategoryListAdapter(this, categoryNameList, categoryImages);

    }
}