package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewModelRegistration extends ViewModel {
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public ViewModelRegistration() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void register(
            String email,
            String password,
            String name,
            String lastName,
            int age
    ) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = authResult.getUser();
                if (firebaseUser == null) {
                    return;
                }
                User userInfo = new User(firebaseUser.getUid(), name, lastName, age, false);
                databaseReference.child(userInfo.getId()).setValue(userInfo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errorMessage.setValue(e.getMessage());
            }
        });
    }
}
