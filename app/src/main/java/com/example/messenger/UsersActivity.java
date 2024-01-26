package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {
    private FirebaseUser user;
    private ViewModelUsers viewModelUsers;
    private RecyclerView recyclerViewUsers;
    private UsersAdepter usersAdepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        viewModelUsers = new ViewModelProvider(this).get(ViewModelUsers.class);

        observeViewModel();

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdepter = new UsersAdepter();
        recyclerViewUsers.setAdapter(usersAdepter);

        viewModelUsers.loadUsers();
    }

    private void observeViewModel() {
        viewModelUsers.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = MainActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
        viewModelUsers.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                usersAdepter.setUsers(users);
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
        if (item.getItemId() == R.id.menuItemLogOut) {
            viewModelUsers.logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UsersActivity.class);
        return intent;
    }
}