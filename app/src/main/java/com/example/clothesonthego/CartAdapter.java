package com.example.clothesonthego;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * An adapter for the RecyclerView in the CartActivity
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private HashMap<String, Long> products;
    private ArrayList<Product> productDetails;

    public CartAdapter(Context context, HashMap<String, Long> products,
                       ArrayList<Product> productDetails) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.products = products;
        this.productDetails = productDetails;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        final TextView rowName;
        final ImageView rowImage;
        final EditText quantity;
        final TextView rowPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowImage = itemView.findViewById(R.id.imageView);
            quantity = itemView.findViewById(R.id.quantity);
            rowPrice = itemView.findViewById(R.id.priceLabel);
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
        Product product = productDetails.get(position);
        holder.rowName.setText(product.getName());
        Glide.with(context).load(product.getPhotoUrl()).into(holder.rowImage);
        holder.quantity.setText(String.format(Locale.CANADA, "%d", products.get(product.getId())));
        double price = product.getPrice() * products.get(product.getId());
        holder.rowPrice.setText(context.getString(R.string.dollar_amount, price));
        // TODO: Add listeners for quantity and delete buttons
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
