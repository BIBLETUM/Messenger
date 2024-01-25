package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        String email = "pasha.remizov@mail.ru";
        String password = "123456";

//        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Log.d(TAG, "Registered");
//            }
//        });

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "mess send");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                });

//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser == null) {
//            Log.d(TAG, "null");
//        } else {
//            Log.d(TAG, "auth");
//        }
//
//        mAuth.signOut();
//
//        String email = "aboba@mail.ru";
//        String password = "123456";
//
//        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                if (currentUser == null) {
//                    Log.d(TAG, "null");
//                } else {
//                    Log.d(TAG, "auth");
//                }
//            }
//        });
//
//        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                if (currentUser == null) {
//                    Log.d(TAG, "userNull");
//                } else {
//                    Log.d(TAG, "userNN");
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, e.getMessage());
//                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}