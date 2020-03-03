package com.example.sela.finalprojectandroid1;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//    }
//}

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    public EditText editTextname, editTextid, editTextpassword, editTextemail, editTextphoneNumber;
    public String email, password, name, id, phoneNumber;
    private FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextpassword = findViewById(R.id.editTextPassword);
        editTextemail = findViewById(R.id.editTextEmail);
        editTextname = findViewById(R.id.editTextName);
        editTextid = findViewById(R.id.editTextID);
        editTextphoneNumber = findViewById(R.id.editTextPhoneNumber);

// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    public void createAccount(View view) {
        email = editTextemail.getText().toString();
        password = editTextpassword.getText().toString();
        name = editTextname.getText().toString();
        id = editTextid.getText().toString();
        phoneNumber = editTextphoneNumber.getText().toString();

        if (email.isEmpty()) {
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Please enter a valid Email.");
            editTextemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Password is required.");
            editTextpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Minimum length of password should be 6 digits.");
            editTextpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Authentication succeed.",
                                    Toast.LENGTH_SHORT).show();
//                            enterUSerDetailsToFirebase();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

        Intent intent = new Intent(this, MainActivity.class);
        //send the new registered email and password with putExtra
        //and set the editTexts in the LoginActivity with these values.
        intent.putExtra("password", password);
        intent.putExtra("email", email);

        startActivity(intent);

    }


//    public void enterUSerDetailsToFirebase() {
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        String uid = user.getUid();
//        Log.d(TAG, "got id: " + uid);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users").child(uid).child("profile");
//
//        User u = new User();
//        u.setName(name);
//        u.setId(id);
//        u.setPassword(password);
//        u.setEmail(email);
//        u.setPhoneNumber(phoneNumber);
//
//
//        myRef.setValue(u);
//        Toast.makeText(RegisterActivity.this, "after setValue", Toast.LENGTH_SHORT).show();
//
//
//    }

}
