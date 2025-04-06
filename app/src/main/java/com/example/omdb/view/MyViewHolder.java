package com.example.omdb.view;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omdb.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView title;
    TextView year;
    ItemClickListener clickListener;
    public MyViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
        super(itemView);

        this.clickListener = clickListener;

        imageView = itemView.findViewById(R.id.moviePoster);
        title = itemView.findViewById(R.id.movieTitle);
        year = itemView.findViewById(R.id.movieYear);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "onViewHolder Click");
                clickListener.onClick(view, getAdapterPosition());
            }
        });
    }
}