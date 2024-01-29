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

import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private final static String EXTRA_CURID = "currentUserId";
    private FirebaseUser user;
    private ViewModelUsers viewModelUsers;
    private RecyclerView recyclerViewUsers;
    private UsersAdepter usersAdepter;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        currentUserId = getIntent().getStringExtra(EXTRA_CURID);

        viewModelUsers = new ViewModelProvider(this).get(ViewModelUsers.class);

        observeViewModel();

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdepter = new UsersAdepter();
        recyclerViewUsers.setAdapter(usersAdepter);
        usersAdepter.setOnUserClickListener(new UsersAdepter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = ChatActivity.newIntent(UsersActivity.this, currentUserId, user.getId());
                startActivity(intent);
            }
        });

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

    @Override
    protected void onResume() {
        viewModelUsers.setUserOnline(true);
        super.onResume();
    }

    @Override
    protected void onPause() {
        viewModelUsers.setUserOnline(false);
        super.onPause();
    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_CURID, currentUserId);
        return intent;
    }
}