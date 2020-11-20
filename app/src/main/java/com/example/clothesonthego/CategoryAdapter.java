package com.example.clothesonthego;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * An adapter for the RecyclerView in the CategoryActivity
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    final ArrayList<Product> products;

    public CategoryAdapter(Context context, ArrayList<Product> products) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.products = products;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView rowName;
        final ImageView rowImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowImage = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the products for a corresponding category and launch that category screen
            Product product = products.get(getLayoutPosition());
            Intent categoryIntent = new Intent(context, ProductActivity.class);
            categoryIntent.putExtra("productId", product.getId());
            categoryIntent.putExtra("productName", product.getName());
            categoryIntent.putExtra("photoUrl", product.getPhotoUrl());
            categoryIntent.putExtra("price", product.getPrice());
            // TODO: Add product description
            context.startActivity(categoryIntent);
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_category_list_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Product product = products.get(position);
        holder.rowName.setText(product.getName());
        Glide.with(context).load(product.getPhotoUrl()).into(holder.rowImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
