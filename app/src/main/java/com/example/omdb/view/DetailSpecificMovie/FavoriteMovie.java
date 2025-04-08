package com.example.omdb.view.DetailSpecificMovie;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.omdb.Firebase.DeleteMovie;
import com.example.omdb.Firebase.GetMovie;
import com.example.omdb.Firebase.UpdateMovie;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;
import com.example.omdb.view.FavoriteMovie.ListFavoriteMovie;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FavoriteMovie extends AppCompatActivity {
    //Declare the XML Elements
    ImageView imagePoster;
    Button btnCancel, btnUpdate, btnDelete;
    TextView favoriteMovieTitle, favoriteMovieYearAndLength, favoriteMovieGender, favoriteMovieDirector, favoriteMovieActors, favoriteMovieSinopsis, favoriteMovieRate;

    //Declare and initialize a new class
    GetMovie getFavoriteMovies = new GetMovie();
    UpdateMovie updateMovie = new UpdateMovie();
    DeleteMovie deleteMovie = new DeleteMovie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_favorite_movie);

        //Connect variables to the XML elements IDs.
        imagePoster = findViewById(R.id.favoriteMoviePoster);
        favoriteMovieTitle = findViewById(R.id.favoriteMovieTitle);
        favoriteMovieYearAndLength = findViewById(R.id.favoriteMovieYearAndLength);
        favoriteMovieGender = findViewById(R.id.favoriteMovieGender);
        favoriteMovieDirector = findViewById(R.id.favoriteMovieDirector);
        favoriteMovieActors = findViewById(R.id.favoriteMovieActors);
        favoriteMovieSinopsis = findViewById(R.id.favoriteMovieSinopsis);
        favoriteMovieRate = findViewById(R.id.favoriteMovieRate);
        btnUpdate = findViewById(R.id.updateBtn);
        btnDelete = findViewById(R.id.removeBtn);
        btnCancel = findViewById(R.id.cancelBtn);

        //Firebase Connections
        //FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Intent and FirebaseID Document
        Intent intent = getIntent();
        String firebaseID = intent.getStringExtra("FirebaseID");

        //Message when the user enters the page
        Toast.makeText(this, "Only the movie description can be modified.", LENGTH_LONG).show();

        //Method that gets the specific document by firebaseID
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

        //Cancel button that returns to the Favorite Movies page.
        btnCancel.setOnClickListener(view -> {
            Intent intentobj = new Intent(getApplicationContext(), FavoriteMovie.class);
            intentobj.putExtra("FirebaseID", firebaseID);
            startActivity(intentobj);
        });

        //Update button that updates the description of a movie.
        btnUpdate.setOnClickListener(view -> {
            Map<String, Object> movieDetail = new HashMap<>();

            movieDetail.put("plot", favoriteMovieSinopsis.getText().toString());

            updateMovie.updateMovie(db, firebaseID, movieDetail, new UpdateMovie.FirestoreCallBackUpdate() {
                @Override
                public void onUpdateDocument(boolean isSuccessful) {
                    if (isSuccessful) {
                        Toast.makeText(FavoriteMovie.this, "Movie updated successfully.", LENGTH_LONG).show();
                        Intent intentobj = new Intent(getApplicationContext(), FavoriteMovie.class);
                        intentobj.putExtra("FirebaseID", firebaseID);
                        startActivity(intentobj);
                    }
                    else {
                        Toast.makeText(FavoriteMovie.this, "Failed to update the movie.", LENGTH_LONG).show();
                    }
                }
            });
        });

        //Delete button that deletes the movie.
        btnDelete.setOnClickListener(view -> {
            deleteMovie.deleteMovie(db, firebaseID, new DeleteMovie.FirestoreCallBackDelete() {
                @Override
                public void onDeleteDocument(boolean isSuccessful) {
                    if (isSuccessful) {
                        Toast.makeText(FavoriteMovie.this, "Movie deleted successfully.", LENGTH_LONG).show();
                        Intent intentObj = new Intent(getApplicationContext(), ListFavoriteMovie.class);
                        startActivity(intentObj);
                    } else {
                        Toast.makeText(FavoriteMovie.this, "Failed to delete the movie.", LENGTH_LONG).show();
                    }
                }
            });
        });
    }
}