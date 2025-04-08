package com.example.omdb.view.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omdb.Firebase.LoginAccount;
import com.example.omdb.R;
import com.example.omdb.view.MainActivity;
import com.example.omdb.view.Register.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText textUser, passwordUser;
    Button btnSignInAccount, btnRedirectToRegister;
    FirebaseAuth mAuth;
    final LoginAccount login = new LoginAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //Authentication Firebase Process
        mAuth = FirebaseAuth.getInstance();

        textUser = findViewById(R.id.textEmail);
        passwordUser = findViewById(R.id.textPassword);
        btnSignInAccount = findViewById(R.id.buttonSignIn);
        btnRedirectToRegister = findViewById(R.id.buttonRedirectToRegister);

        Intent intentGetObj = getIntent();

        String emailCreated = intentGetObj.getStringExtra("User");
        if(emailCreated != null ) {
            textUser.setText(emailCreated);
        }

        btnSignInAccount.setOnClickListener(view -> login.signIn(mAuth, textUser.getText().toString(), passwordUser.getText().toString(), isSuccessful -> {
            if (isSuccessful) {
                Toast.makeText(LoginActivity.this, "Authentication passed", Toast.LENGTH_SHORT).show();
                Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentObj);
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }));

        btnRedirectToRegister.setOnClickListener(view -> {
            Intent intentObj = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intentObj);
        });
    }
}