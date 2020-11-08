package com.example.clothesonthego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;


public class CategoryActivity extends AppCompatActivity {
    CategoryListActivity category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        category = new category(this);

        // TODO: Complete the code
    }
}
