package com.example.omdb.Firebase;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterAccount {
    //Callback interface that captures the result from registerUser.
    public interface AuthCallback {
        void onAuthResult(boolean isSuccessful);
    }

    //Method to create a new user in firebase
    public void registerUser(FirebaseAuth mAuth, String email, String password, AuthCallback callback) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        callback.onAuthResult(task.isSuccessful());
                    });
        }
        else {
            callback.onAuthResult(false);
        }
    }
}
