package com.example.omdb.Firebase;

import java.util.Map;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateMovie {
    //Callback interface that captures the result from updateMovie.
    public  interface FirestoreCallBackUpdate {
        void onUpdateDocument(boolean isSuccessful);
    }

    //Method that updates an existing document in the IMDb collection.
    public void updateMovie (FirebaseFirestore db, String documentID, Map<String, Object> newSynomsis, FirestoreCallBackUpdate callBackUpdate) {
        db.collection("IMDb")
                .document(documentID)
                .update(newSynomsis)
                .addOnSuccessListener(aVoid -> {
                    callBackUpdate.onUpdateDocument(true);
                })
                .addOnFailureListener(e -> {
                    callBackUpdate.onUpdateDocument(false);
                });
    }
}