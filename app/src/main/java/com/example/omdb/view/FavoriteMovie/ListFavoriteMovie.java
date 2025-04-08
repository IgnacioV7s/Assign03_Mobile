package com.example.omdb.view.FavoriteMovie;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omdb.Firebase.GetMovie;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;

import com.example.omdb.view.RecycleFavorites.MyAdapterFavorite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListFavoriteMovie extends AppCompatActivity {
    Button detailSpecificMovie;
    // Inicializar getFavoriteMovies
    GetMovie getFavoriteMovies = new GetMovie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_favorite_movie);

        // Instancia de FirebaseFirestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        detailSpecificMovie = findViewById(R.id.getDetailMovie);

        // Inicializar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvFavoriteMovies);

        List<OMDbModel> itemList = new ArrayList<>();
        MyAdapterFavorite myFavoriteFavorite = new MyAdapterFavorite(itemList, this);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(myFavoriteFavorite);

        // Llamar al metodo GetAllMovies
        getFavoriteMovies.GetAllMovies(db, new GetMovie.FirestoreCallback() {
            @Override
            public void onCallback(List<OMDbModel> movies) {
                // Actualizar el RecyclerView con los datos obtenidos
                itemList.clear();
                itemList.addAll(movies);
                myFavoriteFavorite.notifyDataSetChanged();
            }
        });
    }
}