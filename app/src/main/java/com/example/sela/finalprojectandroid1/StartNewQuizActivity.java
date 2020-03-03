package com.example.sela.finalprojectandroid1;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class StartNewQuizActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_new_quiz);
//    }
//}

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartNewQuizActivity extends AppCompatActivity {
    public static final String TAG = StartNewQuizActivity.class.getSimpleName();

    private Button btnManagerOptions, btnNewGame;
    private ImageView triviaIconGame;

    //Firebase
    private FirebaseAuth mAuth;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_quiz);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnManagerOptions = findViewById(R.id.button_manager_options);
        btnNewGame = findViewById(R.id.button_newGame);

        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        if (isAdmin) {
            btnManagerOptions.setVisibility(View.VISIBLE);
            btnNewGame.setVisibility(View.GONE);
        }

        getCurrentUser();

    }


    public void startNewQuiz(View view) { //will be moved to another activity
        Intent intent = new Intent(this, categoriesActivity.class);
        startActivity(intent);
    }


    public void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            Log.d(TAG, "got id: " + uid);
            Log.d(TAG, "got email: " + email);


        }
    }
}