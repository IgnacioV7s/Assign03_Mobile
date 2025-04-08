package com.example.omdb.Firebase;

import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteMovie {
    //Callback interface that captures the result from deleteMovie.
    public  interface FirestoreCallBackDelete {
        void onDeleteDocument(boolean isSuccessful);
    }

    //Method that deletes an existing document in the IMDb Collection.
    public void deleteMovie(FirebaseFirestore db, String documentID, FirestoreCallBackDelete callBackDelete) {
        db.collection("IMDb").document(documentID)
                .delete()
                .addOnSuccessListener(successful -> {
                    callBackDelete.onDeleteDocument(true);
                })
                .addOnFailureListener(failed -> {
                    callBackDelete.onDeleteDocument(false);
                });
    }
}