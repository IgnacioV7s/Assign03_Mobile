package com.example.omdb.view.RecycleFavorites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;
import com.example.omdb.view.DetailSpecificMovie.FavoriteMovie;
import com.example.omdb.view.ItemClickListener;
import java.util.List;

public class MyAdapterFavorite extends RecyclerView.Adapter<MyViewHolderFavorite> {
    final List<OMDbModel> items;
    final Context context;
    ItemClickListener clickListener;

    public MyAdapterFavorite(List<OMDbModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setClickListener(ItemClickListener myListener) {
        this.clickListener = myListener;
    }

    @NonNull
    @Override
    public MyViewHolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_info, parent, false);
        return new MyViewHolderFavorite(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFavorite holder, int position) {
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

            view.setPressed(false);

            Intent intent = new Intent(context, FavoriteMovie.class);
            intent.putExtra("FirebaseID", movieOMDb.getIdFirebase());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

