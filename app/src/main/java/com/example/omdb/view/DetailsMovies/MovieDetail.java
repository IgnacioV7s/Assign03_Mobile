package com.example.omdb.view.DetailsMovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.omdb.Firebase.CreateMovie;
import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;
import com.example.omdb.view.MainActivity;
import com.example.omdb.viewmodel.OMDbViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieDetail extends AppCompatActivity {
    Button btnAdd;
    OMDbViewModel viewModel;
    OMDbModel OMDb;
    final CreateMovie addMovie = new CreateMovie();
    private String moviePosterUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        btnAdd = findViewById(R.id.btnFavoriteDetail);

        Intent intDetailMovie = getIntent();
        String IMDbID = intDetailMovie.getStringExtra("IMDbID");
        viewModel = new ViewModelProvider(this).get(OMDbViewModel.class);

        ImageView moviePoster = findViewById(R.id.moviePoster);
        TextView movieTitle = findViewById(R.id.movieTitle);
        TextView movieYearAndLength = findViewById(R.id.movieYearAndLength);
        TextView movieGender = findViewById(R.id.movieGender);
        TextView movieDirector = findViewById(R.id.movieDirector);
        TextView movieActors = findViewById(R.id.movieActors);
        TextView movieSynopsis = findViewById(R.id.movieSinopsis);
        TextView movieRate = findViewById(R.id.movieRate);
        Button backButton = findViewById(R.id.btnBack);

        viewModel.getOMDbDataFilter().observe(this, movie -> {
            String yearAndLength = movie.getYear() + " - " + movie.getLengthMovie();
            String actors = "Actors: " + movie.getActors();
            String rating = "IMDb Rating: " + movie.getRate() + "/10";
            movieTitle.setText(movie.getTitle());
            movieYearAndLength.setText(yearAndLength);
            movieGender.setText(movie.getGender());
            movieDirector.setText(movie.getDirector());
            movieActors.setText(actors);
            movieSynopsis.setText(movie.getSynopsis());
            movieRate.setText(rating);

            Glide.with(this)
                    .load(movie.getUrlImg())
                    .into(moviePoster);

            // Almacenar la URL en la variable global
            moviePosterUrl = movie.getUrlImg();
        });

        viewModel.GetMovieByID(IMDbID);

        backButton.setOnClickListener(v -> {
            Intent intentobj = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentobj);
        });

        btnAdd.setOnClickListener(v -> {
            OMDb = new OMDbModel();

            OMDb.setImdbID(IMDbID);
            OMDb.setActors(movieActors.getText().toString());
            OMDb.setDirector(movieDirector.getText().toString());
            OMDb.setGender(movieGender.getText().toString());
            OMDb.setLengthMovie(movieYearAndLength.getText().toString().split("-")[1]);
            OMDb.setYear(movieYearAndLength.getText().toString().split("-")[0]);
            OMDb.setRate(movieRate.getText().toString());
            OMDb.setSynopsis(movieSynopsis.getText().toString());
            OMDb.setTitle(movieTitle.getText().toString());
            OMDb.setUrlImg(moviePosterUrl);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            addMovie.addNewFavoriteMovie(db, OMDb, isSuccessful -> {
                if (isSuccessful) {
                    Toast.makeText(MovieDetail.this, "New movie added to favorites", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MovieDetail.this, "Oops! Couldn't add the movie to your favorites.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
