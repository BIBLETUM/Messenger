package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModelFactory implements ViewModelProvider.Factory{
    private String currId;
    private String otherId;

    public ChatViewModelFactory(String currId, String otherId) {
        this.currId = currId;
        this.otherId = otherId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currId, otherId);
    }
}
