package com.example.omdb.Firebase;

import com.example.omdb.model.OMDbModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateMovie {
    //Callback interface that captures the result from addNewFavoriteMovie.
    public interface NewDocumentCallback {
        void onNewDocument(boolean isSuccessful);
    }

    //Method that creates a new document in the IMDb Collection.
    public void addNewFavoriteMovie(FirebaseFirestore db, OMDbModel OMDb, NewDocumentCallback callback) {
        Map<String, Object> newMovie = getStringObjectMap(OMDb);

        db.collection("IMDb")
                .add(newMovie)
                .addOnSuccessListener(documentReference -> callback.onNewDocument(true))
                .addOnFailureListener(e -> callback.onNewDocument(false));
    }

    //Map<String, Object> structured to create a new document in Firebase.
    private static Map<String, Object> getStringObjectMap(OMDbModel OMDb) {
        Map<String, Object> newMovie = new HashMap<>();
        newMovie.put("imdbID", OMDb.getImdbID());
        newMovie.put("title", OMDb.getTitle());
        newMovie.put("director", OMDb.getDirector());
        newMovie.put("poster", OMDb.getUrlImg());
        newMovie.put("source", OMDb.getRate());
        newMovie.put("plot", OMDb.getSynopsis());
        newMovie.put("actor", OMDb.getActors());
        newMovie.put("gender", OMDb.getGender());
        newMovie.put("year", OMDb.getYear());
        newMovie.put("length", OMDb.getLengthMovie());

        return newMovie;
    }
}
