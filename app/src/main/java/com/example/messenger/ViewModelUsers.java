package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewModelUsers extends AndroidViewModel {
    private static final String TAG = "ViewModelUsers";
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private FirebaseAuth mAuth;

    public ViewModelUsers(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logOut() {
        mAuth.signOut();
    }
}
