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
    //Declare the XML Elements
    Button btnAdd, backButton;
    TextView movieTitle, movieYearAndLength, movieGender, movieDirector, movieActors, movieSynopsis, movieRate;
    ImageView moviePoster;
    OMDbViewModel viewModel;

    //Declare  variable
    OMDbModel OMDb;
    final CreateMovie addMovie = new CreateMovie();
    String moviePosterUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Connect variables to the XML elements IDs.
        moviePoster = findViewById(R.id.moviePoster);
        movieTitle = findViewById(R.id.movieTitle);
        movieYearAndLength = findViewById(R.id.movieYearAndLength);
        movieGender = findViewById(R.id.movieGender);
        movieDirector = findViewById(R.id.movieDirector);
        movieActors = findViewById(R.id.movieActors);
        movieSynopsis = findViewById(R.id.movieSinopsis);
        movieRate = findViewById(R.id.movieRate);
        backButton = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnFavoriteDetail);

        //Firebase Connections
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Intent variable that retrieves the IMDb ID
        Intent intDetailMovie = getIntent();
        String IMDbID = intDetailMovie.getStringExtra("IMDbID");

        // Obtains the ViewModel instance to interact with movie data (OMDb)
        viewModel = new ViewModelProvider(this).get(OMDbViewModel.class);

        // Observes the filtered movie data and updates the UI when the data changes
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

            moviePosterUrl = movie.getUrlImg();
        });

        // Calls the method to get the movie data by its IMDb ID
        viewModel.GetMovieByID(IMDbID);

        //Button that return MainActivity page
        backButton.setOnClickListener(v -> {
            Intent intentobj = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentobj);
        });

        //Button that add a new movie to favorite
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