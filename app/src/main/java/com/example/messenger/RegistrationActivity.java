package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private ViewModelRegistration viewModelRegistration;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();

        viewModelRegistration = new ViewModelProvider(this).get(ViewModelRegistration.class);

        observeViewModel();

        setListeners();
    }

    private void observeViewModel() {
        viewModelRegistration.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModelRegistration.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTextViewTrimmedString(editTextLogin);
                String password = getTextViewTrimmedString(editTextPassword);
                String name = getTextViewTrimmedString(editTextName);
                String lastName = getTextViewTrimmedString(editTextLastName);
                String ageString = getTextViewTrimmedString(editTextAge);
                if (
                        TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(lastName) ||
                        TextUtils.isEmpty(ageString)
                ) {
                    Toast.makeText(RegistrationActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewModelRegistration.register(email, password, name, lastName, Integer.parseInt(ageString));
            }
        });
    }

    private void initViews() {
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    private String getTextViewTrimmedString(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}