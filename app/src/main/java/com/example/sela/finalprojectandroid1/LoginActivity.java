package com.example.sela.finalprojectandroid1;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class LoginActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//    }
//}


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity { //will be changed to MainActivity
    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String ADMIN = "admin";

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //before change: refferented to activity_login

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //check if we've just registered a new user.
        //then copy it's email and password to editTexts fields.
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        editTextEmail.setText(email);
        editTextPassword.setText(password);


    }

    public void signIn(View view) {

        //check if we've just registered a new user

            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required.");
            editTextEmail.requestFocus();
            return;
        }

        if(email.equals(ADMIN)) {   //if admin signs in.
            email = "admin@gmail.com";
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "signInWithEmail:success");
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Hello Admin!.",
                                    Toast.LENGTH_SHORT).show();

                            startNewQuiz();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    public void startNewQuiz() {
        FirebaseUser user = mAuth.getCurrentUser();

        Intent intent = new Intent(this, StartNewQuizActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
//
////    @Override
////    public void onStart() {
////        super.onStart();
////        // Check if user is signed in (non-null) and update UI accordingly.
////        FirebaseUser currentUser = mAuth.getCurrentUser();
////        Log.d("test", "currentUser: "+currentUser);
////
////        if (currentUser != null) {
////            // Name, email address, and profile photo Url
////            String name = currentUser.getDisplayName();
////            String email = currentUser.getEmail();
////            Uri photoUrl = currentUser.getPhotoUrl();
////
////            // Check if user's email is verified
////            boolean emailVerified = currentUser.isEmailVerified();
////            Log.d("test", "name: "+email);
////            Log.d("test", "emailVerified: "+emailVerified);
////
////
////
////            // The user's ID, unique to the Firebase project. Do NOT use this value to
////            // authenticate with your backend server, if you have one. Use
////            // FirebaseUser.getIdToken() instead.
////            String uid = currentUser.getUid();
////        }
////        Log.d("test", "not entered if. ");
////
////    }
//}
