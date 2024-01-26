package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersActivity extends AppCompatActivity {
    private final static String EXTRA_USER = "user";
    private FirebaseUser user;
    private ViewModelUsers viewModelUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        viewModelUsers = new ViewModelProvider(this).get(ViewModelUsers.class);

        observeViewModel();
       // user = (FirebaseUser) getIntent().getSerializableExtra(EXTRA_USER);

        //Toast.makeText(this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
    }

    private void observeViewModel(){
        viewModelUsers.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null){
                    Intent intent = MainActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemLogOut){
            viewModelUsers.logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, FirebaseUser user){
        Intent intent = new Intent(context, UsersActivity.class);
        //intent.putExtra(EXTRA_USER, user);
        return intent;
    }
}