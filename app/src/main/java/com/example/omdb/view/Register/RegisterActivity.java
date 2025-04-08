package com.example.omdb.view.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omdb.Firebase.RegisterAccount;
import com.example.omdb.R;
import com.example.omdb.view.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    //Declare the XML Elements
    Button btnRegister;
    EditText textEmail, textPassword;

    //Declare and initialize a new class
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    RegisterAccount register = new RegisterAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //Connect variables to the XML elements IDs.
        btnRegister = findViewById(R.id.buttonCreateAccount);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);

        //Button that creates a new user in Firebase.
        btnRegister.setOnClickListener(view -> register.registerUser(mAuth, textEmail.getText().toString(), textPassword.getText().toString(), isSuccessful -> {
            if(isSuccessful) {
                Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                user = mAuth.getCurrentUser();

                Intent intentobj = new Intent(getApplicationContext(), LoginActivity.class);
                intentobj.putExtra("User", user.getEmail());
                startActivity(intentobj);
            }
            else {
                Toast.makeText(RegisterActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}