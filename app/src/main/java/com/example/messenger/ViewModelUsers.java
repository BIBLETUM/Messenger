package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewModelUsers extends AndroidViewModel {
    private static final String TAG = "ViewModelUsers";
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> users = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void loadUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> tempUsers = new ArrayList<>();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser == null){
                    return;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User userFromDB = dataSnapshot.getValue(User.class);
                    if (userFromDB == null){
                        return;
                    }
                    if (!userFromDB.getId().equals(currentUser.getUid())){
                        tempUsers.add(userFromDB);
                    }
                }
                users.setValue(tempUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void logOut() {
        mAuth.signOut();
    }
}
