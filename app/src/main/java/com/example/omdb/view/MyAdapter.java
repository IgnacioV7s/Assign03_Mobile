package com.example.omdb.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;
import com.example.omdb.viewmodel.OMDbViewModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final List<OMDbModel> items;
    private final Context context;
    private ItemClickListener clickListener;

    public MyAdapter(List<OMDbModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setClickListener(ItemClickListener myListener) {
        this.clickListener = myListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_info, parent, false);
        return new MyViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OMDbModel movieOMDb = items.get(position);

        holder.title.setText(movieOMDb.getTitle());
        holder.year.setText(String.valueOf(movieOMDb.getYear()));

        Glide.with(context)
                .load(movieOMDb.getUrlImg())
                .error(holder.imageView)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onClick(view, position);
            }

            Intent intent = new Intent(context, MovieDetail.class);
            intent.putExtra("IMDbID", movieOMDb.getImdbID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
