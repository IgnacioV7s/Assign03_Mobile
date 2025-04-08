package com.example.omdb.view.FavoriteMovie;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omdb.Firebase.GetMovie;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;

import com.example.omdb.view.MainActivity;
import com.example.omdb.view.RecycleFavorites.MyAdapterFavorite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListFavoriteMovie extends AppCompatActivity {
    //Declare the XML Elements
    Button detailSpecificMovie, btnRedirectToSearch;

    //Declare and initialize a new class
    GetMovie getFavoriteMovies = new GetMovie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_favorite_movie);

        //Connect variables to the XML elements IDs.
        detailSpecificMovie = findViewById(R.id.getDetailMovie);
        btnRedirectToSearch = findViewById(R.id.btnRedirectToSearch);


        //Firebase Connections
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvFavoriteMovies);

        List<OMDbModel> itemList = new ArrayList<>();
        MyAdapterFavorite myFavoriteFavorite = new MyAdapterFavorite(itemList, this);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(myFavoriteFavorite);

        //Method that gets the all document
        getFavoriteMovies.GetAllMovies(db, new GetMovie.FirestoreCallback() {
            @Override
            public void onCallback(List<OMDbModel> movies) {
                if(movies.size() > 0) {
                    itemList.clear();
                    itemList.addAll(movies);
                    myFavoriteFavorite.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(ListFavoriteMovie.this, "You don't have any favorite movies yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button that return MainActivity page
        btnRedirectToSearch.setOnClickListener(view -> {
            Intent intentobj = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentobj);
        });
    }
}