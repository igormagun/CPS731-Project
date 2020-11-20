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

import java.util.ArrayList;

/**
 * An adapter for the CategoryListActivity's RecyclerView
 */
public class CategoryListAdapter
        extends RecyclerView.Adapter<CategoryListAdapter.CategoryListHolder> {

    private final Context context;
    final String[] categoryNameList;
    final int[] categoryImages;
    private final LayoutInflater inflater;

    public CategoryListAdapter(Context context, String[] categoryNameList, int[] categoryImages) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.categoryNameList = categoryNameList;
        this.categoryImages = categoryImages;
    }

    public class CategoryListHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        final TextView rowName;
        final ImageView rowImage;

        public CategoryListHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowImage = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the products for a corresponding category and launch that category screen
            DBController controller = new DBController();
            ArrayList<Product> products = controller.loadCategory(
                    categoryNameList[getLayoutPosition()]);
            Intent categoryIntent = new Intent(context, CategoryActivity.class);
            categoryIntent.putExtra("products", products);
            context.startActivity(categoryIntent);
        }
    }

    @NonNull
    @Override
    public CategoryListAdapter.CategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        View view = inflater.inflate(R.layout.activity_category_list_item, parent,
                false);
        return new CategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.CategoryListHolder holder,
                                 int position) {
        holder.rowName.setText(categoryNameList[position]);
        holder.rowImage.setImageResource(categoryImages[position]);
    }

    @Override
    public int getItemCount() {
        return categoryNameList.length;
    }
}
