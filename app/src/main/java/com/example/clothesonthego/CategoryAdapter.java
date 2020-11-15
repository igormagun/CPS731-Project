package com.example.clothesonthego;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;
    ArrayList<Product> products;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView rowName;
        TextView rowImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowImage = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View view) {
            // Get the products for a corresponding category and launch that category screen
            DBController controller = new DBController();
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

    public CategoryAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(products.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
