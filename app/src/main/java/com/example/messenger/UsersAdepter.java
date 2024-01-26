package com.example.messenger;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdepter extends RecyclerView.Adapter<UsersAdepter.UsersViewHolder> {
    private List<User> users = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_item,
                parent,
                false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);

        int backGroundId;
        if (user.getStatus()) {
            backGroundId = R.drawable.circle_green;
        } else {
            backGroundId = R.drawable.circle_red;
        }
        Drawable drawable = ContextCompat.getDrawable(holder.viewStatus.getContext(), backGroundId);
        holder.viewStatus.setBackground(drawable);

        String userInfo = String.format("%s %s, %s", user.getName(), user.getLastName(), user.getAge());
        holder.textViewUserInfo.setText(userInfo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener != null) {
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    interface OnUserClickListener {
        void onUserClick(User user);
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewUserInfo;
        private final View viewStatus;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
            viewStatus = itemView.findViewById(R.id.viewStatus);
        }
    }
}
