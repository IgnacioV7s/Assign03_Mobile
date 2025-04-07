package com.example.omdb.Firebase;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterAccount {
    public interface AuthCallback {
        void onAuthResult(boolean isSuccessful);
    }

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
