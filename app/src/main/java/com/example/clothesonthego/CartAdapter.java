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
import java.util.HashMap;

/**
 * An adapter for the RecyclerView in the CartActivity
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private HashMap<String, Long> products;

    public CartAdapter(Context context, HashMap<String, Long> products) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.products = products;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        final TextView rowName;
        final ImageView rowImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowImage = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // TODO: Implement a way of pulling the corresponding product name and image
        String productID = (String) products.keySet().toArray()[position];
        holder.rowName.setText(productID);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
