package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ActivityForgotPassword extends AppCompatActivity {
    private final static String EMAIL_KEY = "email";
    private EditText editTextLogin;
    private Button buttonResetPassword;
    private ViewModelResetPassword viewModelResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();

        viewModelResetPassword = new ViewModelProvider(this).get(ViewModelResetPassword.class);

        String email = getIntent().getStringExtra(EMAIL_KEY);
        editTextLogin.setText(email);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextLogin.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ActivityForgotPassword.this, "Поле логина не заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewModelResetPassword.resetPassword(email);
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModelResetPassword.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null){
                    Toast.makeText(ActivityForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModelResetPassword.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if (shouldCloseScreen) {
                    Toast.makeText(ActivityForgotPassword.this, R.string.succes_sent_reset_link, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ActivityForgotPassword.class);
        intent.putExtra(EMAIL_KEY, email);
        return intent;
    }

    private void initViews() {
        editTextLogin = findViewById(R.id.editTextLogin);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }
}