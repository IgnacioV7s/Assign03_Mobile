package com.example.omdb.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.omdb.R;
import com.example.omdb.viewmodel.OMDbViewModel;

public class MovieDetail extends AppCompatActivity {
    OMDbViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

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
        Button backButton = findViewById(R.id.backBtn);

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
        });

        viewModel.GetMovieByID(IMDbID);

        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
