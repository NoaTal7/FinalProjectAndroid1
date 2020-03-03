package com.example.sela.finalprojectandroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

//    private Button newGameBtn;
//
//    // Ori Implementaion
//    public static final String TAG = MainActivity.class.getSimpleName();
//    public static final String ADMIN = "admin";
//
//    private FirebaseAuth mAuth;
//
//    private EditText editTextEmail;
//    private EditText editTextPassword;
//
//    private String email, password;
//    boolean isAdmin = false;
//    // End Ori Implamentaion
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        newGameBtn = findViewById(R.id.button_newGame);
//        Intent intent1 = getIntent();
//
//
//
//        // Ori implamentaion
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//
//        //check if we've just registered a new user.
//        //then copy it's email and password to editTexts fields.
//        email = getIntent().getStringExtra("email");
//        password = getIntent().getStringExtra("password");
//
//        editTextEmail.setText(email);
//        editTextPassword.setText(password);
//
//    }
//
//    public void startNewQuiz(View view)
//    {
//        // Sela Old Code
////        Intent intent = new Intent(this, categoriesActivity.class);
////        startActivity(intent);
//        // Sela Old Code End
//
//
//        FirebaseUser user = mAuth.getCurrentUser();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users");
//        Log.d(TAG, "database: " + database);
//        Log.d(TAG, "myRef: " + myRef);
//
//        Intent intent = new Intent(this, StartNewQuizActivity.class);
//        intent.putExtra("isAdmin", isAdmin);
//        startActivity(intent);
//    }
//
//    public void register(View view) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
//
//    }

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String ADMIN = "admin";

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


