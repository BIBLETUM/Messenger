package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private final static String EXTRA_CURID = "currentUserId";
    private final static String EXTRA_OTHRID = "otherUserId";
    private TextView textViewTitle;
    private View viewStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageView;
    private MessagesAdapter messagesAdapter;
    private String currentUserId;
    private String otherUserId;
    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra(EXTRA_CURID);
        otherUserId = intent.getStringExtra(EXTRA_OTHRID);

        chatViewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);

        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messagesAdapter);

        setListeners();

        observeViewModel();
    }

    public void setListeners(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (TextUtils.isEmpty(messageText)){
                    Toast.makeText(ChatActivity.this, "Введите сообщение", Toast.LENGTH_SHORT).show();
                    return;
                }
                Message message = new Message(currentUserId, otherUserId, messageText);
                chatViewModel.sendMessage(message);
            }
        });
    }

    private void observeViewModel() {
        chatViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(ChatActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                if (messages != null) {
                    messagesAdapter.setMessages(messages);
                }
            }
        });

        chatViewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSent) {
                if (isSent) {
                    editTextMessage.setText("");
                }
            }
        });

        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    textViewTitle.setText(user.getName() + " " + user.getLastName());
                    int backgroundId;
                    if(user.getStatus()){
                        backgroundId = R.drawable.circle_green;
                    } else {
                        backgroundId = R.drawable.circle_red;
                    }
                    Drawable drawable = ContextCompat.getDrawable(ChatActivity.this, backgroundId);
                    viewStatus.setBackground(drawable);
                }
            }
        });
    }

    public void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewStatus = findViewById(R.id.viewStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageView = findViewById(R.id.imageView);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURID, currentUserId);
        intent.putExtra(EXTRA_OTHRID, otherUserId);
        return intent;
    }
    @Override
    protected void onResume() {
        chatViewModel.setUserOnline(true);
        super.onResume();
    }

    @Override
    protected void onPause() {
        chatViewModel.setUserOnline(false);
        super.onPause();
    }
}