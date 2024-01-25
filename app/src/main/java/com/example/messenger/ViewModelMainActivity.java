package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class ViewModelMainActivity extends AndroidViewModel {
    private static final String TAG = "ViewModelMainActivity";
    private MutableLiveData<Boolean> isLoginSucces = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    public ViewModelMainActivity(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }
    public LiveData<Boolean> getIsLoginSucces() {
        return isLoginSucces;
    }

}
