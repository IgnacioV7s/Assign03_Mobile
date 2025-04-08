package com.example.omdb.Firebase;

import androidx.annotation.NonNull;

import com.example.omdb.model.OMDbModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetMovie {
    //Callback interface that captures the result from GetAllMovies.
    public interface FirestoreCallback {
        void onCallback(List<OMDbModel> movies);
    }

    //Callback interface that captures the result from GetMovieByFirebaseID.
    public interface FirestoreCallbackID {
        void onCallbackID(OMDbModel getMovieByID);
    }

    //Method that returns all movies from IMDb the collection.
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
                                movie.setImdbID(document.getString("IMDbID"));
                                movie.setUrlImg(document.getString("poster"));
                                movie.setTitle(document.getString("title"));
                                movie.setDirector(document.getString("director"));
                                movie.setActors(document.getString("actor"));
                                movie.setSynopsis(document.getString("plot"));
                                movie.setRate(document.getString("value"));
                                movie.setGender(document.getString("gender"));
                                movie.setLengthMovie(document.getString("lengthMovie"));
                                movie.setYear(document.getString("year"));

                                listGetMovie.add(movie);
                            }
                            callback.onCallback(listGetMovie);
                        } else {
                            List<OMDbModel> listNull = new ArrayList<>();
                            callback.onCallback(listNull);
                        }
                    }
                });
    }

    //Method that returns a specific movie filtered by ID from the IMDb collection.
    public void GetMovieByFirebaseID(FirebaseFirestore db, String firebaseID, FirestoreCallbackID callback) {
        db.collection("IMDb") // Nombre de la colección
                .document(firebaseID) // ID único del documento
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult().exists()) {
                            DocumentSnapshot document = task.getResult();
                            OMDbModel OMDbMovie = new OMDbModel();
                            OMDbMovie.setIdFirebase(document.getId());
                            OMDbMovie.setImdbID(document.getString("IMDbID"));
                            OMDbMovie.setUrlImg(document.getString("poster"));
                            OMDbMovie.setTitle(document.getString("title"));
                            OMDbMovie.setDirector(document.getString("director"));
                            OMDbMovie.setActors(document.getString("actor"));
                            OMDbMovie.setSynopsis(document.getString("plot"));
                            OMDbMovie.setRate(document.getString("value"));
                            OMDbMovie.setGender(document.getString("gender"));
                            OMDbMovie.setLengthMovie(document.getString("lengthMovie"));
                            OMDbMovie.setYear(document.getString("year"));

                            callback.onCallbackID(OMDbMovie); // Devuelve el objeto encontrado
                        } else {
                            callback.onCallbackID(null); // Si el documento no existe
                        }
                    }
                });
    }

}
