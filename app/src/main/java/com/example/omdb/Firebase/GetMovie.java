package com.example.omdb.Firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.omdb.model.OMDbModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetMovie {

    public interface FirestoreCallback {
        void onCallback(List<OMDbModel> movies);
    }
    public void GetAllMovies(FirebaseFirestore db, FirestoreCallback callback) {
        db.collection("/IMDb")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<OMDbModel> listGetMovie = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OMDbModel movie = new OMDbModel();
                                movie.setIdFirebase(document.getId());
                                movie.setType(document.getString("type"));
                                movie.setImdbID(document.getString("IMDbID"));
                                movie.setActors(document.getString("actor"));
                                movie.setLengthMovie(document.getString("lengthMovie"));
                                movie.setYear(document.getString("year"));
                                movie.setGender(document.getString("gender"));
                                movie.setDirector(document.getString("director"));
                                movie.setSynopsis(document.getString("plot"));
                                movie.setUrlImg(document.getString("poster"));
                                movie.setTitle(document.getString("title"));
                                movie.setRate(document.getString("value"));

                                listGetMovie.add(movie);
                            }

                            callback.onCallback(listGetMovie);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
