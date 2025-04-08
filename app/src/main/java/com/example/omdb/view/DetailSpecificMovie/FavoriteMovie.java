package com.example.omdb.view.DetailSpecificMovie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.omdb.Firebase.GetMovie;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FavoriteMovie extends AppCompatActivity {
    ImageView imagePoster;

    TextView favoriteMovieTitle, favoriteMovieYearAndLength, favoriteMovieGender, favoriteMovieDirector, favoriteMovieActors, favoriteMovieSinopsis, favoriteMovieRate;

    GetMovie getFavoriteMovies = new GetMovie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_favorite_movie);

        Intent intent = getIntent();

        //Intent and FirebaseID Document
        String firebaseID = intent.getStringExtra("FirebaseID");

        //Firebase Connections
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //ID XML
        imagePoster = findViewById(R.id.favoriteMoviePoster);
        favoriteMovieTitle = findViewById(R.id.favoriteMovieTitle);
        favoriteMovieYearAndLength = findViewById(R.id.favoriteMovieYearAndLength);
        favoriteMovieGender = findViewById(R.id.favoriteMovieGender);
        favoriteMovieDirector = findViewById(R.id.favoriteMovieDirector);
        favoriteMovieActors = findViewById(R.id.favoriteMovieActors);
        favoriteMovieSinopsis = findViewById(R.id.favoriteMovieSinopsis);
        favoriteMovieRate = findViewById(R.id.favoriteMovieRate);

        getFavoriteMovies.GetMovieByFirebaseID(db, firebaseID, new GetMovie.FirestoreCallbackID() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCallbackID(OMDbModel movie) {
                Glide.with(FavoriteMovie.this)
                        .load(movie.getUrlImg())
                        .into(imagePoster);

                favoriteMovieTitle.setText(movie.getTitle());
                favoriteMovieYearAndLength.setText(movie.getYear() + " - " + movie.getLengthMovie());
                favoriteMovieGender.setText(movie.getGender());
                favoriteMovieDirector.setText(movie.getDirector());
                favoriteMovieActors.setText(movie.getActors());
                favoriteMovieSinopsis.setText(movie.getSynopsis());
                favoriteMovieRate.setText(movie.getRate());
            }
        });

    }
}