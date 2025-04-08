package com.example.omdb.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAccount {
    //Callback interface that captures the result from signIn.
    public interface AuthCallback {
        void onAuthResult(boolean isSuccessful);
    }

    //Method for authentication with firebase
    public void signIn(FirebaseAuth mAuth, String email, String password, AuthCallback callback) {

        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> callback.onAuthResult(task.isSuccessful()));
        }
        else {
            callback.onAuthResult(false);
        }
    }
}
