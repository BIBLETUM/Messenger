package com.example.messenger;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivityForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, ActivityForgotPassword.class);
    }
}