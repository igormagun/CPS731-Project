package com.example.clothesonthego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    String[] categoryNameList;
    int[] categoryImages;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView rowName;
        TextView rowImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.title);
            rowImage = itemView.findViewById(R.id.imageView);
        }
    }

    public Adapter (Context context, String[] categoryNameList, int[] categoryImages) {
        this.context = context;
        this.categoryNameList = categoryNameList;
        this.categoryImages = categoryImages;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_category_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.rowName.setText(categoryNameList[position]);

    }

    @Override
    public int getItemCount() {
        return categoryNameList.length;
    }
}
