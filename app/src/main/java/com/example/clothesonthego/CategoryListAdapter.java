package com.example.clothesonthego;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * An adapter for the CategoryListActivity's RecyclerView
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private final Context context;
    String[] categoryNameList;
    int[] categoryImages;

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
            ArrayList<Product> products = controller.loadCategory(rowName.toString());
            Intent categoryIntent = new Intent(context, CategoryActivity.class);
            categoryIntent.putExtra("products", products);
            context.startActivity(categoryIntent);
        }
    }

    public CategoryListAdapter(Context context, String[] categoryNameList, int[] categoryImages) {
        this.context = context;
        this.categoryNameList = categoryNameList;
        this.categoryImages = categoryImages;
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(categoryNameList[position]);

    }

    @Override
    public int getItemCount() {
        return categoryNameList.length;
    }
}
