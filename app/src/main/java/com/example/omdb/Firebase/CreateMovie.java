package com.example.omdb.Firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateMovie {
    public void createNewDocument(FirebaseFirestore db) {
        Map<String, Object> newMovie = getStringObjectMap();

        db.collection("IMDb")
                .add(newMovie)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private static Map<String, Object> getStringObjectMap() {
        Map<String, Object> newMovie = new HashMap<>();
        newMovie.put("title", "Transformers: Dark of the Moon");
        newMovie.put("director", "Martin Scorsese");
        newMovie.put("poster", "https://m.media-amazon.com/images/M/MV5BMTkwOTY0MTc1NV5BMl5BanBnXkFtZTcwMDQwNjA2NQ@@._V1_SX300.jpg");
        newMovie.put("source", "Internet Movie Database");
        newMovie.put("value", "6.2");
        newMovie.put("plot", "When oil is discovered in 1920s Oklahoma under Osage Nation land, the Osage people are murdered one by one - until the FBI steps in to unravel the mystery.");
        return newMovie;
    }
}
